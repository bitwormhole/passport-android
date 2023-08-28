package com.bitwormhole.passport.components.security;

public interface SecretKeyDriver extends KeyDriver {

    SecretKeyLoader getLoader();

    SecretKeyGenerator getGenerator();

}
