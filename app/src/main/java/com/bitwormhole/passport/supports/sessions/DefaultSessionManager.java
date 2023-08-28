package com.bitwormhole.passport.supports.sessions;

import com.bitwormhole.passport.contexts.IClient;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.services.SessionManager;
import com.bitwormhole.passport.web.dto.SessionDTO;
import com.bitwormhole.passport.web.dto.UserDTO;

public class DefaultSessionManager implements SessionManager {

    public ISession current;
    public IClient client;

    public DefaultSessionManager() {
    }

    //  @Override
    public void setCurrent(ISession s) {
        if (s == null) {
            s = this.open(new UserDTO());
        }
        this.current = s;
    }

    @Override
    public ISession getCurrent() {
        ISession s = this.current;
        if (s == null) {
            s = this.open(new UserDTO());
            this.current = s;
        }
        return s;
    }

    @Override
    public ISession open(UserDTO o) {
        if (o == null) {
            o = new UserDTO();
        }
        return new DefaultSession(client, o);
    }

}
