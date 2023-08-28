package com.bitwormhole.passport.components.security;

public interface KeyPairHolder {

    String getAlias();

    PublicKeyHolder getPublicKey();

    void delete();

    Signer getSignatureSigner();

    Decrypter getDecrypter();

}
