package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.components.userspace.UserSpace;

public interface ISession {

    IClient getClient();

    String getUserDomain();

    String getUserEmail();

    UserSpace getUserSpace();

    void reload();

    void load();

    void save();
}
