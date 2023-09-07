package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.data.db.RootDatabase;

import java.util.Optional;

public interface IClientComponentsLoader {

    KeyPairHolder loadKeyPair();

    SecretKeyHolder loadSecretKey();

    RootDatabase loadDatabase();

    Optional<ISession> loadCurrentSession();

    ISession loadSession(UserSpace have);
}
