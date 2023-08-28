package com.bitwormhole.passport.supports.sessions;

import com.bitwormhole.passport.components.userspace.UserSpace;

import java.io.File;

final class UserSpaceImpl implements UserSpace {

    private final String path;

    public UserSpaceImpl(File path) {
        this.path = path.getPath();
    }

    @Override
    public File path() {
        return new File(path);
    }

    @Override
    public File getDatabaseFile() {
        return new File(path, "userspace.db");
    }
}
