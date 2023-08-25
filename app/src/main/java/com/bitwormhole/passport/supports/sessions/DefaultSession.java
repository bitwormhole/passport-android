package com.bitwormhole.passport.supports.sessions;

import com.bitwormhole.passport.contexts.IClient;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.web.dto.SessionDTO;

public class DefaultSession implements ISession {

    private final IClient client;
    private final String domain;
    private final String username;

    public DefaultSession(IClient c, SessionDTO o) {
        client = c;
        username = o.getName();
        domain = o.getDomain();
    }

    @Override
    public IClient getClient() {
        return client;
    }

    @Override
    public String getUserDomain() {
        return domain;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public void reload() {

    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }

}
