package com.bitwormhole.passport.data.dao;


import androidx.room.*;

import com.bitwormhole.passport.data.entity.UserEntity;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM UserEntity")
    List<UserEntity> getAll();

    @Query("SELECT * FROM UserEntity WHERE id IN (:userIds)")
    List<UserEntity> loadAllByIds(int[] userIds);

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
//    User findByName(String first, String last);

    @Insert
    void insertAll(UserEntity... users);

    @Delete
    void delete(UserEntity user);
}
