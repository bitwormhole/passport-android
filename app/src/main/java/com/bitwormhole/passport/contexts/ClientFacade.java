package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.components.bo.UserSpaceBO;
import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.data.converters.Converters;
import com.bitwormhole.passport.data.db.RootDatabase;
import com.bitwormhole.passport.services.JWTClientService;
import com.bitwormhole.passport.services.RestClientService;
import com.bitwormhole.passport.services.Services;
import com.bitwormhole.passport.services.SessionManager;
import com.bitwormhole.passport.services.TaskService;
import com.bitwormhole.passport.services.UserSpaceService;

import java.util.Optional;

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
    public Optional<ISession> getCurrentSession() {
        ISession current = context.currentSession;
        if (current == null) {
            Optional<ISession> res = context.loader.loadCurrentSession();
            if (!res.isPresent()) {
                return res;
            }
            current = res.get();
            context.currentSession = current;
        }
        return Optional.of(current);
    }

    @Override
    public ISession openSession(UserSpaceBO want) {
        UserSpaceService spaces = context.services.getUserSpaces();
        Optional<UserSpace> res = spaces.getManager().getSpace(want);
        UserSpace have = null;
        if (res.isPresent()) {
            have = res.get();
        } else {
            have = spaces.getManager().initSpace(want);
        }
        return context.loader.loadSession(have);
    }

    @Override
    public Services getServices() {
        return context.services;
    }

    @Override
    public Converters getConverters() {
        return context.converters;
    }

    @Override
    public RootDatabase getDatabase() {
        RootDatabase db = context.database;
        if (db == null) {
            db = context.loader.loadDatabase();
            context.database = db;
        }
        return db;
    }

    @Override
    public KeyPairHolder getKeyPair() {
        KeyPairHolder h = context.keyPair;
        if (h == null) {
            h = context.loader.loadKeyPair();
            context.keyPair = h;
        }
        return h;
    }

    @Override
    public SecretKeyHolder getSecretKey() {
        SecretKeyHolder h = context.secretKey;
        if (h == null) {
            h = context.loader.loadSecretKey();
            context.secretKey = h;
        }
        return h;
    }

}
