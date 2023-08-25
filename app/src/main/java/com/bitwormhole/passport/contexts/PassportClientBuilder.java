package com.bitwormhole.passport.contexts;

import android.support.v4.os.IResultReceiver;

import com.bitwormhole.passport.supports.keys.DefaultKeyManager;
import com.bitwormhole.passport.supports.rest.DefaultRestClient;
import com.bitwormhole.passport.supports.sessions.DefaultSessionManager;
import com.bitwormhole.passport.supports.tasks.DefaultTaskService;
import com.bitwormhole.passport.supports.tokens.DefaultTokenManager;

public class PassportClientBuilder {

    public DefaultTokenManager tokens;
    public DefaultRestClient rest;
    public DefaultKeyManager keys;
    public DefaultSessionManager sessions;
    public IPassportApplication app;
    public DefaultTaskService tasks;

    private ClientContext client;

    public void init() {

        client = new ClientContext();

        tokens = new DefaultTokenManager();
        rest = new DefaultRestClient();
        keys = new DefaultKeyManager();
        sessions = new DefaultSessionManager();
        tasks = new DefaultTaskService();
    }

    public void bind() {
        sessions.client = this.client.facade;
    }


    public IClient create() {
        ClientContext cc = client;

        cc.jwt = this.tokens;
        cc.rest = this.rest;
        cc.keyPairs = this.keys;
        cc.sessions = this.sessions;
        cc.app = this.app;
        cc.tasks = this.tasks;

        return cc.facade;
    }

}
