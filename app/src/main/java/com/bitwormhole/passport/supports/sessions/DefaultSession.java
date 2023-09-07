package com.bitwormhole.passport.supports.sessions;

import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.IClient;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.contexts.SessionContext;
import com.bitwormhole.passport.contexts.SessionFacade;
import com.bitwormhole.passport.web.dto.SessionDTO;
import com.bitwormhole.passport.web.dto.UserDTO;

public class DefaultSession extends SessionFacade {

    public DefaultSession(SessionContext ctx) {
        super(ctx);
    }
}
