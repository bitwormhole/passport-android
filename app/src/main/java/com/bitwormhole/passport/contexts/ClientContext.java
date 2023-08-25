package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.services.JWTClientService;
import com.bitwormhole.passport.services.KeyPairService;
import com.bitwormhole.passport.services.RestClientService;
import com.bitwormhole.passport.services.SessionManager;
import com.bitwormhole.passport.services.TaskService;

public class ClientContext {

    public final IClient facade;

    public SessionManager sessions;
    public RestClientService rest;
    public JWTClientService jwt;
    public KeyPairService keyPairs;
    public IPassportApplication app;
    public TaskService tasks;

    public ClientContext() {
        this.facade = new ClientFacade(this);
    }

}
