package com.bitwormhole.passport.supports.keys.rsa;

import com.bitwormhole.passport.components.security.PublicKeyDriver;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

final class RsaKey {

    String alias;

    PublicKey keyPublic;

    PrivateKey keyPrivate;

    Certificate cert;

    KeyStore store;

}
