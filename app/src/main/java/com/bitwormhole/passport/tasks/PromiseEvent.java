package com.bitwormhole.passport.tasks;

public class PromiseEvent<T> {

    public final Throwable error;

    public final T result;
    public final Class<T> type;


    public PromiseEvent(Throwable exp) {
        this.error = exp;
        this.result = null;
        this.type = null;
    }

    public PromiseEvent(Class<T> t, T data) {
        this.result = data;
        this.error = null;
        this.type = t;
    }

    public boolean isError() {
        return this.error != null;
    }

    public boolean isResult() {
        return this.type != null;
    }

}
