package com.bitwormhole.passport.supports.keys.rsa;

import com.bitwormhole.passport.components.bo.CryptBO;
import com.bitwormhole.passport.components.security.Encryptor;
import com.bitwormhole.passport.web.dto.EncryptedDTO;

import java.security.spec.MGF1ParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class RsaEncryptor implements Encryptor {
    private final RsaKey key;

    public RsaEncryptor(RsaKey key) {
        this.key = key;
    }

    @Override
    public void encrypt(CryptBO o) {
        try {
            Cipher cipher = RsaDriver.getCipher();
            // OAEPParameterSpec spec = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA1, PSource.PSpecified.DEFAULT);
            cipher.init(Cipher.ENCRYPT_MODE, this.key.keyPublic);
            o.encrypted = cipher.doFinal(o.plain);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
