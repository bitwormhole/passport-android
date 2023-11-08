package com.bitwormhole.passport.tasks;

import android.app.Activity;

import com.bitwormhole.passport.supports.tasks.DefaultTaskContext;

public final class Promise<T> {

    private final PromiseInner<T> inner;

    private Promise(TaskContext ctx, Class<T> t) {
        PromiseInner.Builder b = new PromiseInner.Builder<>();
        b.type = t;
        b.context = ctx;
        b.chain = prepareChain(b);
        this.inner = b.create();
    }

    private static <T> Chain<T> prepareChain(PromiseInner.Builder<T> b) {
        final Class<T> type = b.type;
        final String key = "chain:" + type.getName();
        final TaskContext ctx = b.context;
        Chain<T> ch = null;
        Object obj = ctx.attr(key);
        if (obj instanceof Chain) {
            ch = (Chain<T>) obj;
            return ch;
        }
        ch = new DefaultChain<>(ctx, type);
        ctx.setAttr(key, ch);
        return ch;
    }


    public static <T> Promise<T> resolve(TaskContext ctx, Class<T> t, T r) {
        Promise<T> p = new Promise<>(ctx, t);
        p.inner.result = r;
        return p;
    }

    public static <T> Promise<T> reject(TaskContext ctx, Class<T> t, Throwable err) {
        Promise<T> p = new Promise<>(ctx, t);
        p.inner.error = err;
        return p;
    }

    public static <T> PromiseHolder<T> pend(TaskContext ctx, Class<T> t) {
        Promise<T> p = new Promise<>(ctx, t);
        return new myHolder(p);
    }

    private final static class myHolder<T> implements PromiseHolder<T> {

        final Promise<T> mPromise;

        myHolder(Promise<T> p) {
            mPromise = p;
        }

        @Override
        public Promise<T> get() {
            return this.mPromise;
        }

        @Override
        public void dispatchError(Throwable err) {
            this.mPromise.inner.error = err;
            this.mPromise.inner.chain.dispatchError(err);
        }

        @Override
        public void dispatchResult(T value) {
            Class<T> t = this.mPromise.inner.type;
            this.mPromise.inner.result = value;
            this.mPromise.inner.chain.dispatchResult(t, value);
        }
    }


    public static <T> Promise<T> execute(TaskContext ctx, Class<T> t, Task<T> task) {
        Promise<T> p = new Promise<>(ctx, t);
        p.inner.chain.addTask(task);
        return p;
    }

    public Promise<T> onThen(ThenHandler<T> h) {
        this.inner.chain.addThen(h);
        return this;
    }

    public Promise<T> onCatch(CatchHandler<T> h) {
        this.inner.chain.addCatch(h);
        return this;
    }

    public Promise<T> onFinally(FinallyHandler h) {
        this.inner.chain.addFinally(h);
        return this;
    }

    PromiseInner<T> unwrap() {
        return this.inner;
    }

    public static TaskContext createTaskContext(Activity a) {
        return new DefaultTaskContext(a);
    }

    public static void start(TaskContext ctx) {
        Thread t = new Thread(() -> {
            ctx.worker().run();
        });
        t.start();
    }
}
