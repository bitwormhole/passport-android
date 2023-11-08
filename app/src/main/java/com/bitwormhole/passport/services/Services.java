package com.bitwormhole.passport.services;

public interface Services {

    JWTClientService getJWTs();

    LoginService getLoginService();

    PublicKeyService getPublicKeys();

    SecretKeyService getSecretKeys();

    RestClientService getRestClients();

    ProfileService getProfiles();

    SessionManager getSessions();

    ServerDiscoverService getServerDiscoverService();

    UUIDGenerater getUUIDGenerater();

    DatabaseService getDatabases();

    UserSpaceService getUserSpaces();
}
