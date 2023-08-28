package com.bitwormhole.passport.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.bitwormhole.passport.data.entity.CredentialEntity;

import java.util.List;

@Dao
public interface CredentialDao {

    @Query("SELECT * FROM CredentialEntity")
    List<CredentialEntity> getAll();

    @Insert
    void insertAll(CredentialEntity... credentials);

    @Delete
    void delete(CredentialEntity c);

}
