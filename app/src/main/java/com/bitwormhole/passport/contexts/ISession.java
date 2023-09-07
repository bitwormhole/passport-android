package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.data.db.UserDatabase;

public interface ISession {

    IClient getClient();

    String getUserDomain();

    String getUserEmail();

    UserSpace getUserSpace();

    UserDatabase getDatabase();

    KeyPairHolder getKeyPair();

    SecretKeyHolder getSecretKey();

    void reload();

    void load();

    void save();

    void saveAsCurrent();

}
