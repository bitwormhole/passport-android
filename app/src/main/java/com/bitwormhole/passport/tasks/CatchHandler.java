package com.bitwormhole.passport.tasks;

public interface CatchHandler<T> {

    Promise<T> handle(Reason r);

}
