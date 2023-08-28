package com.bitwormhole.passport.services;

import com.bitwormhole.passport.components.security.PublicKeyDriver;
import com.bitwormhole.passport.components.security.SecretKeyDriver;

public interface PublicKeyService {

    PublicKeyDriver findDriver(String algorithm);

}
