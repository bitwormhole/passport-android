package com.bitwormhole.passport.supports.keys.aes;

import com.bitwormhole.passport.components.security.SecretKeyGenerator;
import com.bitwormhole.passport.components.security.SecretKeyHolder;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AesKeyGenerator implements SecretKeyGenerator {

    @Override
    public SecretKeyHolder generate() {
        KeyGenerator keygen = null;
        try {
            keygen = KeyGenerator.getInstance(AESDriver.ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keygen.init(256);
        SecretKey key = keygen.generateKey();
        return new AesKeyHolder(key);
    }
}
