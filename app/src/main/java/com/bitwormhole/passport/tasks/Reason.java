package com.bitwormhole.passport.tasks;

public class Reason {

    private Throwable error;

    public Reason(Throwable e) {
        error = e;
    }

    public Throwable getError() {
        return error;
    }

}
