package com.bitwormhole.passport;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bitwormhole.passport.components.bo.CryptBO;
import com.bitwormhole.passport.components.security.SecretKeyDriver;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.services.SecretKeyService;
import com.bitwormhole.passport.web.dto.EncryptedDTO;
import com.bitwormhole.passport.web.dto.SecretKeyDTO;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AesKeyTest {
    @Test
    public void useAES() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SecretKeyService keyService = PassportApplication.getInstance(appContext).getServices().getSecretKeys();
        SecretKeyDriver driver = keyService.findDriver("aes");


        CryptBO o1 = new CryptBO();
        o1.plain = this.makePlainData((100 * 1024) + 3);
        o1.iv = this.makePlainData(16);
        SecretKeyDTO ko = this.tryEncrypt(driver, o1);

        CryptBO o2 = new CryptBO();
        o2.iv = o1.iv;
        o2.encrypted = o1.encrypted;
        this.tryDecrypt(driver, ko, o2);

        assertArrayEquals(o1.plain, o2.plain);
    }

    private byte[] makePlainData(int size) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        Random rand = new Random();
        for (; b.size() < size; ) {
            int n = rand.nextInt();
            b.write(n);
        }
        return b.toByteArray();
    }

    private SecretKeyDTO tryEncrypt(SecretKeyDriver driver, CryptBO o) {
        SecretKeyHolder key = driver.getGenerator().generate();
        key.getEncryptor().encrypt(o);
        return key.export();
    }

    private void tryDecrypt(SecretKeyDriver driver, SecretKeyDTO ko, CryptBO o) {
        SecretKeyHolder key = driver.getLoader().load(ko);
        key.getDecrypter().decrypt(o);
    }

}
