package com.bitwormhole.passport;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.contexts.IClient;
import com.bitwormhole.passport.contexts.IPassportApplication;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.data.db.RootDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class ClientContextTest {

    @Test
    public void useClientContext() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        IPassportApplication app = PassportApplication.getInstance(appContext);
        IClient client = app.getClient();

        client.getServices();
        RootDatabase db = client.getDatabase();
        SecretKeyHolder secretkey = client.getSecretKey();
        KeyPairHolder keypair = client.getKeyPair();
        ISession session = client.getCurrentSession().get();

        assertNotNull(db);
        assertNotNull(keypair);
        assertNotNull(secretkey);
        assertNotNull(session);

        //   assertEquals("com.bitwormhole.passport", appContext.getPackageName());
    }

}
