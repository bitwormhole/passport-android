package com.bitwormhole.passport.components.security;

public interface KeyPairLoader {

    KeyPairHolder load(String alias);

}
