package com.bitwormhole.passport.tasks;

public class PromiseEvent<T> {

    public final Reason error;

    public final Result<T> result;

    // public Promise<T> source;
    // public PromiseEvent() {  }

    public PromiseEvent(Exception exp) {
        this.error = new Reason(exp);
        this.result = null;
    }

    public PromiseEvent(T data) {
        this.result = new Result<>(data);
        this.error = null;
    }

    public PromiseEvent(Result<T> r) {
        this.result = r;
        this.error = null;
    }

    public PromiseEvent(Reason r) {
        this.error = r;
        this.result = null;
    }

}
