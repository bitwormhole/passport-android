package com.bitwormhole.passport.tasks;

public class Result<T> {

    private final T value;
    private final Class<T> valueType;

    public Result(Class<T> t, T d) {
        this.valueType = t;
        this.value = d;
    }

    public T getValue() {
        return value;
    }

    public Class<T> getType() {
        return valueType;
    }
}
