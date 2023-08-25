package com.bitwormhole.passport.contexts;

public class SessionContext {

    private final ClientContext client;
    private final ISession facade;

    public SessionContext(ClientContext parent) {
        this.facade = new SessionFacade(this);
        this.client = parent;
    }

    public ClientContext getParent() {
        return this.client;
    }

    public ISession getFacade() {
        return this.facade;
    }

}
