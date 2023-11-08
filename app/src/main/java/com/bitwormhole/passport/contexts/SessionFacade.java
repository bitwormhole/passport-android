package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.components.bo.UserSpaceBO;
import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.components.userspace.UserSpaceManager;
import com.bitwormhole.passport.data.db.RootDatabase;
import com.bitwormhole.passport.data.db.UserDatabase;
import com.bitwormhole.passport.data.dxo.UserSpaceID;
import com.bitwormhole.passport.services.RestClientService;
import com.bitwormhole.passport.utils.Attributes;
import com.bitwormhole.passport.utils.HashAttribute;
import com.bitwormhole.passport.utils.Mode;
import com.bitwormhole.passport.web.RestClient;

import java.util.Optional;

public class SessionFacade implements ISession {

    private final SessionContext context;

    public SessionFacade(SessionContext ctx) {
        this.context = ctx;
    }

    @Override
    public Attributes attributes() {
        Attributes att = context.attributes;
        if (att == null) {
            att = new HashAttribute(Mode.Safe);
            context.attributes = att;
        }
        return att;
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
    public Optional<UserSpace> getUserSpace() {
        UserSpace us = context.space;
        if (us != null) {
            return Optional.of(us);
        }
        Optional<UserSpace> opt = context.loader.loadUserSpace();
        if (!opt.isPresent()) {
            return Optional.empty();
        }
        us = opt.get();
        context.space = us;
        return Optional.of(us);
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
    public RestClient getRESTClient() {
        RestClient rest = context.rest;
        if (rest == null) {
            RestClientService clients = context.facade.getClient().getServices().getRestClients();
            rest = clients.createNewClient(this);
            context.rest = rest;
        }
        return rest;
    }

    @Override
    public void reload() {

    }

    @Override
    public void load() {

    }

    @Override
    public void reconnect() {

    }

    @Override
    public void keepAlive() {

    }

    @Override
    public void save() {

    }

    @Override
    public void saveAsCurrent() {
        UserSpaceManager spaceManager = this.getClient().getServices().getUserSpaces().getManager();
        Optional<UserSpace> spaceOpt = this.getUserSpace();
        UserSpace space = null;
        if (spaceOpt.isPresent()) {
            space = spaceOpt.get();
        } else {
            UserSpaceBO bo = new UserSpaceBO();
            bo.domain = context.domain;
            bo.email = context.email;
            space = spaceManager.initSpace(bo);
        }
        spaceManager.setCurrentSpace(space);
        context.clientContext.currentSession = this;
    }

    @Override
    public boolean exists() {
        return context.exists;
    }

    @Override
    public boolean enabled() {
        return context.enabled;
    }
}
