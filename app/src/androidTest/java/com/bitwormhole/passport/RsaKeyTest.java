package com.bitwormhole.passport;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bitwormhole.passport.components.bo.CryptBO;
import com.bitwormhole.passport.components.bo.SignatureBO;
import com.bitwormhole.passport.components.security.Decrypter;
import com.bitwormhole.passport.components.security.Encryptor;
import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.PublicKeyDriver;
import com.bitwormhole.passport.components.security.PublicKeyHolder;
import com.bitwormhole.passport.components.security.SecretKeyDriver;
import com.bitwormhole.passport.components.security.Signer;
import com.bitwormhole.passport.components.security.Verifier;
import com.bitwormhole.passport.services.PublicKeyService;
import com.bitwormhole.passport.services.SecretKeyService;
import com.bitwormhole.passport.utils.HashUtils;
import com.bitwormhole.passport.web.dto.EncryptedDTO;
import com.bitwormhole.passport.web.dto.PublicKeyDTO;
import com.bitwormhole.passport.web.dto.SignatureDTO;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class RsaKeyTest {

    @Test
    public void useRSA() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        PublicKeyService keyService = PassportApplication.getInstance(appContext).getServices().getPublicKeys();
        PublicKeyDriver driver = keyService.findDriver("RSA");

        String alias = "example-669";
        KeyPairHolder kp1 = null;
        if (driver.containsAlias(alias)) {
            kp1 = driver.getKeyPairLoader().load(alias);
        } else {
            kp1 = driver.getKeyPairGenerator().generate(alias);
        }

        PublicKeyDTO pkdata = kp1.getPublicKey().export();
        PublicKeyHolder pub = driver.getPublicKeyLoader().load(pkdata);

        String fingerprint1 = kp1.getPublicKey().getFingerprint().toString();
        String fingerprint2 = pub.getFingerprint().toString();

        this.tryCrypt(kp1, pub);
        this.trySign(kp1, pub);

        assertEquals(fingerprint1, fingerprint2);
    }

    private void tryCrypt(KeyPairHolder kp1, PublicKeyHolder pub) {

        byte[] data1 = HashUtils.computeMD5sum("mock-data".getBytes());

        Decrypter dec = kp1.getDecrypter();
        Encryptor enc = pub.getEncryptor();

        CryptBO pack1 = new CryptBO();
        pack1.plain = data1;
        enc.encrypt(pack1);

        CryptBO pack2 = new CryptBO();
        pack2.encrypted = pack1.encrypted;
        dec.decrypt(pack2);
        byte[] data2 = pack2.plain;

        assertArrayEquals(data1, data2);
    }

    private void trySign(KeyPairHolder kp1, PublicKeyHolder pub) {

        Signer sig = kp1.getSignatureSigner();
        Verifier ver = pub.getSignatureVerifier();
        SignatureBO o1 = new SignatureBO();
        SignatureBO o2 = new SignatureBO();

        o1.data = "RsaKeyTest[84]:trySign".getBytes();
        sig.sign(o1);

        o2.data = o1.data;
        o2.signature = o1.signature;
        ver.verify(o2);
    }
}
