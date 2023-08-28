package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.supports.ServicesImpl;
import com.bitwormhole.passport.supports.db.DatabaseServiceImpl;
import com.bitwormhole.passport.supports.keys.KeyDriverManagerImpl;
import com.bitwormhole.passport.supports.keys.PublicKeyServiceImpl;
import com.bitwormhole.passport.supports.keys.SecretKeyServiceImpl;
import com.bitwormhole.passport.supports.keys.aes.AESDriver;
import com.bitwormhole.passport.supports.keys.rsa.RsaDriver;
import com.bitwormhole.passport.supports.rest.DefaultRestClient;
import com.bitwormhole.passport.supports.sessions.DefaultSessionManager;
import com.bitwormhole.passport.supports.sessions.UserSpaceServiceImpl;
import com.bitwormhole.passport.supports.tasks.DefaultTaskService;
import com.bitwormhole.passport.supports.tokens.DefaultTokenManager;

import java.util.ArrayList;
import java.util.List;

public final class PassportClientBuilder {

    public final IPassportApplication app;
    private ClientContext client;

    // components:

    ServicesImpl services;
    DefaultTokenManager tokens;
    DefaultRestClient rest;
    PublicKeyServiceImpl publicKeys;
    SecretKeyServiceImpl secretKeys;
    KeyDriverManagerImpl keyDrivers;
    AESDriver keyDriverAES;
    RsaDriver keyDriverRSA;
    DefaultSessionManager sessions;
    DefaultTaskService tasks;
    UserSpaceServiceImpl userSpaces;
    DatabaseServiceImpl databases;


    public PassportClientBuilder(IPassportApplication app) {
        this.app = app;
    }


    public IClient create() {
        this.createComponents();
        this.createContext();
        this.listComponents();
        this.initComponents();
        return this.client.facade;
    }


    private void initComponents() {
        final ClientContext cc = this.client;
        List<Object> list = this.client.components;
        list = new ArrayList<>(list);
        list.forEach((o) -> {
            if (o instanceof IComponent) {
                IComponent com = (IComponent) o;
                com.init(cc);
            }
        });
    }


    private void createComponents() {
        // c-list:
        services = new ServicesImpl();
        tokens = new DefaultTokenManager();
        rest = new DefaultRestClient();
        publicKeys = new PublicKeyServiceImpl();
        secretKeys = new SecretKeyServiceImpl();
        keyDrivers = new KeyDriverManagerImpl();
        keyDriverAES = new AESDriver();
        keyDriverRSA = new RsaDriver();
        sessions = new DefaultSessionManager();
        tasks = new DefaultTaskService();
        databases = new DatabaseServiceImpl();
        userSpaces = new UserSpaceServiceImpl();
    }


    private void listComponents() {
        final ClientContext cc = this.client;
        final List<Object> cl = cc.components;
        // c-list:
        cl.add(cc.services);
        cl.add(cc.jwt);
        cl.add(cc.rest);

        cl.add(cc.publicKeys);
        cl.add(cc.secretKeys);
        cl.add(cc.keyDrivers);
        cl.add(this.keyDriverAES);
        cl.add(this.keyDriverRSA);

        cl.add(cc.sessions);
        cl.add(cc.app);
        cl.add(cc.tasks);
        cl.add(cc.userSpaces);
        cl.add(cc.databases);
    }


    private void createContext() {
        ClientContext cc = new ClientContext();

        cc.context = this.app.getContext();

        // c-list:
        cc.services = this.services;
        cc.jwt = this.tokens;
        cc.rest = this.rest;
        cc.publicKeys = this.publicKeys;
        cc.secretKeys = this.secretKeys;
        cc.keyDrivers = this.keyDrivers;
        cc.sessions = this.sessions;
        cc.app = this.app;
        cc.tasks = this.tasks;
        cc.userSpaces = this.userSpaces;
        cc.databases = this.databases;

        this.client = cc;
    }

}
