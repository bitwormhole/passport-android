package com.bitwormhole.passport.supports.keys.rsa;

import com.bitwormhole.passport.components.security.Decrypter;
import com.bitwormhole.passport.web.dto.EncryptedDTO;

import java.security.spec.MGF1ParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class RsaDecrypter implements Decrypter {

    private final RsaKey key;

    public RsaDecrypter(RsaKey key) {
        this.key = key;
    }

    @Override
    public void decrypt(EncryptedDTO o) {
        try {
            Cipher cipher = RsaDriver.getCipher();
            OAEPParameterSpec spec = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA1, PSource.PSpecified.DEFAULT);
            cipher.init(Cipher.DECRYPT_MODE, this.key.keyPrivate, spec);
            o.plain = cipher.doFinal(o.encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
