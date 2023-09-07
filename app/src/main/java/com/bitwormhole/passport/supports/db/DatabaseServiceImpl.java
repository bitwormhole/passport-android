package com.bitwormhole.passport.supports.db;

import android.content.Context;

import androidx.room.Room;

import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.IComponent;
import com.bitwormhole.passport.data.db.RootDatabase;
import com.bitwormhole.passport.data.db.UserDatabase;
import com.bitwormhole.passport.services.DatabaseService;
import com.bitwormhole.passport.services.UserSpaceService;

import java.io.File;

public class DatabaseServiceImpl implements DatabaseService, IComponent {

    private RootDatabase root;
    private ClientContext clientContext;
    private UserSpaceService userSpaces;

    @Override
    public UserDatabase loadUserDB(UserSpace space) {
        Context ctx = this.clientContext.context;
        File dbfile = space.getDatabaseFile();
        String name = dbfile.getPath();
        return Room.databaseBuilder(ctx, UserDatabase.class, name).build();
    }

    @Override
    public RootDatabase loadRootDB() {
        Context ctx = this.clientContext.context;
        File dbfile = this.userSpaces.getManager().getRoot().getDatabaseFile();
        String name = dbfile.getPath();
        return Room.databaseBuilder(ctx, RootDatabase.class, name)
                //  .addMigrations(RootDatabaseMigrationFactory.getMigrations())
                .fallbackToDestructiveMigration()
                .build();
    }

    @Override
    public RootDatabase getRootDB() {
        RootDatabase r = root;
        if (r == null) {
            r = this.loadRootDB();
            root = r;
        }
        return r;
    }

    @Override
    public void init(ClientContext cc) {
        this.clientContext = cc;
        this.userSpaces = cc.userSpaces;
    }
}
