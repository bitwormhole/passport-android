package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.data.db.UserDatabase;
import com.bitwormhole.passport.utils.Attributes;
import com.bitwormhole.passport.web.RestClient;

import java.util.Optional;

public interface ISession {

    Attributes attributes();

    IClient getClient();

    String getUserDomain();

    String getUserEmail();

    Optional<UserSpace> getUserSpace();

    UserDatabase getDatabase();

    KeyPairHolder getKeyPair();

    SecretKeyHolder getSecretKey();

    RestClient getRESTClient();

    void reload();

    void load();

    void reconnect();

    void keepAlive();

    void save();

    void saveAsCurrent();

    boolean exists();

    boolean enabled();

}
