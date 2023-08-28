package com.bitwormhole.passport.supports.keys.aes;

import com.bitwormhole.passport.components.security.KeyDriverRegistration;
import com.bitwormhole.passport.components.security.KeyDriverRegistry;
import com.bitwormhole.passport.components.security.SecretKeyDriver;
import com.bitwormhole.passport.components.security.SecretKeyGenerator;
import com.bitwormhole.passport.components.security.SecretKeyLoader;

public class AESDriver implements SecretKeyDriver, KeyDriverRegistry {

    public static final String ALGORITHM = "AES";


    @Override
    public SecretKeyLoader getLoader() {
        return new AesKeyLoader();
    }

    @Override
    public SecretKeyGenerator getGenerator() {
        return new AesKeyGenerator();
    }

    @Override
    public KeyDriverRegistration[] ListRegistrations() {
        KeyDriverRegistration r = new KeyDriverRegistration();
        r.name = "AES";
        r.driver = this;
        return new KeyDriverRegistration[]{r};
    }
}
