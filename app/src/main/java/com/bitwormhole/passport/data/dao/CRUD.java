package com.bitwormhole.passport.data.dao;

import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.IClient;
import com.bitwormhole.passport.data.dxo.UUID;
import com.bitwormhole.passport.data.entity.BaseEntity;
import com.bitwormhole.passport.services.UUIDGenerater;

public final class CRUD {

    private final IClient client;

    private CRUD(IClient c) {
        this.client = c;
    }

    public static CRUD getInstance(IClient c) {
        return new CRUD(c);
    }

    public static CRUD getInstance(ClientContext cc) {
        return new CRUD(cc.facade);
    }

    public void prepareInsert(BaseEntity entity) {
        long now = System.currentTimeMillis();
        UUIDGenerater uuidGen = this.client.getServices().getUUIDGenerater();
        UUID uuid = uuidGen.generate("class:" + entity.getClass().getName());
        entity.createdAt = now;
        entity.updatedAt = now;
        entity.uuid = uuid.toString();
    }

    public void prepareUpdate(BaseEntity entity) {
        entity.updatedAt = System.currentTimeMillis();
    }

}
