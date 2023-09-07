package com.bitwormhole.passport.services;

import com.bitwormhole.passport.components.security.SecretKeyDriver;
import com.bitwormhole.passport.data.IEntityUpdater;
import com.bitwormhole.passport.data.dxo.SecretKeyID;
import com.bitwormhole.passport.data.entity.SecretKeyEntity;
import com.bitwormhole.passport.web.dto.SecretKeyDTO;

import java.util.List;
import java.util.Optional;

public interface SecretKeyService {


    // base

    List<SecretKeyDTO> listAll();

    Optional<SecretKeyDTO> findByID(SecretKeyID id);

    SecretKeyDTO insert(SecretKeyDTO item);

    SecretKeyDTO update(SecretKeyID id, IEntityUpdater<SecretKeyEntity> h);

    void remove(SecretKeyID id);

    // ext

    SecretKeyDriver findDriver(String algorithm);

}
