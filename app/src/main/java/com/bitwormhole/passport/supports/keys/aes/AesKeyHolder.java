package com.bitwormhole.passport.supports.keys.aes;

import android.util.Base64;

import com.bitwormhole.passport.components.security.Decrypter;
import com.bitwormhole.passport.components.security.Encryptor;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.web.dto.EncryptedDTO;
import com.bitwormhole.passport.web.dto.SecretKeyDTO;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AesKeyHolder implements SecretKeyHolder, Decrypter, Encryptor {

    private static final String transformation = "AES/CBC/PKCS5Padding";


    private final SecretKey key;


    public AesKeyHolder(SecretKey key) {
        this.key = key;
    }

    @Override
    public Decrypter getDecrypter() {
        return this;
    }

    @Override
    public Encryptor getEncryptor() {
        return this;
    }

    @Override
    public SecretKeyDTO export() {
        SecretKey k = this.key;
        SecretKeyDTO o = new SecretKeyDTO();
        o.data = k.getEncoded();
        o.algorithm = k.getAlgorithm();
        o.format = k.getFormat();
        o.data64 = Base64.encodeToString(o.data, Base64.DEFAULT);
        return o;
    }


    @Override
    public void decrypt(EncryptedDTO o) {
        byte[] iv = o.iv;
        if (iv == null) {
            iv = new byte[16];
        }
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, this.key, new IvParameterSpec(iv));
            o.plain = cipher.doFinal(o.encrypted);
            // o.iv = cipher.getIV();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void encrypt(EncryptedDTO o) {
        byte[] iv = o.iv;
        if (iv == null) {
            iv = new byte[16];
        }
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, this.key, new IvParameterSpec(iv));
            o.encrypted = cipher.doFinal(o.plain);
            o.iv = cipher.getIV();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
