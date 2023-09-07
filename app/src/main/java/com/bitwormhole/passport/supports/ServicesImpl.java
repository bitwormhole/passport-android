package com.bitwormhole.passport.supports;

import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.IComponent;
import com.bitwormhole.passport.services.DatabaseService;
import com.bitwormhole.passport.services.JWTClientService;
import com.bitwormhole.passport.services.LoginService;
import com.bitwormhole.passport.services.ProfileService;
import com.bitwormhole.passport.services.PublicKeyService;
import com.bitwormhole.passport.services.RestClientService;
import com.bitwormhole.passport.services.SecretKeyService;
import com.bitwormhole.passport.services.ServerDiscoverService;
import com.bitwormhole.passport.services.Services;
import com.bitwormhole.passport.services.SessionManager;
import com.bitwormhole.passport.services.TaskService;
import com.bitwormhole.passport.services.UUIDGenerater;
import com.bitwormhole.passport.services.UserSpaceService;

public class ServicesImpl implements Services, IComponent {

    private ClientContext context;

    public ServicesImpl() {
    }

    @Override
    public void init(ClientContext cc) {
        this.context = cc;
    }

    @Override
    public JWTClientService getJWTs() {
        return context.jwt;
    }

    @Override
    public LoginService getLoginService() {
        return context.loginService;
    }

    @Override
    public PublicKeyService getPublicKeys() {
        return context.publicKeys;
    }

    @Override
    public SecretKeyService getSecretKeys() {
        return context.secretKeys;
    }

    @Override
    public RestClientService getRestClients() {
        return context.rest;
    }

    @Override
    public ProfileService getProfiles() {
        return context.profiles;
    }

    @Override
    public SessionManager getSessions() {
        return context.sessions;
    }

    @Override
    public ServerDiscoverService getServerDiscoverService() {
        return context.serverDiscoverService;
    }

    @Override
    public TaskService getTasks() {
        return context.tasks;
    }

    @Override
    public UUIDGenerater getUUIDGenerater() {
        return context.uuidGenerater;
    }

    @Override
    public DatabaseService getDatabases() {
        return context.databases;
    }

    @Override
    public UserSpaceService getUserSpaces() {
        return context.userSpaces;
    }

}
