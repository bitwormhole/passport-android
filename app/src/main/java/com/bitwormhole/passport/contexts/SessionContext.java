package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.data.db.RootDatabase;
import com.bitwormhole.passport.data.db.UserDatabase;

public class SessionContext {

    public final ClientContext clientContext;
    public final IClient clientInterface;
    public final ISession facade;
    public final ISessionComponentsLoader loader;

    public String domain;
    public String email;

    public UserDatabase database;
    public KeyPairHolder keyPair;
    public SecretKeyHolder secretKey;
    public UserSpace space;


    public SessionContext(ClientContext parent) {
        this.facade = new SessionFacade(this);
        this.clientContext = parent;
        this.clientInterface = parent.facade;
        this.loader = new SessionComponentsLoaderImpl(this);
    }

}
