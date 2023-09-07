package com.bitwormhole.passport;


import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.bitwormhole.passport.components.bo.UserSpaceBO;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.contexts.IPassportApplication;
import com.bitwormhole.passport.data.dxo.Timestamp;
import com.bitwormhole.passport.services.UserSpaceService;
import com.bitwormhole.passport.web.dto.UserSpaceDTO;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class UserSpaceTest {


    @Test
    public void useUserSpaceService() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        IPassportApplication app = PassportApplication.getInstance(appContext);
        UserSpaceService ser = app.getServices().getUserSpaces();

        UserSpace have = null;
        UserSpaceBO want = new UserSpaceBO();
        want.email = "test@local";
        want.domain = "test.local";

        Optional<UserSpace> haveOpt = ser.getManager().getSpace(want);
        if (haveOpt.isPresent()) {
            have = haveOpt.get();
        } else {
            have = ser.getManager().initSpace(want);
        }

        assertEquals(have.email(), want.email);
    }

}
