package com.bitwormhole.passport.services;

import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.web.dto.SessionDTO;

public interface SessionManager {

    void setCurrent(ISession s);

    ISession getCurrent();

    ISession open(SessionDTO o);

}
