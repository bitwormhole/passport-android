package com.bitwormhole.passport;


import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.bitwormhole.passport.contexts.IPassportApplication;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.services.LoginService;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Test
    public void useAppContext() throws IOException {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        IPassportApplication app = PassportApplication.getInstance(appContext);

        LoginService.Params p = new LoginService.Params();
        p.domain = "www.bitwormhole.com";
        p.email = "test@local";
        p.password = "123".toCharArray();

        LoginService ser = app.getServices().getLoginService();
        ISession session = ser.connect(p);

        ser.reconnect(session);

        //  assertEquals("com.bitwormhole.passport", appContext.getPackageName());
    }
}
