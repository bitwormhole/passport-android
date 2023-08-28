package com.bitwormhole.passport.supports.keys.rsa;

import com.bitwormhole.passport.components.security.KeyDriverRegistration;
import com.bitwormhole.passport.components.security.KeyDriverRegistry;
import com.bitwormhole.passport.components.security.KeyPairGenerator;
import com.bitwormhole.passport.components.security.KeyPairLoader;
import com.bitwormhole.passport.components.security.PublicKeyDriver;
import com.bitwormhole.passport.components.security.PublicKeyLoader;

public class RsaDriver implements KeyDriverRegistry, PublicKeyDriver {
    @Override
    public KeyDriverRegistration[] ListRegistrations() {
        return new KeyDriverRegistration[0];
    }

    @Override
    public PublicKeyLoader getPublicKeyLoader() {
        return null;
    }

    @Override
    public KeyPairLoader getKeyPairLoader() {
        return null;
    }

    @Override
    public KeyPairGenerator getKeyPairGenerator() {
        return null;
    }
}
