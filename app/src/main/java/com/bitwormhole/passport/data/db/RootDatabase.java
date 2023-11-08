package com.bitwormhole.passport.data.db;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.bitwormhole.passport.data.dao.SecretKeyDao;
import com.bitwormhole.passport.data.dao.UserSpaceDao;
import com.bitwormhole.passport.data.entity.SecretKeyEntity;
import com.bitwormhole.passport.data.entity.UserSpaceEntity;

@Database(
        version = 5,
        entities = {
                UserSpaceEntity.class,
        }
)
public abstract class RootDatabase extends RoomDatabase {

    public abstract UserSpaceDao userSpaceDao();

}
