package com.bitwormhole.passport;

import android.app.Application;
import android.content.Context;

import com.bitwormhole.passport.contexts.IClient;
import com.bitwormhole.passport.contexts.IPassportApplication;
import com.bitwormhole.passport.contexts.PassportClientBuilder;
import com.bitwormhole.passport.services.Services;

public class PassportApplication extends Application implements IPassportApplication {

    private IClient client;

    @Override
    public IClient getClient() {
        IClient c = this.client;
        if (c == null) {
            c = this.initClient();
            this.client = c;
        }
        return c;
    }

    @Override
    public Services getServices() {
        return this.getClient().getServices();
    }

    @Override
    public Context getContext() {
        return this;
    }

    private IClient initClient() {
        PassportClientBuilder pcb = new PassportClientBuilder(this);
        return pcb.create();
    }

    public static IPassportApplication getInstance(Context c) {
        Context ac = c.getApplicationContext();
        return (IPassportApplication) ac;
    }

}
