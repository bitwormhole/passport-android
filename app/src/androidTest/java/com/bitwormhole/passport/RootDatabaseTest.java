package com.bitwormhole.passport;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.bitwormhole.passport.components.bo.UserSpaceBO;
import com.bitwormhole.passport.contexts.IPassportApplication;
import com.bitwormhole.passport.data.dao.UserDao;
import com.bitwormhole.passport.data.dao.UserSpaceDao;
import com.bitwormhole.passport.data.db.RootDatabase;
import com.bitwormhole.passport.data.entity.UserEntity;
import com.bitwormhole.passport.data.entity.UserSpaceEntity;
import com.bitwormhole.passport.web.dto.UserSpaceDTO;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class RootDatabaseTest {

    @Test
    public void useRootDB() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        IPassportApplication pa = PassportApplication.getInstance(appContext);
        RootDatabase db = pa.getServices().getDatabases().getRootDB();
        UserSpaceDao ud = db.userSpaceDao();

        long now = System.currentTimeMillis();

        UserSpaceEntity user = new UserSpaceEntity();
        user.domain = "example.com";
        user.email = "user-" + now + "@example.com";
        ud.insertAll(user);

        UserSpaceEntity[] all = ud.listAll();
        for (UserSpaceEntity o : all) {
            System.out.println(o.id);
        }
    }
}
