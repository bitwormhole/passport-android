package com.bitwormhole.passport.services;

import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.web.dto.KeyPairDTO;
import com.bitwormhole.passport.web.dto.SessionDTO;

import java.security.KeyPair;
import java.security.KeyStore;

public interface KeyPairService {

    KeyPair getAppKeyPair(boolean create);

    KeyPair getUserKeyPair(ISession session, boolean create);

    KeyStore getKeyStore();

    KeyPairDTO[] listKeyPairs();

}
