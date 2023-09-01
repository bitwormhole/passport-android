package com.bitwormhole.passport.components.security;

public interface PublicKeyDriver extends KeyDriver {

    PublicKeyLoader getPublicKeyLoader();

    KeyPairLoader getKeyPairLoader();

    KeyPairGen getKeyPairGenerator();

    boolean containsAlias(String alias);

}
