package com.bitwormhole.passport.tasks;

class PromiseInner<T> {

    // final
    public final TaskContext context;
    public final Class<T> type;
    public final Chain<T> chain;

    // mutable
    public T result;
    public Throwable error;

    private PromiseInner(Builder b) {
        this.type = b.type;
        this.context = b.context;
        this.chain = b.chain;
    }

    public static class Builder<T> {

        public TaskContext context;
        public Class<T> type;
        public Chain<T> chain;

        public PromiseInner<T> create() {
            return new PromiseInner(this);
        }
    }
}
