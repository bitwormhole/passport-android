package com.bitwormhole.passport.supports.keys;

import com.bitwormhole.passport.components.security.KeyDriver;
import com.bitwormhole.passport.components.security.KeyDriverManager;
import com.bitwormhole.passport.components.security.PublicKeyDriver;
import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.IComponent;
import com.bitwormhole.passport.services.PublicKeyService;

public class PublicKeyServiceImpl implements PublicKeyService, IComponent {

    private KeyDriverManager drivers;

    @Override
    public void init(ClientContext cc) {
        this.drivers = cc.keyDrivers;
    }

    @Override
    public PublicKeyDriver findDriver(String algorithm) {
        KeyDriver[] list = this.drivers.findByName(algorithm);
        for (KeyDriver d : list) {
            if (d instanceof PublicKeyDriver) {
                return (PublicKeyDriver) d;
            }
        }
        throw new RuntimeException("no public-key-driver with name:" + algorithm);
    }
}
