package com.bitwormhole.passport.contexts;

import android.app.Activity;

import java.util.Optional;

public class PassportClientHolder {

    private IClient client;

    public PassportClientHolder() {
    }

    public IClient getClient(Activity a) {
        IClient c = this.client;
        if (c == null) {
            IPassportApplication app = (IPassportApplication) a.getApplication();
            c = app.getClient();
            this.client = c;
        }
        return c;
    }

    public Optional<ISession> getSession(Activity a) {
        IClient c = this.getClient(a);
        return c.getCurrentSession();
    }

}
