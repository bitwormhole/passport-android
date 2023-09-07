package com.bitwormhole.passport.data.entity;

import androidx.room.ColumnInfo;

import com.bitwormhole.passport.data.dxo.UUID;

public class BaseEntity {

    @ColumnInfo(name = "uuid")
    public String uuid;

    @ColumnInfo(name = "created_at")
    public long createdAt;

    @ColumnInfo(name = "updated_at")
    public long updatedAt;

    @ColumnInfo(name = "deleted_at")
    public long deletedAt;

}
