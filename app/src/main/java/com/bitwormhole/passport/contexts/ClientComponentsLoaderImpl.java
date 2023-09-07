package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.components.security.KeyPairHolder;
import com.bitwormhole.passport.components.security.PublicKeyDriver;
import com.bitwormhole.passport.components.security.SecretKeyHolder;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.data.db.RootDatabase;
import com.bitwormhole.passport.services.DatabaseService;
import com.bitwormhole.passport.services.PublicKeyService;
import com.bitwormhole.passport.services.UserSpaceService;
import com.bitwormhole.passport.supports.keys.NativeSecretKeyLoader;

import java.util.Optional;

final class ClientComponentsLoaderImpl implements IClientComponentsLoader {

    private final ClientContext context;

    public ClientComponentsLoaderImpl(ClientContext cc) {
        this.context = cc;
    }

    @Override
    public KeyPairHolder loadKeyPair() {
        final String algorithm = "RSA";
        final String alias = "root";
        final PublicKeyService keys = context.services.getPublicKeys();
        final PublicKeyDriver driver = keys.findDriver(algorithm);
        if (driver.containsAlias(alias)) {
            return driver.getKeyPairLoader().load(alias);
        } else {
            return driver.getKeyPairGenerator().generate(alias);
        }
    }

    @Override
    public SecretKeyHolder loadSecretKey() {
        KeyPairHolder kp = context.facade.getKeyPair();
        NativeSecretKeyLoader loader = new NativeSecretKeyLoader(context);
        return loader.loadByKeyPair(kp);
    }

    @Override
    public Optional<ISession> loadCurrentSession() {
        UserSpaceService spaces = context.facade.getServices().getUserSpaces();
        Optional<UserSpace> space = spaces.getManager().getCurrent();
        if (!space.isPresent()) {
            return Optional.empty();
        }
        ISession session = this.loadSession(space.get());
        return Optional.of(session);
    }

    @Override
    public ISession loadSession(UserSpace have) {
        SessionContext sc = new SessionContext(context);
        sc.domain = have.domain();
        sc.email = have.email();
        return sc.facade;
    }

    @Override
    public RootDatabase loadDatabase() {
        DatabaseService ser = context.services.getDatabases();
        return ser.loadRootDB();
    }
}
