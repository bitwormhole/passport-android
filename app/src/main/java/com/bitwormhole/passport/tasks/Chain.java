package com.bitwormhole.passport.tasks;

public interface Chain<T> {

    void addTask(Task<T> t);

    void addThen(ThenHandler<T> h);

    void addCatch(CatchHandler<T> h);

    void addFinally(FinallyHandler h);

    void dispatch(PromiseEvent evt);

    PromiseEvent getCurrentEvent();

}
