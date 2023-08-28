package com.bitwormhole.passport.services;

import com.bitwormhole.passport.components.security.SecretKeyDriver;

public interface SecretKeyService {

    SecretKeyDriver findDriver(String algorithm);

}
