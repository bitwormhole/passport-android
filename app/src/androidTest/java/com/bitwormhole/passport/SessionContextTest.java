package com.bitwormhole.passport;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.bitwormhole.passport.components.bo.UserSpaceBO;
import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.contexts.IPassportApplication;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.data.db.UserDatabase;
import com.bitwormhole.passport.services.UserSpaceService;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SessionContextTest {

    @Test
    public void useAppContext() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        IPassportApplication app = PassportApplication.getInstance(appContext);

        Optional<ISession> sessionOpt = app.getClient().getCurrentSession();
        if (!sessionOpt.isPresent()) {
            sessionOpt = this.initCurrentSession(app);
        }
        ISession session = sessionOpt.get();

        UserDatabase db = session.getDatabase();
        KeyPairHolder kp = session.getKeyPair();
        SecretKeyHolder sk = session.getSecretKey();
        UserSpace space = session.getUserSpace();

        Log.i("test-session", "" + db);
        Log.i("test-session", "" + kp);
        Log.i("test-session", "" + sk);
        Log.i("test-session", "" + space);
    }

    private Optional<ISession> initCurrentSession(IPassportApplication app) {
        UserSpaceBO o = new UserSpaceBO();
        o.email = "test@local";
        o.domain = "local.test";
        ISession session = app.getClient().openSession(o);
        session.saveAsCurrent();
        return Optional.of(session);
    }

}
