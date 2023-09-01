package com.bitwormhole.passport.supports.keys.rsa;

import com.bitwormhole.passport.components.security.KeyDriverRegistration;
import com.bitwormhole.passport.components.security.KeyDriverRegistry;
import com.bitwormhole.passport.components.security.KeyPairGen;
import com.bitwormhole.passport.components.security.KeyPairLoader;
import com.bitwormhole.passport.components.security.PublicKeyDriver;
import com.bitwormhole.passport.components.security.PublicKeyLoader;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class RsaDriver implements KeyDriverRegistry, PublicKeyDriver {


    public final static String StoreName = "AndroidKeyStore";


    @Override
    public KeyDriverRegistration[] ListRegistrations() {
        KeyDriverRegistration r1 = new KeyDriverRegistration();
        r1.driver = this;
        r1.name = "RSA";
        return new KeyDriverRegistration[]{r1};
    }

    @Override
    public PublicKeyLoader getPublicKeyLoader() {
        return new RsaPublicKeyLoader();
    }

    @Override
    public KeyPairLoader getKeyPairLoader() {
        return new RsaKeyPairLoader();
    }

    @Override
    public KeyPairGen getKeyPairGenerator() {
        return new RsaKeyGen();
    }

    @Override
    public boolean containsAlias(String alias) {
        try {
            KeyStore store = getStore();
            return store.containsAlias(alias);
        } catch (Exception e) {
            return false;
        }
    }

    public static KeyStore getStore() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        KeyStore ks = KeyStore.getInstance(StoreName);
        ks.load(null);
        return ks;
    }

    public static Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        String trans;
        trans = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
        // trans = "RSA/None/NoPadding";
        // trans = "RSA/ECB/OAEPPadding";
        return Cipher.getInstance(trans);
    }

}
