package com.bitwormhole.passport.tasks;

final class DefaultChain<T> implements Chain<T> {

    private final TaskContext context;
    private final Class<T> resultType;
    private Node first;
    private Node last;
    //  private NodeHandler currentHandler;

    public DefaultChain(TaskContext ctx, Class<T> t) {
        if (ctx == null) {
            throw new RuntimeException("no TaskContext, use Promise.bind(ctx) first");
        }
        context = ctx;
        resultType = t;
    }

    @Override
    public void addTask(Task t) {
        context.runWithWorkerThread(() -> {
            runTask(t);
        });
    }

    private void runTask(Task<T> t) {
        try {
            T res = t.run();
            this.dispatchResult(this.resultType, res);
        } catch (Exception e) {
            e.printStackTrace();
            this.dispatchError(e);
        }
    }

    @Override
    public void dispatch(PromiseEvent event) {
        context.runWithUIThread(() -> {
            fireAll(null, event);
        });
    }

    @Override
    public void dispatchError(Throwable err) {
        PromiseEvent event = new PromiseEvent(err);
        this.dispatch(event);
    }

    @Override
    public void dispatchResult(Class<T> t, T result) {
        PromiseEvent event = new PromiseEvent(t, result);
        this.dispatch(event);
    }


    private void addNode(Node n) {
        Node tail = this.last;
        if (tail == null) {
            this.first = n;
        } else {
            tail.next = n;
        }
        this.last = n;
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

    // private interface NodeHandler {   void handle(Node node);   }

    private static class Node {
        private Node next;
        private Listener listener; // 这个是一次性的，触发后应该设置为 null
    }


    private void fireAll(Node p, PromiseEvent e) {
        if (p == null) {
            p = this.first;
        }
        for (; p != null; p = p.next) {
            if (e == null) {
                break;
            }
            e = fire(p, e);
        }
        this.handleThrownError(e);
    }


    private void handleThrownError(PromiseEvent event) {
        if (event == null) {
            return;
        }
        Throwable err = event.error;
        if (err == null) {
            return;
        }
        ErrorHandler h = this.context.getMainErrorHandler();
        this.context.runWithUIThread(() -> {
            postThrownError(err, h);
        });
    }

    private void postThrownError(Throwable err, ErrorHandler h) {
        if (err == null || h == null) {
            return;
        }
        try {
            h.handleError(err);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PromiseEvent fire(Node node, PromiseEvent e) {
        if (node == null) {
            return e;
        }
        Listener l = node.listener;
        node.listener = null;
        if (l == null) {
            return e;
        }
        PromiseEvent e2 = null;
        try {
            Promise p = l.handle(e);
            if (p == null) {
                return e;
            }
            PromiseInner inner = p.unwrap();
            if (inner.error != null) {
                e2 = new PromiseEvent(inner.error);
            } else if (inner.type != null) {
                e2 = new PromiseEvent(inner.type, inner.result);
            } else {
                return null; // 暂停触发，等下一波信号
            }
        } catch (Exception err) {
            e2 = new PromiseEvent(err);
        }
        return e2;
    }


    private static interface Listener<T> {
        Promise<T> handle(PromiseEvent<T> e);
    }

    private static class ResultListener<T> implements Listener<T> {

        private final ThenHandler handler;

        ResultListener(ThenHandler h) {
            handler = h;
        }

        @Override
        public Promise<T> handle(PromiseEvent<T> e) {
            if (!e.isResult()) {
                return null;
            }
            return this.handler.handle(e.result);
        }
    }

    private static class ErrorListener implements Listener {

        private final CatchHandler handler;

        ErrorListener(CatchHandler h) {
            handler = h;
        }

        @Override
        public Promise handle(PromiseEvent e) {
            if (!e.isError()) {
                return null;
            }
            return this.handler.handle(e.error);
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
