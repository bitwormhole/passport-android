package com.bitwormhole.passport;

import android.app.Activity;
import android.os.Bundle;

import com.bitwormhole.passport.contexts.PassportClientHolder;

public class VerifyPinCodeActivity extends Activity {

    private final PassportClientHolder clientHolder = new PassportClientHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_verify_pin_code);
    }

}
