package com.bitwormhole.passport.supports.sessions;

import com.bitwormhole.passport.components.bo.UserSpaceBO;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.data.dxo.LongID;
import com.bitwormhole.passport.data.dxo.UUID;
import com.bitwormhole.passport.data.dxo.UserSpaceID;
import com.bitwormhole.passport.data.entity.UserSpaceEntity;

import java.io.File;

final class UserSpaceImpl implements UserSpace {

    private final boolean root;
    private final String alias;
    private final String email;
    private final String domain;
    private final String path;
    private final String uuid;
    private final long id;

    private UserSpaceImpl(Builder b) {
        String keyAlias = UUID.valueOf(b.space.uuid);
        if (b.root) {
            keyAlias = "root";
        }
        this.root = b.root;
        this.path = b.path.getPath();
        this.email = b.space.email;
        this.domain = b.space.domain;
        this.alias = keyAlias;
        this.uuid = UUID.valueOf(b.space.uuid);
        this.id = LongID.valueOf(b.space.id);
    }


    public static class Builder {

        public boolean root;
        // public String alias;
        // public String email;
        // public String domain;
        public File path;
        public UserSpaceBO space;


        public UserSpace create() {
            return new UserSpaceImpl(this);
        }
    }


    @Override
    public UserSpaceID id() {
        return new UserSpaceID(this.id);
    }

    @Override
    public UUID uuid() {
        return new UUID(this.uuid);
    }

    @Override
    public boolean isRoot() {
        return root;
    }

    @Override
    public String keyPairAlias() {
        return alias;
    }

    @Override
    public String email() {
        return this.email;
    }

    @Override
    public String domain() {
        return this.domain;
    }

    @Override
    public File path() {
        return new File(this.path);
    }

    @Override
    public File getDatabaseFile() {
        return new File(this.path, "space.db");
    }
}
