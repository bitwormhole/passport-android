package com.bitwormhole.passport.supports.tasks;

import android.app.Activity;
import android.app.AlertDialog;

import com.bitwormhole.passport.tasks.ErrorHandler;
import com.bitwormhole.passport.R;

public class DefaultActivityErrorHandler implements ErrorHandler {

    private final Activity mActivity;

    public DefaultActivityErrorHandler(Activity a) {
        this.mActivity = a;
    }

    @Override
    public void handleError(Throwable err) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.mActivity);
        b.setTitle(R.string.error);
        b.setIcon(android.R.drawable.stat_notify_error);
        b.setMessage(err.getLocalizedMessage());
        b.setNegativeButton(R.string.close, (v1, v2) -> {
        });
        b.create().show();
    }
}
