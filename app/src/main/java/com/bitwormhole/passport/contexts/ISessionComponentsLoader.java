package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.data.db.UserDatabase;

public interface ISessionComponentsLoader {

    KeyPairHolder loadKeyPair();

    SecretKeyHolder loadSecretKey();

    UserSpace loadUserSpace();

    UserDatabase loadDatabase();
}
