package com.bitwormhole.passport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class HomeActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_home);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //      Intent i = new Intent(this, StartingActivity.class);
        //    this.startActivity(i);
    }
}
