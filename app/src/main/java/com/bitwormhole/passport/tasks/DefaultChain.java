package com.bitwormhole.passport.tasks;

public class DefaultChain<T> implements Chain<T> {

    private final TaskContext context;
    private Node first;
    private Node last;
    private PromiseEvent current;

    public DefaultChain(TaskContext ctx) {
        if (ctx == null) {
            throw new RuntimeException("no TaskContext, use Promise.bind(ctx) first");
        }
        context = ctx;
    }

    @Override
    public void addTask(Task t) {
        context.runWithWorkerThread(() -> {
            runTask(t);
        });
    }

    private void runTask(Task t) {
        try {
            Result res = t.run();
            this.dispatch(new PromiseEvent(res));
        } catch (Exception e) {
            e.printStackTrace();
            this.dispatch(new PromiseEvent(e));
        }
    }

    @Override
    public void dispatch(PromiseEvent event) {
        context.runWithUIThread(() -> {
            current = event;
            Node n = first;
            PromiseEvent e = event;
            for (; n != null && e != null; n = n.next) {
                e = n.handle(e);
                first = n;
            }
            if (n == null) {
                last = null;
            }
        });
    }

    @Override
    public PromiseEvent getCurrentEvent() {
        return current;
    }

    private void addNode(Node n) {
        if (n == null) {
            return;
        }
        final PromiseEvent evt = current;
        if (evt != null) {
            n.handle(evt); // 如果已经存在结果，立即触发事件处理
            return;
        }
        final Node olderFirst = first;
        final Node olderLast = last;
        if (olderFirst == null || olderLast == null) {
            first = n;
            last = n;
        } else {
            olderLast.next = n;
            last = n;
        }
    }

    @Override
    public void addThen(ThenHandler h) {
        Node n = new Node();
        n.listener = new ResultListener(h);
        addNode(n);
    }

    @Override
    public void addCatch(CatchHandler h) {
        Node n = new Node();
        n.listener = new ErrorListener(h);
        addNode(n);
    }

    @Override
    public void addFinally(FinallyHandler h) {
        Node n = new Node();
        n.listener = new FinallyListener(h);
        addNode(n);
    }


    private static class Node {
        private Node next;
        private Listener listener; // 这个是一次性的，用完后应该设置为 null

        private PromiseEvent handle(PromiseEvent e) {
            Listener l = listener;
            listener = null;
            if (l == null) {
                return e;
            }
            try {
                Promise p = l.handle(e);
                if (p == null) {
                    return e;
                }
                Chain chain = p.getChain();
                PromiseEvent current = chain.getCurrentEvent();
                if (current == null) {
                    join(p);
                    return null;
                }
                return current;
            } catch (Exception exp) {
                return new PromiseEvent(exp);
            }
        }

        void join(Promise p) {
            p.onThen((r) -> {
                fireThisChain(new PromiseEvent(r));
                return null;
            });
            p.onCatch((r) -> {
                fireThisChain(new PromiseEvent(r));
                return null;
            });
            p.onFinally(() -> {
                // fireThisChain(  );
            });
        }

        void fireThisChain(PromiseEvent e) {
            Node p = this;
            for (; p != null; p = p.next) {
                p.handle(e);
            }
        }

    }

    private static interface Listener {
        Promise handle(PromiseEvent e);
    }

    private static class ResultListener implements Listener {

        private ThenHandler handler;

        ResultListener(ThenHandler h) {
            handler = h;
        }

        @Override
        public Promise handle(PromiseEvent e) {
            Result r = e.result;
            if (r == null) {
                return null;
            }
            return this.handler.handle(r);
        }
    }

    private static class ErrorListener implements Listener {

        private final CatchHandler handler;

        ErrorListener(CatchHandler h) {
            handler = h;
        }

        @Override
        public Promise handle(PromiseEvent e) {
            Reason r = e.error;
            if (r == null) {
                return null;
            }
            return this.handler.handle(r);
        }
    }

    private static class FinallyListener implements Listener {

        private final FinallyHandler handler;

        FinallyListener(FinallyHandler h) {
            handler = h;
        }

        @Override
        public Promise handle(PromiseEvent e) {
            this.handler.handle();
            return null;
        }
    }

}
