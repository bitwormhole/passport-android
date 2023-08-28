package com.bitwormhole.passport.supports.sessions;

import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.contexts.IClient;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.web.dto.SessionDTO;
import com.bitwormhole.passport.web.dto.UserDTO;

public class DefaultSession implements ISession {

    private final IClient client;
    private final String domain;
    private final String email;
    private final UserSpace space;

    public DefaultSession(IClient c, UserDTO o) {
        client = c;
        email = o.email;
        domain = o.domain;
        space = this.loadUserSpace(c, o);
    }

    private UserSpace loadUserSpace(IClient c, UserDTO u) {
        return c.getServices().getUserSpaces().getSpace(u);
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
    public String getUserEmail() {
        return this.email;
    }

    @Override
    public UserSpace getUserSpace() {
        return this.space;
    }

    @Override
    public void reload() {
        // todo...

    }

    @Override
    public void load() {
        // todo...

    }

    @Override
    public void save() {
    // todo...
    }

}
