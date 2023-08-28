package com.bitwormhole.passport.supports.keys.aes;

import android.util.Base64;

import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.components.security.SecretKeyLoader;
import com.bitwormhole.passport.web.dto.SecretKeyDTO;

import javax.crypto.spec.SecretKeySpec;

public class AesKeyLoader implements SecretKeyLoader {

    @Override
    public SecretKeyHolder load(SecretKeyDTO o) {
        final String algorithm = AESDriver.ALGORITHM;
        final byte[] key = this.getKeyData(o);
        SecretKeySpec spec = new SecretKeySpec(key, algorithm);
        return new AesKeyHolder(spec);
    }


    private byte[] getKeyData(SecretKeyDTO o) {
        byte[] data = null;
        if (o == null) {
            return null;
        }
        data = o.data;
        if (data != null) {
            return data;
        }
        String b64 = o.data64;
        if (b64 == null) {
            return null;
        }
        data = Base64.decode(b64, Base64.DEFAULT);
        o.data = data;
        return data;
    }
}
