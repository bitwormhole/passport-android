package com.bitwormhole.passport.supports.sessions;

import com.bitwormhole.passport.components.bo.UserSpaceBO;
import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.IClient;
import com.bitwormhole.passport.contexts.IComponent;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.contexts.SessionContext;
import com.bitwormhole.passport.data.converters.UserSpaceConverter;
import com.bitwormhole.passport.data.db.RootDatabase;
import com.bitwormhole.passport.data.entity.UserSpaceEntity;
import com.bitwormhole.passport.services.SessionManager;
import com.bitwormhole.passport.utils.ResultSet;
import com.bitwormhole.passport.web.dto.SessionDTO;
import com.bitwormhole.passport.web.dto.UserDTO;
import com.bitwormhole.passport.web.dto.UserSpaceDTO;

public class DefaultSessionManager implements SessionManager, IComponent {

    private ClientContext context;

    public DefaultSessionManager() {
    }


    private UserSpaceBO loadCurrentUserInfo() {
        RootDatabase db = context.facade.getDatabase();
        UserSpaceEntity[] all = db.userSpaceDao().listAll();
        UserSpaceBO user = new UserSpaceBO();
        user.email = "foo@bar";
        user.domain = "undefine";
        final String want = UserSpaceBO.SelectionCurrent;
        for (UserSpaceEntity ent : all) {
            if (want.equals(ent.selection)) {
                user.domain = ent.domain;
                user.email = ent.email;
                user.selection = ent.selection;
                user.enabled = ent.enabled;
                user.exists = true;
            }
        }
        return user;
    }


    @Override
    public ISession openCurrent() {
        ISession session = context.currentSession;
        if (session != null) {
            return session;
        }
        UserSpaceBO user = this.loadCurrentUserInfo();
        session = this.open(user);
        context.currentSession = session;
        return session;
    }

    @Override
    public ISession open(UserSpaceBO o) {
        if (o == null) {
            o = new UserSpaceBO();
        }
        SessionContext ctx = new SessionContext(context);
        ctx.domain = o.domain;
        ctx.email = o.email;
        ctx.enabled = o.enabled;
        ctx.exists = o.exists;
        return new DefaultSession(ctx);
    }

    @Override
    public void init(ClientContext cc) {
        context = cc;
    }
}
