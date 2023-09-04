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
import java.security.Key;
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
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
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
public class RsaCryptTest {

    @Test
    public void useRsaCrypt() {
        try {
            KeyPair kp = getKeyPair4crypt();
            this.inTryCrypt(kp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    private KeyPair getKeyPair4crypt() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, KeyStoreException, CertificateException, IOException, SignatureException, InvalidKeyException, UnrecoverableKeyException {

        String storeName = "AndroidKeyStore";
        String alias = "RsaCryptTest.inTryCrypt";
        KeyPairGenerator kpg;

        // gen key-pair
        int purposes = KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY | KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT;
        KeyGenParameterSpec.Builder kgpsBuilder = new KeyGenParameterSpec.Builder(alias, purposes);
        kgpsBuilder.setDigests(KeyProperties.DIGEST_SHA256);

        // kgpsBuilder.setRandomizedEncryptionRequired(false);
        // kgpsBuilder.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE);
        kgpsBuilder.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1);
        kgpsBuilder.setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1);
        kgpsBuilder.setKeySize(1024 * 2);
        kgpsBuilder.setBlockModes(KeyProperties.BLOCK_MODE_ECB);


        kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, storeName);
        kpg.initialize(kgpsBuilder.build());
        KeyPair kp = kpg.generateKeyPair();


        KeyStore store = KeyStore.getInstance(storeName);
        store.load(null);
        Certificate cert = store.getCertificate(alias);
        Key sikey = store.getKey(alias, null);
        cert.getPublicKey();
        Log.d("rsa-key-pair", "cert = " + cert + "");

        this.trySignWithPrivateKey((PrivateKey) sikey);

        return kp;
    }

    private void trySignWithPrivateKey(PrivateKey key) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        byte[] data = "rsa-sign-data".getBytes();
        Signature s = Signature.getInstance("SHA256WithRSA");
        s.initSign(key);
        s.update(data);
        byte[] signature = s.sign();
        Log.i("rsa-sign", signature + "");
    }


    private void inTryCrypt(KeyPair kp) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, KeyStoreException, CertificateException, IOException, UnrecoverableEntryException {

        String transformation = "RSA/ECB/PKCS1Padding";

        // encrypt
        EncryptedDTO o1 = new EncryptedDTO();
        o1.plain = "hello,world".getBytes();
        Cipher cipher1 = Cipher.getInstance(transformation);
        cipher1.init(Cipher.ENCRYPT_MODE, kp.getPublic());
        o1.encrypted = cipher1.doFinal(o1.plain);

        // decrypt
        EncryptedDTO o2 = new EncryptedDTO();
        o2.encrypted = o1.encrypted;
        Cipher cipher2 = Cipher.getInstance(transformation);
        cipher2.init(Cipher.DECRYPT_MODE, kp.getPrivate());
        o2.plain = cipher2.doFinal(o2.encrypted);

        // compare
        assertArrayEquals(o1.plain, o2.plain);
    }
}
