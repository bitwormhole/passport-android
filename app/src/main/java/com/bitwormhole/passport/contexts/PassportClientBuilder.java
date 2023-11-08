package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.profiles.ProfileServiceImpl;
import com.bitwormhole.passport.supports.ServicesImpl;
import com.bitwormhole.passport.supports.converting.ConverterGetter;
import com.bitwormhole.passport.supports.converting.SecretKeyConverterImpl;
import com.bitwormhole.passport.supports.converting.UserSpaceConverterImpl;
import com.bitwormhole.passport.supports.db.DatabaseServiceImpl;
import com.bitwormhole.passport.supports.db.UUIDGeneraterImpl;
import com.bitwormhole.passport.supports.keys.KeyDriverManagerImpl;
import com.bitwormhole.passport.supports.keys.PublicKeyServiceImpl;
import com.bitwormhole.passport.supports.keys.SecretKeyServiceImpl;
import com.bitwormhole.passport.supports.keys.aes.AESDriver;
import com.bitwormhole.passport.supports.keys.rsa.RsaDriver;
import com.bitwormhole.passport.supports.rest.RestClients;
import com.bitwormhole.passport.supports.sessions.DefaultSessionManager;
import com.bitwormhole.passport.supports.sessions.LoginServiceImpl;
import com.bitwormhole.passport.supports.sessions.ServerDiscoverServiceImpl;
import com.bitwormhole.passport.supports.sessions.UserSpaceServiceImpl;
import com.bitwormhole.passport.supports.tokens.DefaultTokenManager;

import java.util.ArrayList;
import java.util.List;

public final class PassportClientBuilder {

    public final IPassportApplication app;
    private ClientContext client;

    // components:

    ServicesImpl services;
    DefaultTokenManager tokens;
    RestClients rest;
    PublicKeyServiceImpl publicKeys;
    SecretKeyServiceImpl secretKeys;
    KeyDriverManagerImpl keyDrivers;
    AESDriver keyDriverAES;
    RsaDriver keyDriverRSA;
    DefaultSessionManager sessions;

    UserSpaceServiceImpl userSpaces;
    DatabaseServiceImpl databases;
    UUIDGeneraterImpl uuidGen;
    ProfileServiceImpl profiles;
    LoginServiceImpl loginService;
    ServerDiscoverServiceImpl serverDiscoverService;
    ConverterGetter converters;
    SecretKeyConverterImpl secretKeyConverter;
    UserSpaceConverterImpl userSpaceConverter;


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
        rest = new RestClients();
        publicKeys = new PublicKeyServiceImpl();
        secretKeys = new SecretKeyServiceImpl();
        keyDrivers = new KeyDriverManagerImpl();
        keyDriverAES = new AESDriver();
        keyDriverRSA = new RsaDriver();
        sessions = new DefaultSessionManager();
        databases = new DatabaseServiceImpl();
        userSpaces = new UserSpaceServiceImpl();
        converters = new ConverterGetter();
        userSpaceConverter = new UserSpaceConverterImpl();
        secretKeyConverter = new SecretKeyConverterImpl();
        uuidGen = new UUIDGeneraterImpl();
        loginService = new LoginServiceImpl();
        serverDiscoverService = new ServerDiscoverServiceImpl();
        profiles = new ProfileServiceImpl();
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
        cl.add(cc.userSpaces);
        cl.add(cc.databases);

        cl.add(cc.converters);
        cl.add(cc.secretKeyConverter);
        cl.add(cc.userSpaceConverter);
        cl.add(cc.uuidGenerater);
        cl.add(cc.serverDiscoverService);
        cl.add(cc.loginService);
        cl.add(cc.profiles);
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
        cc.userSpaces = this.userSpaces;
        cc.databases = this.databases;
        cc.converters = this.converters;
        cc.secretKeyConverter = this.secretKeyConverter;
        cc.userSpaceConverter = this.userSpaceConverter;
        cc.uuidGenerater = this.uuidGen;
        cc.serverDiscoverService = this.serverDiscoverService;
        cc.loginService = this.loginService;
        cc.profiles = this.profiles;

        this.client = cc;
    }

}
