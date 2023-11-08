package com.bitwormhole.passport.supports.sessions;

import com.bitwormhole.passport.components.bo.UserSpaceBO;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.components.userspace.UserSpaceManager;
import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.data.dxo.UUID;
import com.bitwormhole.passport.data.entity.UserSpaceEntity;
import com.bitwormhole.passport.services.UserSpaceService;

import java.io.File;
import java.util.Optional;

final class UserSpaceManagerImpl implements UserSpaceManager {

    private final UserSpaceService service;
    private final File datadir;

    public UserSpaceManagerImpl(ClientContext cc) {
        this.service = cc.facade.getServices().getUserSpaces();
        this.datadir = cc.context.getFilesDir();
    }

    private static final class UserSpaceBuilder extends UserSpaceImpl.Builder {
    }


    @Override
    public Optional<UserSpace> getCurrent() {
        Optional<UserSpaceBO> result = this.service.findCurrent();
        if (!result.isPresent()) {
            return Optional.empty();
        }
        UserSpace space = this.makeForUser(result.get());
        return Optional.of(space);
    }


    @Override
    public Optional<UserSpace> getSpace(UserSpaceBO want1) {
        UserSpaceEntity want2 = new UserSpaceEntity();
        want2.domain = want1.domain;
        want2.email = want1.email;
        Optional<UserSpaceBO> result = this.service.findByDomainAndEmail(want2);
        if (!result.isPresent()) {
            return Optional.empty();
        }
        UserSpace space = this.makeForUser(result.get());
        return Optional.of(space);
    }

    @Override
    public UserSpace openSpace(UserSpaceBO want) {
        Optional<UserSpace> opt = this.getSpace(want);
        if (opt.isPresent()) {
            return opt.get();
        }
        UserSpaceBO have = this.service.insert(want);
        return this.makeForUser(have);
    }

    @Override
    public void setCurrentSpace(UserSpace space) {
        this.service.setCurrent(space.id());
    }

    @Override
    public UserSpace initSpace(UserSpaceBO want) {
        UserSpaceBO have = this.service.insert(want);
        return this.makeForUser(have);
    }

    private UserSpace makeForUser(UserSpaceBO have) {

        final String alias = have.uuid.toString();
        final String domain = have.domain;
        final String email = have.email;

        UserSpaceBuilder b = new UserSpaceBuilder();
        b.root = false;
        b.space = have;
        b.path = new File(this.datadir, "home/" + alias);
        return b.create();
    }

    @Override
    public UserSpace getRoot() {
        UserSpaceBO o = new UserSpaceBO();
        o.uuid = new UUID();
        o.email = "root@local";
        o.domain = "local";
        UserSpaceBuilder b = new UserSpaceBuilder();
        b.root = true;
        b.space = o;
        b.path = new File(this.datadir, "root");
        return b.create();
    }
}
