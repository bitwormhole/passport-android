package com.bitwormhole.passport.components.userspace;

import com.bitwormhole.passport.data.dxo.UUID;
import com.bitwormhole.passport.data.dxo.UserSpaceID;

import java.io.File;

public interface UserSpace {

    UserSpaceID id();

    UUID uuid();


    boolean isRoot();

    String keyPairAlias();

    String email();

    String domain();

    File path();

    File getDatabaseFile();

}
