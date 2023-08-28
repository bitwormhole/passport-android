package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.components.userspace.UserSpace;

public class SessionFacade implements ISession {

    private final SessionContext context;

    public SessionFacade(SessionContext ctx) {
        this.context = ctx;
    }

    @Override
    public IClient getClient() {
        return this.context.getParent().facade;
    }

    @Override
    public String getUserDomain() {
        return null;
    }

    @Override
    public String getUserEmail () {
        return null;
    }

    @Override
    public UserSpace getUserSpace() {
        return null;
    }

    @Override
    public void reload() {

    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}
