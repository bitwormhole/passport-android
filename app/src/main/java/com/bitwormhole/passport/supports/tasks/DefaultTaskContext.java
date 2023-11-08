package com.bitwormhole.passport.supports.tasks;

import android.app.Activity;

import com.bitwormhole.passport.tasks.TaskContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultTaskService implements TaskContext {

    private final Activity mActivity;
    private final List<Runnable> mTasks;

    public DefaultTaskService(Activity a) {
        List<Runnable> list = new ArrayList<>();
        mActivity = a;
        mTasks = Collections.synchronizedList(list);
    }

    private void run() {
    }

    @Override
    public void runWithWorkerThread(Runnable r) {
    }

    @Override
    public void runWithUIThread(Runnable r) {
        mActivity.runOnUiThread(r);
    }

    @Override
    public void start() {
    }
}
