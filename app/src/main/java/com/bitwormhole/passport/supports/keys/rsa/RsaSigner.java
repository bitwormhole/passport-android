package com.bitwormhole.passport.supports.keys.rsa;

import com.bitwormhole.passport.components.bo.SignatureBO;
import com.bitwormhole.passport.components.security.Signer;
import com.bitwormhole.passport.web.dto.SignatureDTO;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

public class RsaSigner implements Signer {

    private final RsaKey key;

    public RsaSigner(RsaKey key) {
        this.key = key;
    }


    @Override
    public void sign(SignatureBO s) throws SecurityException {
        try {
            this.doSign(s);
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    private void doSign(SignatureBO o) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        String algorithm = RsaDriver.SignatureAlgorithm;
        Signature s = Signature.getInstance(algorithm);
        s.initSign(this.key.keyPrivate);
        s.update(o.data);
        o.signature = s.sign();
        o.ok = false;
    }
}
