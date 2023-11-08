package com.bitwormhole.passport.components.userspace;

import com.bitwormhole.passport.components.bo.UserSpaceBO;

import java.util.Optional;

public interface UserSpaceManager {

    UserSpace getRoot();

    UserSpace initSpace(UserSpaceBO want);

    Optional<UserSpace> getCurrent();

    Optional<UserSpace> getSpace(UserSpaceBO want);

    UserSpace openSpace(UserSpaceBO want);

    void setCurrentSpace(UserSpace space);

}
