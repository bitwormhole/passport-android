package com.bitwormhole.passport;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;

import com.bitwormhole.passport.lifecycle.LifeManager;

public class BaseActivity extends Activity {

    private final LifeManager lifeManager = new LifeManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    protected final OnCreateContext onCreateBegin(Bundle savedInstanceState, PersistableBundle persistentState) {
        //  IPassportApplication app = PassportApplication.getInstance(this);
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
