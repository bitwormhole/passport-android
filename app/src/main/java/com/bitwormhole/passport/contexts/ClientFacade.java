package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.services.JWTClientService;
import com.bitwormhole.passport.services.KeyPairService;
import com.bitwormhole.passport.services.RestClientService;
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
    public JWTClientService getJWT() {
        return this.context.jwt;
    }

    @Override
    public KeyPairService getKeyPairs() {
        return context.keyPairs;
    }

    @Override
    public RestClientService getRest() {
        return this.context.rest;
    }

    @Override
    public SessionManager getSessions() {
        return context.sessions;
    }

    @Override
    public ISession getCurrentSession() {
        return context.sessions.getCurrent();
    }

    @Override
    public TaskService getTasks() {
        return context.tasks;
    }
}
