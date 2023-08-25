package com.bitwormhole.passport.lifecycle;

import android.os.Bundle;
import android.os.PersistableBundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LifeManager implements OnCreateHandler, OnStartHandler, OnResumeHandler, OnPauseHandler, OnStopHandler, OnRestartHandler, OnDestroyHandler {

    private final List<Life.Registration> handlers;

    public LifeManager() {
        this.handlers = new ArrayList<>();
    }

    public void add(Life.Registry lr) {
        if (lr == null) {
            return;
        }
        Life.Registration[] all = lr.listLifeRegistrations();
        if (all == null) {
            return;
        }
        for (Life.Registration item : all) {
            if (item == null) {
                continue;
            }
            this.handlers.add(item);
        }
    }


    private interface ForEachFunc {
        void invoke(Life.Registration lr);
    }

    private void forEach(boolean reverse, ForEachFunc fn) {
        List<Life.Registration> list = new ArrayList<>(this.handlers);
        if (reverse) {
            Collections.reverse(list);
        }
        for (Life.Registration lr : list) {
            forLR(lr, fn);
        }
    }

    private void forLR(Life.Registration lr, ForEachFunc fn) {
        if (lr == null || fn == null) {
            return;
        }
        try {
            fn.invoke(lr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        this.forEach(false, (lr) -> {
            OnCreateHandler h = lr.onCreate;
            if (h == null) {
                return;
            }
            h.handleCreate(savedInstanceState, persistentState);
        });
    }


    @Override
    public void handlePause() {
        this.forEach(true, (lr) -> {
            OnPauseHandler h = lr.onPause;
            if (h == null) {
                return;
            }
            h.handlePause();
        });
    }

    @Override
    public void handleRestart() {
        this.forEach(false, (lr) -> {
            OnRestartHandler h = lr.onRestart;
            if (h == null) {
                return;
            }
            h.handleRestart();
        });
    }

    @Override
    public void handleResume() {
        this.forEach(false, (lr) -> {
            OnResumeHandler h = lr.onResume;
            if (h == null) {
                return;
            }
            h.handleResume();
        });
    }

    @Override
    public void handleStart() {
        this.forEach(false, (lr) -> {
            OnStartHandler h = lr.onStart;
            if (h == null) {
                return;
            }
            h.handleStart();
        });
    }

    @Override
    public void handleStop() {
        this.forEach(true, (lr) -> {
            OnStopHandler h = lr.onStop;
            if (h == null) {
                return;
            }
            h.handleStop();
        });
    }

    @Override
    public void handleDestroy() {
        this.forEach(true, (lr) -> {
            OnDestroyHandler h = lr.onDestroy;
            if (h == null) {
                return;
            }
            h.handleDestroy();
        });
    }
}
