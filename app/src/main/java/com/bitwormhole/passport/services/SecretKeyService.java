package com.bitwormhole.passport.services;

import com.bitwormhole.passport.components.security.SecretKeyDriver;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.data.IEntityUpdater;
import com.bitwormhole.passport.data.dxo.SecretKeyID;
import com.bitwormhole.passport.data.entity.SecretKeyEntity;
import com.bitwormhole.passport.web.dto.SecretKeyDTO;

import java.util.List;
import java.util.Optional;

public interface SecretKeyService {


    // base

    List<SecretKeyDTO> listAll(ISession session);

    Optional<SecretKeyDTO> findByID(ISession session, SecretKeyID id);

    SecretKeyDTO insert(ISession session, SecretKeyDTO item);

    SecretKeyDTO update(ISession session, SecretKeyID id, IEntityUpdater<SecretKeyEntity> h);

    void remove(ISession session, SecretKeyID id);

    // ext

    SecretKeyDriver findDriver(String algorithm);

}
