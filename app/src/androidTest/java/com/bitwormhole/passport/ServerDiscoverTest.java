package com.bitwormhole.passport;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.bitwormhole.passport.contexts.IPassportApplication;
import com.bitwormhole.passport.services.ServerDiscoverService;
import com.bitwormhole.passport.web.dto.ServiceDTO;
import com.bitwormhole.passport.web.vo.ServicesVO;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class ServerDiscoverTest {

    @Test
    public void useResolveServerLocation() throws IOException {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        IPassportApplication app = PassportApplication.getInstance(appContext);
        ServerDiscoverService ser = app.getServices().getServerDiscoverService();

        ServicesVO result = ser.resolve("www.bitwormhole.com");
        List<ServiceDTO> all = result.services;
        for (ServiceDTO s : all) {
        }

        // assertEquals("com.bitwormhole.passport", appContext.getPackageName());
    }


}
