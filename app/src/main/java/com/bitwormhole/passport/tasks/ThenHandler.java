package com.bitwormhole.passport.tasks;

public interface ThenHandler<T> {

    Promise<T> handle(Result<T> r);

}
