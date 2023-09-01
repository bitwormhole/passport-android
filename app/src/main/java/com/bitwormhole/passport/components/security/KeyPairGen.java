package com.bitwormhole.passport.components.security;

public interface KeyPairGenerator {

    KeyPairHolder generate(String alias);

}
