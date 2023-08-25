package com.bitwormhole.passport.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.bitwormhole.passport.data.dao.UserDao;
import com.bitwormhole.passport.data.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class RootDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}
