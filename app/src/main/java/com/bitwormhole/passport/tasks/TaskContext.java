package com.bitwormhole.passport.tasks;


public interface TaskContext {

    void runWithWorkerThread(Runnable r);

    void runWithUIThread(Runnable r);

    Runnable worker();

    Object attr(String key);

    void setAttr(String key, Object value);

    void setMainErrorHandler(ErrorHandler h);

    ErrorHandler getMainErrorHandler();

}
