package com.bitwormhole.passport.supports.sessions;

import com.bitwormhole.passport.components.bo.UserSpaceBO;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.components.userspace.UserSpaceManager;
import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.IComponent;
import com.bitwormhole.passport.data.IEntityUpdater;
import com.bitwormhole.passport.data.converters.UserSpaceConverter;
import com.bitwormhole.passport.data.dao.CRUD;
import com.bitwormhole.passport.data.dao.UserSpaceDao;
import com.bitwormhole.passport.data.dxo.LongID;
import com.bitwormhole.passport.data.dxo.UserSpaceID;
import com.bitwormhole.passport.data.entity.UserSpaceEntity;
import com.bitwormhole.passport.services.UserSpaceService;
import com.bitwormhole.passport.utils.BinaryUtils;
import com.bitwormhole.passport.utils.HashUtils;
import com.bitwormhole.passport.utils.ListOptionalUtils;
import com.bitwormhole.passport.web.dto.UserDTO;
import com.bitwormhole.passport.web.dto.UserSpaceDTO;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class UserSpaceServiceImpl implements UserSpaceService, IComponent {


    private static final long BE_CURRENT = 1;


    private ClientContext context;
    private UserSpaceConverter userSpaceConverter;
    private UserSpaceManager manager;

    @Override
    public UserSpaceBO insert(UserSpaceBO o1) {
        final UserSpaceDao dao = getUserSpaceDao();
        UserSpaceEntity o2 = this.userSpaceConverter.bo2entity(o1).toOptional().get();
        CRUD.getInstance(context).prepareInsert(o2);
        if (o2.current == 0) {
            o2.current = System.currentTimeMillis();
        }
        o2.id = dao.insertOne(o2);
        return this.userSpaceConverter.entity2bo(o2).toOptional().get();
    }

    @Override
    public UserSpaceBO update(UserSpaceID id, IEntityUpdater<UserSpaceEntity> h) {
        final UserSpaceDao dao = getUserSpaceDao();
        final Optional<UserSpaceEntity> op = dao.findById(id.id);
        UserSpaceEntity ent = h.update(op.get());
        if (ent == null) {
            return null;
        }
        CRUD.getInstance(context).prepareUpdate(ent);
        dao.update(ent);
        return this.userSpaceConverter.entity2bo(ent).toOptional().get();
    }

    @Override
    public void remove(UserSpaceID id) {
        final UserSpaceDao dao = getUserSpaceDao();
        Optional<UserSpaceEntity> op = dao.findById(id.id);
        if (op.isPresent()) {
            dao.delete(op.get());
        }
    }

    @Override
    public List<UserSpaceBO> listAll() {
        final UserSpaceDao dao = getUserSpaceDao();
        UserSpaceEntity[] all = dao.listAll();
        return this.userSpaceConverter.entity2bo(all).toList();
    }

    @Override
    public Optional<UserSpaceBO> findByID(UserSpaceID id) {
        if (id != null) {
            final UserSpaceDao dao = getUserSpaceDao();
            final Optional<UserSpaceEntity> op = dao.findById(id.id);
            if (op.isPresent()) {
                return this.userSpaceConverter.entity2bo(op.get()).toOptional();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserSpaceBO> findByDomainAndEmail(UserSpaceEntity want) {
        if (want == null) {
            return Optional.empty();
        }
        if (want.email == null || want.domain == null) {
            return Optional.empty();
        }
        final UserSpaceDao dao = getUserSpaceDao();
        final UserSpaceEntity[] all = dao.listAll();
        for (UserSpaceEntity item : all) {
            if (want.email.equals(item.email) && want.domain.equals(item.domain)) {
                return this.userSpaceConverter.entity2bo(item).toOptional();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserSpaceBO> findCurrent() {
        final UserSpaceDao dao = getUserSpaceDao();
        final UserSpaceEntity[] all = dao.listAll();
        for (UserSpaceEntity item : all) {
            if (item.current == BE_CURRENT) {
                return this.userSpaceConverter.entity2bo(item).toOptional();
            }
        }
        return Optional.empty();
    }

    @Override
    public void setCurrent(UserSpaceID us) {

        final long id = LongID.valueOf(us);
        final UserSpaceDao dao = getUserSpaceDao();
        final UserSpaceEntity[] all = dao.listAll();
        UserSpaceEntity older, newer;
        older = newer = null;
        for (UserSpaceEntity item : all) {
            if (item.current == BE_CURRENT) {
                older = item;
            }
            if (item.id == id) {
                newer = item;
            }
        }
        if (newer == null) {
            return;
        }
        if (older != null) {
            if (older.id == newer.id) {
                return;
            }
            older.current = System.currentTimeMillis();
            dao.update(older);
        }
        newer.current = BE_CURRENT;
        dao.update(newer);
    }

    @Override
    public UserSpaceManager getManager() {
        UserSpaceManager man = manager;
        if (man == null) {
            man = new UserSpaceManagerImpl(context);
            manager = man;
        }
        return man;
    }


    private UserSpaceDao getUserSpaceDao() {
        return context.facade.getDatabase().userSpaceDao();
    }


    @Override
    public void init(ClientContext cc) {
        this.context = cc;
        this.userSpaceConverter = cc.userSpaceConverter;
    }
}
