package com.bitwormhole.passport.utils;

import android.util.Log;

public class ThreadChecker {

    public static void checkInUI() {
        String name = Thread.currentThread().getName();
        Log.d("ThreadChecker", "in-thread:" + name);
    }

    public static void checkInWorker() {
        String name = Thread.currentThread().getName();
        Log.d("ThreadChecker", "in-thread:" + name);
    }

}
