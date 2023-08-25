package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.services.JWTClientService;
import com.bitwormhole.passport.services.KeyPairService;
import com.bitwormhole.passport.services.RestClientService;
import com.bitwormhole.passport.services.SessionManager;
import com.bitwormhole.passport.services.TaskService;

public interface IClient {

    IPassportApplication getPassportApp();

    JWTClientService getJWT();

    KeyPairService getKeyPairs();

    RestClientService getRest();

    SessionManager getSessions();

    ISession getCurrentSession();

    TaskService getTasks();

}
