package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.components.bo.UserSpaceBO;
import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.PublicKeyDriver;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.data.db.UserDatabase;
import com.bitwormhole.passport.services.DatabaseService;
import com.bitwormhole.passport.services.Services;
import com.bitwormhole.passport.services.UserSpaceService;
import com.bitwormhole.passport.web.dto.UserDTO;

import java.util.Optional;

public class SessionComponentsLoaderImpl implements ISessionComponentsLoader {

    private final SessionContext context;

    public SessionComponentsLoaderImpl(SessionContext sc) {
        this.context = sc;
    }

    @Override
    public KeyPairHolder loadKeyPair() {
        String algorithm = "rsa";
        String alias = context.facade.getUserSpace().keyPairAlias();
        Services services = context.clientInterface.getServices();
        PublicKeyDriver driver = services.getPublicKeys().findDriver(algorithm);
        if (driver.containsAlias(alias)) {
            return driver.getKeyPairLoader().load(alias);
        } else {
            return driver.getKeyPairGenerator().generate(alias);
        }
    }

    @Override
    public SecretKeyHolder loadSecretKey() {
        return null;
    }

    @Override
    public UserSpace loadUserSpace() {
        UserSpaceService ser = context.clientInterface.getServices().getUserSpaces();
        UserSpaceBO o = new UserSpaceBO();
        o.domain = context.domain;
        o.email = context.email;
        Optional<UserSpace> result = ser.getManager().getSpace(o);
        return result.get();
    }

    @Override
    public UserDatabase loadDatabase() {
        DatabaseService ser = context.clientInterface.getServices().getDatabases();
        UserSpace space = context.facade.getUserSpace();
        return ser.loadUserDB(space);
    }
}
