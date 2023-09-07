package com.bitwormhole.passport.supports.keys.rsa;

import com.bitwormhole.passport.components.bo.SignatureBO;
import com.bitwormhole.passport.components.security.Verifier;
import com.bitwormhole.passport.web.dto.SignatureDTO;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

public class RsaVerifier implements Verifier {

    private final RsaKey key;

    public RsaVerifier(RsaKey key) {
        this.key = key;
    }


    @Override
    public void verify(SignatureBO o) throws SecurityException {
        o.ok = false;
        try {
            this.doVerify(o);
        } catch (Exception e) {
            throw new SecurityException(e);
        }
        if (!o.ok) {
            throw new SecurityException("bad signature");
        }
    }


    private void doVerify(SignatureBO o) throws SignatureException, InvalidKeyException, NoSuchAlgorithmException {
        String algorithm = RsaDriver.SignatureAlgorithm;
        Signature s = Signature.getInstance(algorithm);
        s.initVerify(this.key.keyPublic);
        s.update(o.data);
        boolean ok = s.verify(o.signature);
        o.ok = ok;
    }

}
