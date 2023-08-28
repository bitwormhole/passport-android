package com.bitwormhole.passport.services;

import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.web.dto.SessionDTO;
import com.bitwormhole.passport.web.dto.UserDTO;

public interface SessionManager {

    ISession getCurrent();

    ISession open(UserDTO o);

}
