package com.bitwormhole.passport.tasks;

public interface Task<T> {

    T run() throws Exception;

}
