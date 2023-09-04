package com.bitwormhole.passport.supports.keys.rsa;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import com.bitwormhole.passport.components.security.KeyPairGen;
import com.bitwormhole.passport.components.security.KeyPairHolder;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class RsaKeyGen implements KeyPairGen {


    @Override
    public KeyPairHolder generate(String alias) {
        try {
            return this.inTryGen(alias);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private KeyPairHolder inTryGen(String alias) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, KeyStoreException, CertificateException, IOException {


        // store
        KeyStore ks = RsaDriver.getStore();
        if (ks.containsAlias(alias)) {
            throw new SecurityException("an older entry in store, with alias:" + alias);
        }

        // gen
        KeyPairGenerator kpg = null;
        int purposes = KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY | KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT;
        KeyGenParameterSpec.Builder kgpsBuilder = new KeyGenParameterSpec.Builder(alias, purposes);
        kgpsBuilder.setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512);
        kgpsBuilder.setKeySize(1024 * 2);
        kgpsBuilder.setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1);
        kgpsBuilder.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1);
        // kgpsBuilder.setRandomizedEncryptionRequired(true);


        kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, RsaDriver.StoreName);
        kpg.initialize(kgpsBuilder.build());
        KeyPair kp = kpg.generateKeyPair();


        // cert
        Certificate cert = ks.getCertificate(alias);

        // result
        RsaKey rk = new RsaKey();
        rk.alias = alias;
        rk.store = ks;
        rk.keyPrivate = kp.getPrivate();
        rk.keyPublic = kp.getPublic();
        rk.cert = cert;
        return new RsaKeyPairHolder(rk);
    }
}
