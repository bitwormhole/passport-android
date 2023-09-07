package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.components.bo.UserSpaceBO;
import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.data.converters.Converters;
import com.bitwormhole.passport.data.db.RootDatabase;
import com.bitwormhole.passport.data.db.UserDatabase;
import com.bitwormhole.passport.services.Services;

import java.util.Optional;

public interface IClient {

    IPassportApplication getPassportApp();

    Optional<ISession> getCurrentSession();

    ISession openSession(UserSpaceBO space);

    Services getServices();

    Converters getConverters();

    RootDatabase getDatabase();

    KeyPairHolder getKeyPair();

    SecretKeyHolder getSecretKey();

}
