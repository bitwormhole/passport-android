package com.bitwormhole.passport.services;

import com.bitwormhole.passport.contexts.ISession;

public interface LoginService {

    ISession connect();

    void reconnect(ISession session);

}
