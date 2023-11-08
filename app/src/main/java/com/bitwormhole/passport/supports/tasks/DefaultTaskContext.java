package com.bitwormhole.passport.supports.tasks;

import android.app.Activity;

import com.bitwormhole.passport.tasks.ErrorHandler;
import com.bitwormhole.passport.tasks.TaskContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultTaskContext implements TaskContext {

    private final Activity mActivity;
    private final Map<String, Object> mAttrs;
    private List<Runnable> mTaskBuffer;
    private ErrorHandler mMainErrorHandler;


    public DefaultTaskContext(Activity a) {

        List<Runnable> list = new ArrayList<>();
        Map<String, Object> attrs = new HashMap<>();

        mAttrs = Collections.synchronizedMap(attrs);
        mTaskBuffer = Collections.synchronizedList(list);
        mActivity = a;
    }


    @Override
    public void runWithWorkerThread(Runnable r) {
        if (r == null) {
            return;
        }
        List<Runnable> buffer = this.mTaskBuffer;
        if (buffer == null) {
            // start right now
            Thread t = new Thread(r);
            t.start();
        } else {
            // start later
            buffer.add(r);
        }
    }

    @Override
    public void runWithUIThread(Runnable r) {
        mActivity.runOnUiThread(r);
    }


    private final class Worker implements Runnable {

        void gan(Runnable t) {
            try {
                if (t != null) {
                    t.run();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            List<Runnable> list = mTaskBuffer;
            mTaskBuffer = null;
            if (list == null) {
                return;
            }
            for (Runnable t : list) {
                this.gan(t);
            }
        }
    }

    @Override
    public Runnable worker() {
        return new Worker();
    }

    @Override
    public Object attr(String key) {
        return mAttrs.get(key);
    }

    @Override
    public void setAttr(String key, Object value) {
        mAttrs.put(key, value);
    }

    @Override
    public void setMainErrorHandler(ErrorHandler h) {
        this.mMainErrorHandler = h;
    }

    @Override
    public ErrorHandler getMainErrorHandler() {
        ErrorHandler h = this.mMainErrorHandler;
        if (h == null) {
            h = new DefaultActivityErrorHandler(this.mActivity);
            this.mMainErrorHandler = h;
        }
        return h;
    }
}
