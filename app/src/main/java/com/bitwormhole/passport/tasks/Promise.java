package com.bitwormhole.passport.tasks;

public final class Promise<T> {

    private static TaskContext context;

    private final Chain chain;

    private Promise(Chain c) {
        this.chain = c;
    }

    public static <T> Promise<T> resolve(Result<T> r) {
        Chain chain = new DefaultChain(context);
        Promise p = new Promise(chain);
        p.chain.dispatch(new PromiseEvent(r));
        return p;
    }

    public static <T> Promise<T> reject(Reason r) {
        Chain chain = new DefaultChain(context);
        Promise p = new Promise(chain);
        p.chain.dispatch(new PromiseEvent(r));
        return p;
    }

    public static <T> Promise<T> execute(Task t) {
        Chain chain = new DefaultChain(context);
        Promise p = new Promise(chain);
        p.chain.addTask(t);
        return p;
    }

    public Promise<T> onThen(ThenHandler<T> h) {
        chain.addThen(h);
        return this;
    }

    public Promise<T> onCatch(CatchHandler<T> h) {
        this.chain.addCatch(h);
        return this;
    }

    public Promise<T> onFinally(FinallyHandler h) {
        this.chain.addFinally(h);
        return this;
    }

    public Chain getChain() {
        return chain;
    }

    public static void bind(TaskContext ctx) {
        if (ctx == null) {
            return;
        }
        context = ctx;
    }

}
