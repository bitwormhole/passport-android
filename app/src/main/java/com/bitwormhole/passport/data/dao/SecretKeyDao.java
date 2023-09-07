package com.bitwormhole.passport.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bitwormhole.passport.data.entity.SecretKeyEntity;

import java.util.List;
import java.util.Optional;

@Dao
public interface SecretKeyDao {

    @Query("SELECT * FROM SecretKeyEntity")
    List<SecretKeyEntity> getAll();

    @Query("SELECT * FROM SecretKeyEntity WHERE id IN (:ids)")
    List<SecretKeyEntity> loadAllByIds(int[] ids);

    @Query("SELECT * FROM SecretKeyEntity WHERE public_key_fingerprint IN (:fingerprint)")
    List<SecretKeyEntity> listByPublicKeyFingerprint(String fingerprint);

    @Query("SELECT * FROM SecretKeyEntity WHERE id IN (:id)")
    Optional<SecretKeyEntity> findById(long id);

    @Insert
    void insertAll(SecretKeyEntity... items);

    @Update
    void update(SecretKeyEntity item);

    @Delete
    void delete(SecretKeyEntity item);

}
