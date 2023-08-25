package com.bitwormhole.passport.contexts;

public interface ISession {

    IClient getClient();

    String getUserDomain();

    String getUserName();

    void reload();

    void load();

    void save();
}
