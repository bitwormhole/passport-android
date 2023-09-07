package com.bitwormhole.passport;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.bitwormhole.passport.components.bo.UserSpaceBO;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.contexts.IPassportApplication;
import com.bitwormhole.passport.data.db.RootDatabase;
import com.bitwormhole.passport.data.db.UserDatabase;
import com.bitwormhole.passport.services.UserSpaceService;
import com.bitwormhole.passport.web.dto.UserDTO;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class UserDatabaseTest {


    @Test
    public void useUserDB() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        IPassportApplication pa = PassportApplication.getInstance(appContext);

        UserSpaceBO user = new UserSpaceBO();
        user.domain = "passport.example.com";
        user.email = "foo@example.com";

        UserSpaceService spaces = pa.getServices().getUserSpaces();
        UserSpace space = spaces.getManager().getSpace(user).get();
        UserDatabase db = pa.getServices().getDatabases().loadUserDB(space);

        db.credentialDao();
    }

}
