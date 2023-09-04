package com.bitwormhole.passport;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bitwormhole.passport.supports.keys.rsa.RsaDriver;
import com.bitwormhole.passport.web.dto.EncryptedDTO;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class RsaSignTest {

    @Test
    public void useRsaSign() {
        try {
            KeyPair kp = getKeyPair4sign();
            this.inTrySign(kp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private KeyPair getKeyPair4sign() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {

        String storeName = "AndroidKeyStore";
        String alias = "RsaCryptTest.inTrySign";
        KeyPairGenerator kpg;

        // KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
        // String masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);

        // gen key-pair
        int purposes = KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY;
        KeyGenParameterSpec.Builder kgpsBuilder = new KeyGenParameterSpec.Builder(alias, purposes);
        kgpsBuilder.setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512);

        // kgpsBuilder.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE);
        kgpsBuilder.setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1);

        kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, storeName);
        kpg.initialize(kgpsBuilder.build());
        KeyPair kp = kpg.generateKeyPair();

        return kp;
    }


    private void inTrySign(KeyPair kp) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, KeyStoreException, CertificateException, IOException, UnrecoverableEntryException, SignatureException {

        String algorithm = "SHA256withRSA";
        byte[] data = "hello,rsa-sign-data".getBytes();

        Signature s = Signature.getInstance(algorithm);
        s.initSign(kp.getPrivate());
        s.update(data);
        byte[] signature = s.sign();

        // data[0] = 0;
        // signature[0] = 0;

        Signature s2 = Signature.getInstance(algorithm);
        s2.initVerify(kp.getPublic());
        s2.update(data);
        boolean valid = s2.verify(signature);
        if (valid) {
            Log.i("rsa-sign", "OK");
        } else {
            Log.e("rsa-sign", "bad signature");
        }
    }

}
