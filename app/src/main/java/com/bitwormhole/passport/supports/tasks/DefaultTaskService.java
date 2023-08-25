package com.bitwormhole.passport.supports.tasks;

import android.os.Handler;

import com.bitwormhole.passport.services.TaskService;

public class DefaultTaskService implements TaskService {

    private Handler handler;

    @Override
    public void init(Handler h) {
        this.handler = h;
    }

    @Override
    public void runWithWorkerThread(Runnable r) {
        Thread t = new Thread(r);
        t.start();
    }

    @Override
    public void runWithUIThread(Runnable r) {
        this.handler.post(r);
    }
}
