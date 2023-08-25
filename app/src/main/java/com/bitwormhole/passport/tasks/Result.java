package com.bitwormhole.passport.tasks;

public class Result<T> {

    private T value;

    public Result(T d) {
        this.value = d;
    }

    public T getValue() {
        return value;
    }

}
