package com.bitwormhole.passport;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.contexts.PassportClientHolder;

public class PasswordDisplayActivity extends Activity {

    private final PassportClientHolder clientHolder = new PassportClientHolder();

    // view
    private TextView mTextViewPassword;
    private TextView mTextViewVCode;

    // data
    private String mPassword;
    private String mVeriCode;
    private boolean mShowPassword;
    private int mPasswordColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_password_display);

        ISession s = clientHolder.getSession(this);
        s.getClient();

        mPassword = "(password)";
        mShowPassword = false;
        mPasswordColor = 0;
        mVeriCode = "(vcode)";

        mTextViewPassword = this.findViewById(R.id.text_password);
        mTextViewVCode = this.findViewById(R.id.text_vcode);

        mTextViewPassword.setOnTouchListener(new myTouchListener());
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.updateDisplay();
    }

    private class myTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (action == MotionEvent.ACTION_MOVE) {
                mShowPassword = true;
                setMotionColor(view, motionEvent);
            } else if (action == MotionEvent.ACTION_UP) {
                mShowPassword = false;
                mPasswordColor = Color.GRAY;
            } else if (action == MotionEvent.ACTION_DOWN) {
                mShowPassword = true;
                setMotionColor(view, motionEvent);
            }
            updateDisplay();
            return true;
        }
    }

    private void setMotionColor(View view, MotionEvent motionEvent) {
        float h = view.getHeight();
        float pos = motionEvent.getY();
        if (h < 1) {
            h = 1;
        }
        float alpha = 1 - (pos / h);
        int n = 127;
        int cc = Color.argb((int) (alpha * n * 2), n, n, n);
        mPasswordColor = cc;
    }

    private void updateDisplay() {

        String pass = mPassword;
        String vc = mVeriCode;
        int color = mPasswordColor;

        if (!mShowPassword) {
            pass = "●●●●●●●●";
        }

        if (color == 0) {
            color = Color.GRAY;
        }

        mTextViewVCode.setText(vc);
        mTextViewPassword.setText(pass);
        mTextViewPassword.setTextColor(color);
    }

}
