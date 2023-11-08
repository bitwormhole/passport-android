package com.bitwormhole.passport.utils;

import android.app.Activity;
import android.view.View;

public final class GuiBinder {

    private final Activity activity;

    private GuiBinder(Activity a) {
        this.activity = a;
    }

    public static GuiBinder getInstance(Activity a) {
        return new GuiBinder(a);
    }

    public void setupOnClickListener(int id, View.OnClickListener l) {
        l = new OnClickListenerProxy(l);
        this.activity.findViewById(id).setOnClickListener(l);
    }

    ////////////////////
    // private

    private class OnClickListenerProxy implements View.OnClickListener {

        private final View.OnClickListener target;

        OnClickListenerProxy(View.OnClickListener l) {
            this.target = l;
        }

        @Override
        public void onClick(View view) {
            try {
                this.target.onClick(view);
            } catch (Exception e) {
                handleException(e);
            }
        }
    }

    private void handleException(Exception e) {
    }

}
