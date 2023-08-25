package com.bitwormhole.passport.contexts;

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
    public String getUserName() {
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
