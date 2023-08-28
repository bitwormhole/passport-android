package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.services.JWTClientService;
import com.bitwormhole.passport.services.RestClientService;
import com.bitwormhole.passport.services.Services;
import com.bitwormhole.passport.services.SessionManager;
import com.bitwormhole.passport.services.TaskService;

public class ClientFacade implements IClient {

    private final ClientContext context;

    public ClientFacade(ClientContext ctx) {
        this.context = ctx;
    }

    @Override
    public IPassportApplication getPassportApp() {
        return context.app;
    }


    @Override
    public ISession getCurrentSession() {
        return context.sessions.getCurrent();
    }

    @Override
    public Services getServices() {
        return context.services;
    }

}
