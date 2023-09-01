package com.bitwormhole.passport.supports.keys.rsa;

import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.KeyPairLoader;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class RsaKeyPairLoader implements KeyPairLoader {

    @Override
    public KeyPairHolder load(String alias) {
        try {
            return this.inTryLoad(alias);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private KeyPairHolder inTryLoad(String alias) throws KeyStoreException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException {

        KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
        ks.load(null);
        // Enumeration<String> aliases = ks.aliases();

        if (!ks.containsAlias(alias)) {
            throw new SecurityException("no key with alias:" + alias);
        }

        PrivateKey pk = (PrivateKey) ks.getKey(alias, null);
        Certificate cert = ks.getCertificate(alias);

        if (pk == null) {
            throw new SecurityException("no key with alias:" + alias);
        }
        if (cert == null) {
            throw new SecurityException("no certificate with alias:" + alias);
        }

        RsaKey rk = new RsaKey();
        rk.alias = alias;
        rk.store = ks;
        rk.keyPrivate = pk;
        rk.cert = cert;
        rk.keyPublic = cert.getPublicKey();
        return new RsaKeyPairHolder(rk);
    }


}
