package com.bitwormhole.passport.supports.keys;

import com.bitwormhole.passport.components.bo.CryptBO;
import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.PublicKeyHolder;
import com.bitwormhole.passport.components.security.SecretKeyDriver;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.data.db.RootDatabase;
import com.bitwormhole.passport.data.db.UserDatabase;
import com.bitwormhole.passport.data.dxo.Hex;
import com.bitwormhole.passport.data.entity.SecretKeyEntity;
import com.bitwormhole.passport.web.dto.EncryptedDTO;
import com.bitwormhole.passport.web.dto.SecretKeyDTO;
import com.google.android.gms.common.util.Base64Utils;

import java.util.List;

public class NativeSecretKeyLoader {

    private final ISession session;

    public NativeSecretKeyLoader(ISession se) {
        this.session = se;
    }

    public SecretKeyHolder loadByKeyPair(KeyPairHolder kp) {

        Hex fingerprint = kp.getPublicKey().getFingerprint();
        UserDatabase db = session.getDatabase();
        List<SecretKeyEntity> list = db.secretKeyDao().listByPublicKeyFingerprint(fingerprint.toString());

        for (SecretKeyEntity item : list) {
            if (item.nativeKey) {
                // load
                return this.loadKey(item, kp);
            }
        }

        // generate
        SecretKeyDriver driver = this.findDriver("AES");
        SecretKeyHolder sk = driver.getGenerator().generate();
        SecretKeyEntity ent = new SecretKeyEntity();
        ent.publicKeyFingerprint = fingerprint.toString();
        ent.nativeKey = true;
        this.saveKey(ent, kp, sk);
        return sk;
    }

    private void saveKey(SecretKeyEntity ent, KeyPairHolder kp, SecretKeyHolder sk) {


    }

    private SecretKeyHolder loadKey(SecretKeyEntity ent, KeyPairHolder kp) {
        CryptBO de = new CryptBO();
        de.encrypted = Base64Utils.decode(ent.encryptedKey);
        kp.getDecrypter().decrypt(de);
        SecretKeyDTO sk = new SecretKeyDTO();
        sk.data = de.plain;
        SecretKeyDriver driver = this.findDriver("AES");
        return driver.getLoader().load(sk);
    }

    private SecretKeyDriver findDriver(String algorithm) {
        return session.getClient().getServices().getSecretKeys().findDriver(algorithm);
    }
}
