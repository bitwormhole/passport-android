package com.bitwormhole.passport.supports.keys;

import com.bitwormhole.passport.components.security.KeyDriver;
import com.bitwormhole.passport.components.security.KeyDriverManager;
import com.bitwormhole.passport.components.security.SecretKeyDriver;
import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.IComponent;
import com.bitwormhole.passport.services.SecretKeyService;

public class SecretKeyServiceImpl implements SecretKeyService, IComponent {

    private KeyDriverManager drivers;

    @Override
    public SecretKeyDriver findDriver(String algorithm) {
        KeyDriver[] list = this.drivers.findByName(algorithm);
        for (KeyDriver d : list) {
            if (d instanceof SecretKeyDriver) {
                return (SecretKeyDriver) d;
            }
        }
        // return null;
        throw new RuntimeException("no secret-key-driver with name:" + algorithm);
    }

    @Override
    public void init(ClientContext cc) {
        this.drivers = cc.keyDrivers;
    }
}
