package com.bitwormhole.passport.tasks;

public interface PromiseHolder<T> {

    Promise<T> get();

    void dispatchError(Throwable err);

    void dispatchResult(T value);

}
