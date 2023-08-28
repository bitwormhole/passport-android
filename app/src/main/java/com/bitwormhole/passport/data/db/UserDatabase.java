package com.bitwormhole.passport.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.bitwormhole.passport.data.dao.CredentialDao;
import com.bitwormhole.passport.data.entity.CredentialEntity;


@Database(entities = {CredentialEntity.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    public abstract CredentialDao credentialDao();

}
