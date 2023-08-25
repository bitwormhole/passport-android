package com.bitwormhole.passport;

import android.app.Application;
import android.content.Context;

import com.bitwormhole.passport.contexts.IClient;
import com.bitwormhole.passport.contexts.IPassportApplication;
import com.bitwormhole.passport.contexts.PassportClientBuilder;

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

    private IClient initClient() {
        PassportClientBuilder pcb = new PassportClientBuilder();
        pcb.app = this;
        pcb.init();
        pcb.bind();
        return pcb.create();
    }

    public static IPassportApplication getInstance(Context c) {
        Context ac = c.getApplicationContext();
        return (IPassportApplication) ac;
    }

}
