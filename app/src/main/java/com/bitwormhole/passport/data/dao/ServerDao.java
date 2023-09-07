package com.bitwormhole.passport.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bitwormhole.passport.data.entity.ServerEntity;
import com.bitwormhole.passport.data.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Dao
public interface ServerDao {

    @Insert
    long insert(ServerEntity item);

    @Insert
    long[] insertAll(ServerEntity... items);

    @Update
    void update(ServerEntity item);

    @Delete
    void delete(ServerEntity item);

    // query

    @Query("SELECT * FROM ServerEntity WHERE id IN (:ids)")
    List<ServerEntity> listByIds(long[] ids);

    @Query("SELECT * FROM ServerEntity WHERE id = (:id)")
    Optional<ServerEntity> findById(long id);

}
