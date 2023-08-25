package com.bitwormhole.passport.tasks;

public interface Task<T> {

    Result<T> run() throws Exception;

}
