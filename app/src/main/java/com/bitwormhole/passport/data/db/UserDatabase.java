package com.bitwormhole.passport.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.bitwormhole.passport.data.dao.CredentialDao;
import com.bitwormhole.passport.data.dao.UserDao;
import com.bitwormhole.passport.data.entity.CredentialEntity;
import com.bitwormhole.passport.data.entity.UserEntity;


@Database(
        version = 1,
        entities = {
                CredentialEntity.class,
                UserEntity.class,
        }
)
public abstract class UserDatabase extends RoomDatabase {

    public abstract CredentialDao credentialDao();

    public abstract UserDao userDao();

}
