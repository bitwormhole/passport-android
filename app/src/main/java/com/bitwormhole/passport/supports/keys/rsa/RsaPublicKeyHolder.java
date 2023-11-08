package com.bitwormhole.passport.supports.keys.rsa;

import com.bitwormhole.passport.components.security.Encryptor;
import com.bitwormhole.passport.components.security.PublicKeyHolder;
import com.bitwormhole.passport.components.security.Verifier;
import com.bitwormhole.passport.data.dxo.Hex;
import com.bitwormhole.passport.utils.BinaryUtils;
import com.bitwormhole.passport.utils.HashUtils;
import com.bitwormhole.passport.web.dto.PublicKeyDTO;

import java.security.PublicKey;

public class RsaPublicKeyHolder implements PublicKeyHolder {

    private final RsaKey key;

    public RsaPublicKeyHolder(RsaKey k) {
        this.key = k;
    }

    @Override
    public PublicKeyDTO export() {
        PublicKeyDTO dst = new PublicKeyDTO();
        PublicKey src = this.key.keyPublic;
        dst.algorithm = src.getAlgorithm();
        dst.format = src.getFormat();
        dst.setKeyData(src.getEncoded());
        return dst;
    }

    @Override
    public Verifier getSignatureVerifier() {
        return new RsaVerifier(this.key);
    }

    @Override
    public Encryptor getEncryptor() {
        return new RsaEncryptor(this.key);
    }

    @Override
    public Hex getFingerprint() {
        byte[] enc = this.key.keyPublic.getEncoded();
        byte[] sum = HashUtils.computeSHA256sum(enc);
        return new Hex(sum);
    }

}
