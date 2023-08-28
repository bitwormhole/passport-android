package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.services.Services;

public interface IClient {

    IPassportApplication getPassportApp();


    ISession getCurrentSession();

    Services getServices();


    //   JWTClientService getJWT();
    //   KeyPairService getKeyPairs();
    //   RestClientService getRest();
    //   SessionManager getSessions();
    //   TaskService getTasks();

}
