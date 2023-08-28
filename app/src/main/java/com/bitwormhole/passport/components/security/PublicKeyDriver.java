package com.bitwormhole.passport.components.security;

public interface PublicKeyDriver extends KeyDriver {

    PublicKeyLoader getPublicKeyLoader();

    KeyPairLoader getKeyPairLoader();

    KeyPairGenerator getKeyPairGenerator();

}
