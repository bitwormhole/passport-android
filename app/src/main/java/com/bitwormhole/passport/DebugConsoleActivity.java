package com.bitwormhole.passport;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bitwormhole.passport.contexts.PassportClientHolder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class DebugConsoleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_console);

        this.findViewById(R.id.button_scan).setOnClickListener((e) -> {
            this.startScanIntent();
        });

        this.findViewById(R.id.button_open_web).setOnClickListener((e) -> {
            this.startWebIntent();
        });

        this.findViewById(R.id.button_login).setOnClickListener((e) -> {
            this.startActivityWithClass(LoginStep1Activity.class);
        });

        this.findViewById(R.id.button_verify_nine_dots).setOnClickListener((e) -> {
            this.startActivityWithClass(VerifyNineDotsActivity.class);
        });

        this.findViewById(R.id.button_verify_pin_code).setOnClickListener((e) -> {
            this.startActivityWithClass(VerifyPinCodeActivity.class);
        });

        this.findViewById(R.id.button_password_display).setOnClickListener((e) -> {
            this.startActivityWithClass(PasswordDisplayActivity.class);
        });

        this.findViewById(R.id.button_scan_qrcode).setOnClickListener((e) -> {
            this.startActivityWithClass(ScanQRCodeActivity.class);
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            // handle scan result
        }
        // else continue with any other code you need in the method
    }

    private void startScanIntent() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    private void startWebIntent() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://im.qq.com/"));
        this.startActivity(i);
    }

    private void startActivityWithClass(Class<?> c) {
        Intent i = new Intent(this, c);
        this.startActivity(i);
    }

}
