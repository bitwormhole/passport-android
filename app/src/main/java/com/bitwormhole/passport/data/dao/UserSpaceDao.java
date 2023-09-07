package com.bitwormhole.passport.data.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bitwormhole.passport.data.entity.UserEntity;
import com.bitwormhole.passport.data.entity.UserSpaceEntity;

import java.util.List;
import java.util.Optional;

@Dao
public interface UserSpaceDao {

    @Insert
    long[] insertAll(UserSpaceEntity... items);

    @Insert
    long insertOne(UserSpaceEntity item);


    @Update
    void update(UserSpaceEntity item);

    @Delete
    void delete(UserSpaceEntity item);

    @Query("SELECT * FROM UserSpaceEntity")
    UserSpaceEntity[] listAll();

    @Query("SELECT * FROM UserSpaceEntity WHERE id = (:id)")
    Optional<UserSpaceEntity> findById(long id);

    @Query("SELECT * FROM UserSpaceEntity WHERE current = (:current)")
    Optional<UserSpaceEntity> findByCurrent(long current);

}
