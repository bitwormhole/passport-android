package com.bitwormhole.passport.services;

import android.os.Handler;

import com.bitwormhole.passport.tasks.TaskContext;

public interface TaskService extends TaskContext {

    void init(Handler h);

}
