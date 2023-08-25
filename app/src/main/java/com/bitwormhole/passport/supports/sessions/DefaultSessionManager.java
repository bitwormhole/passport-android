package com.bitwormhole.passport.supports.sessions;

import com.bitwormhole.passport.contexts.IClient;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.services.SessionManager;
import com.bitwormhole.passport.web.dto.SessionDTO;

public class DefaultSessionManager implements SessionManager {

    public ISession current;
    public IClient client;

    public DefaultSessionManager() {
    }

    @Override
    public void setCurrent(ISession s) {
        if (s == null) {
            s = this.open(new SessionDTO());
        }
        this.current = s;
    }

    @Override
    public ISession getCurrent() {
        ISession s = this.current;
        if (s == null) {
            s = this.open(new SessionDTO());
            this.current = s;
        }
        return s;
    }

    @Override
    public ISession open(SessionDTO o) {
        if (o == null) {
            o = new SessionDTO();
        }
        return new DefaultSession(client, o);
    }

}
