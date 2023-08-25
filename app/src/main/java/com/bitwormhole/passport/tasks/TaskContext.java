package com.bitwormhole.passport.tasks;

public interface TaskContext {

    void runWithWorkerThread(Runnable r);

    void runWithUIThread(Runnable r);

}
