package com.bitwormhole.passport;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;

import com.bitwormhole.passport.contexts.IPassportApplication;
import com.bitwormhole.passport.lifecycle.LifeManager;
import com.bitwormhole.passport.tasks.Promise;

public class BaseActivity extends Activity {

    private final LifeManager lifeManager = new LifeManager();
    private Handler androidHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    protected final OnCreateContext onCreateBegin(Bundle savedInstanceState, PersistableBundle persistentState) {

        androidHandler = new Handler();

        IPassportApplication app = PassportApplication.getInstance(this);
        app.getClient().getTasks().init(androidHandler);
        Promise.bind(app.getClient().getTasks());

        return new OnCreateContext(savedInstanceState, persistentState);
    }

    protected final void onCreateEnd(OnCreateContext occ) {
        lifeManager.handleCreate(occ.savedInstanceState, occ.persistentState);
    }

    public class OnCreateContext {
        public final Bundle savedInstanceState;
        public final PersistableBundle persistentState;

        public OnCreateContext(Bundle b, PersistableBundle p) {
            this.savedInstanceState = b;
            this.persistentState = p;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifeManager.handleStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        lifeManager.handleRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        lifeManager.handleStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifeManager.handleResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        lifeManager.handlePause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifeManager.handleDestroy();
    }
}
