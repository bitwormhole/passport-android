package com.bitwormhole.passport.supports.keys;

import com.bitwormhole.passport.components.security.KeyDriver;
import com.bitwormhole.passport.components.security.KeyDriverManager;
import com.bitwormhole.passport.components.security.SecretKeyDriver;
import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.IComponent;
import com.bitwormhole.passport.data.IEntityUpdater;
import com.bitwormhole.passport.data.dao.SecretKeyDao;
import com.bitwormhole.passport.data.dxo.SecretKeyID;
import com.bitwormhole.passport.data.entity.SecretKeyEntity;
import com.bitwormhole.passport.services.SecretKeyService;
import com.bitwormhole.passport.web.dto.SecretKeyDTO;

import java.util.List;
import java.util.Optional;

public class SecretKeyServiceImpl implements SecretKeyService, IComponent {

    private KeyDriverManager drivers;
    private ClientContext context;


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
    public List<SecretKeyDTO> listAll() {
        return null;
    }

    @Override
    public Optional<SecretKeyDTO> findByID(SecretKeyID id) {
        return Optional.empty();
    }

    @Override
    public SecretKeyDTO insert(SecretKeyDTO item) {


        return null;
    }

    @Override
    public SecretKeyDTO update(SecretKeyID id, IEntityUpdater<SecretKeyEntity> h) {
        return null;
    }

    @Override
    public void remove(SecretKeyID id) {
        SecretKeyDao dao = this.getSecretKeyDao();
        Optional<SecretKeyEntity> op = dao.findById(id.id);
        if (op.isPresent()) {
            dao.delete(op.get());
        }
    }

    private SecretKeyDao getSecretKeyDao() {
        return context.facade.getDatabase().secretKeyDao();
    }

    @Override
    public void init(ClientContext cc) {
        this.context = cc;
        this.drivers = cc.keyDrivers;
    }
}
