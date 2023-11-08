package com.bitwormhole.passport.services;

import com.bitwormhole.passport.contexts.ISession;

import java.io.IOException;

public interface LoginService {


    class Params {
        public String domain;
        public String email;
        public char[] password;
    }


    ISession connect(Params p) throws IOException;

    void reconnect(ISession session);

}
