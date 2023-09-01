package com.bitwormhole.passport.supports.keys.rsa;

import com.bitwormhole.passport.components.security.Decrypter;
import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.PublicKeyHolder;
import com.bitwormhole.passport.components.security.Signer;

import java.security.KeyStoreException;

public class RsaKeyPairHolder implements KeyPairHolder {

    private final RsaKey key;

    public RsaKeyPairHolder(RsaKey k) {
        this.key = k;
    }

    @Override
    public String getAlias() {
        return this.key.alias;
    }

    @Override
    public PublicKeyHolder getPublicKey() {
        return new RsaPublicKeyHolder(this.key);
    }

    @Override
    public void delete() {
        try {
            if (!key.store.containsAlias(key.alias)) {
                return;
            }
            RsaKey k = this.key;
            k.store.deleteEntry(k.alias);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Signer getSignatureSigner() {


        return new  RsaSigner( this.key )  ;
    }

    @Override
    public Decrypter getDecrypter() {
        return  new RsaDecrypter ( this.key  ) ;
    }
}
