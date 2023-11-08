package com.bitwormhole.passport.supports.keys.rsa;

import com.bitwormhole.passport.components.security.PublicKeyHolder;
import com.bitwormhole.passport.components.security.PublicKeyLoader;
import com.bitwormhole.passport.web.dto.PublicKeyDTO;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class RsaPublicKeyLoader implements PublicKeyLoader {
    @Override
    public PublicKeyHolder load(PublicKeyDTO data) {
        RsaKey rk = new RsaKey();
        rk.alias = "";
        rk.store = null;
        rk.cert = null;
        rk.keyPrivate = null;
        try {
            rk.keyPublic = this.loadPublicKey(data.getKeyData());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new RsaPublicKeyHolder(rk);
    }

    private PublicKey loadPublicKey(byte[] data) throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        return factory.generatePublic(spec);
    }
}
