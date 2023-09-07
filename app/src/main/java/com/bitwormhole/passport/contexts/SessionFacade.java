package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.data.db.RootDatabase;
import com.bitwormhole.passport.data.db.UserDatabase;
import com.bitwormhole.passport.data.dxo.UserSpaceID;

public class SessionFacade implements ISession {

    private final SessionContext context;

    public SessionFacade(SessionContext ctx) {
        this.context = ctx;
    }

    @Override
    public IClient getClient() {
        return context.clientInterface;
    }

    @Override
    public String getUserDomain() {
        return context.domain;
    }

    @Override
    public String getUserEmail() {
        return context.email;
    }

    @Override
    public UserSpace getUserSpace() {
        UserSpace us = context.space;
        if (us == null) {
            us = context.loader.loadUserSpace();
            context.space = us;
        }
        return us;
    }

    @Override
    public UserDatabase getDatabase() {
        UserDatabase db = context.database;
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

    @Override
    public void reload() {

    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }

    @Override
    public void saveAsCurrent() {
        UserSpace space = this.getUserSpace();
        UserSpaceID id = space.id();
        this.getClient().getServices().getUserSpaces().setCurrent(id);
    }
}
