package com.bitwormhole.passport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;

import com.bitwormhole.passport.components.bo.UserSpaceBO;
import com.bitwormhole.passport.contexts.IPassportApplication;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.data.db.RootDatabase;
import com.bitwormhole.passport.data.entity.UserSpaceEntity;
import com.bitwormhole.passport.tasks.Promise;
import com.bitwormhole.passport.tasks.TaskContext;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Optional;

public class StartingActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_starting);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TaskContext tc = Promise.createTaskContext(this);
        Promise.execute(tc, UserSpaceBO.class, () -> {
            return boot();
        }).onThen((res) -> {
            this.jumpToHome();
            return null;
        }).onCatch((res) -> {
            this.jumpToLogin();
            return null;
        }).onFinally(() -> {
            return;
        });
        Promise.start(tc);
    }

    private void jumpToHome() {
        Intent i = new Intent(this, LoginStep1Activity.class);
        startActivity(i);
    }

    private void jumpToLogin() {
        Intent i = new Intent(this, LoginStep1Activity.class);
        startActivity(i);
    }

    private UserSpaceBO boot() {
        IPassportApplication app = PassportApplication.getInstance(this);
        ISession current = app.getServices().getSessions().openCurrent();
        UserSpaceBO bo = new UserSpaceBO();
        bo.domain = current.getUserDomain();
        bo.email = current.getUserEmail();
        bo.enabled = current.exists();
        return bo;
    }

}
