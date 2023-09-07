package com.bitwormhole.passport.supports.sessions;

import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.IClient;
import com.bitwormhole.passport.contexts.IComponent;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.contexts.SessionContext;
import com.bitwormhole.passport.services.SessionManager;
import com.bitwormhole.passport.web.dto.SessionDTO;
import com.bitwormhole.passport.web.dto.UserDTO;

public class DefaultSessionManager implements SessionManager, IComponent {

    private ClientContext context;

    public DefaultSessionManager() {
    }


    @Override
    public ISession openCurrent() {
        return null;
    }

    @Override
    public ISession open(UserDTO o) {
        if (o == null) {
            o = new UserDTO();
        }
        SessionContext ctx = new SessionContext(context);
        ctx.domain = o.domain;
        ctx.email = o.email;
        return new DefaultSession(ctx);
    }

    @Override
    public void init(ClientContext cc) {
        context = cc;
    }
}
