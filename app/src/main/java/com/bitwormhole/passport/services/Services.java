package com.bitwormhole.passport.services;

public interface Services {

    JWTClientService getJWTs();

    PublicKeyService getPublicKeys();

    SecretKeyService getSecretKeys();

    RestClientService getRestClients();

    SessionManager getSessions();

    TaskService getTasks();

    UUIDGenerater getUUIDGenerater();

    DatabaseService getDatabases();

    UserSpaceService getUserSpaces();
}
