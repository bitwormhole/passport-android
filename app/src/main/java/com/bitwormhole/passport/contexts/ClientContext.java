package com.bitwormhole.passport.contexts;

import android.content.Context;

import com.bitwormhole.passport.components.security.KeyDriverManager;
import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.data.converters.Converters;
import com.bitwormhole.passport.data.converters.SecretKeyConverter;
import com.bitwormhole.passport.data.converters.UserSpaceConverter;
import com.bitwormhole.passport.data.db.RootDatabase;
import com.bitwormhole.passport.services.DatabaseService;
import com.bitwormhole.passport.services.JWTClientService;
import com.bitwormhole.passport.services.LoginService;
import com.bitwormhole.passport.services.ProfileService;
import com.bitwormhole.passport.services.PublicKeyService;
import com.bitwormhole.passport.services.RestClientService;
import com.bitwormhole.passport.services.SecretKeyService;
import com.bitwormhole.passport.services.ServerDiscoverService;
import com.bitwormhole.passport.services.Services;
import com.bitwormhole.passport.services.SessionManager;
import com.bitwormhole.passport.services.TaskService;
import com.bitwormhole.passport.services.UUIDGenerater;
import com.bitwormhole.passport.services.UserSpaceService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ClientContext {

    public final IClient facade;
    public final List<Object> components;
    public final IClientComponentsLoader loader;

    public Context context;

    public KeyPairHolder keyPair;

    public SecretKeyHolder secretKey;

    public RootDatabase database;

    public ISession currentSession;


    // converters
    public Converters converters;

    public UserSpaceConverter userSpaceConverter;
    public SecretKeyConverter secretKeyConverter;


    // services
    public Services services;

    public SessionManager sessions;
    public RestClientService rest;
    public JWTClientService jwt;
    public IPassportApplication app;
    public TaskService tasks;
    public PublicKeyService publicKeys;
    public SecretKeyService secretKeys;
    public KeyDriverManager keyDrivers;
    public DatabaseService databases;
    public UserSpaceService userSpaces;
    public UUIDGenerater uuidGenerater;
    public LoginService loginService;
    public ServerDiscoverService serverDiscoverService;
    public ProfileService profiles;

    public ClientContext() {
        List<Object> clist = new ArrayList<>();
        this.components = Collections.synchronizedList(clist);
        this.facade = new ClientFacade(this);
        this.loader = new ClientComponentsLoaderImpl(this);
    }

}
