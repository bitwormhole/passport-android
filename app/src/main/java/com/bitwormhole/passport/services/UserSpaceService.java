package com.bitwormhole.passport.services;

import com.bitwormhole.passport.components.bo.UserSpaceBO;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.components.userspace.UserSpaceManager;
import com.bitwormhole.passport.data.IEntityUpdater;
import com.bitwormhole.passport.data.dxo.UserSpaceID;
import com.bitwormhole.passport.data.entity.UserSpaceEntity;
import com.bitwormhole.passport.web.dto.UserDTO;
import com.bitwormhole.passport.web.dto.UserSpaceDTO;

import java.util.List;
import java.util.Optional;

public interface UserSpaceService {


    // base

    UserSpaceBO insert(UserSpaceBO item);

    UserSpaceBO update(UserSpaceID id, IEntityUpdater<UserSpaceEntity> h);

    void remove(UserSpaceID id);

    List<UserSpaceBO> listAll();

    Optional<UserSpaceBO> findByID(UserSpaceID id);

    Optional<UserSpaceBO> findByDomainAndEmail(UserSpaceEntity want);

    Optional<UserSpaceBO> findCurrent();

    void setCurrent(UserSpaceID id);


    // extends

    UserSpaceManager getManager();

}
