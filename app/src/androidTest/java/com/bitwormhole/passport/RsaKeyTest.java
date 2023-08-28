package com.bitwormhole.passport;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bitwormhole.passport.services.PublicKeyService;
import com.bitwormhole.passport.services.SecretKeyService;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class RsaKeyTest {
    @Test
    public void useRSA() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        PublicKeyService keyService = PassportApplication.getInstance(appContext).getServices().getPublicKeys();


        // assertEquals("com.bitwormhole.passport", appContext.getPackageName());
    }
}
