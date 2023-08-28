package com.bitwormhole.passport.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(inheritSuperIndices = true)
public class CredentialEntity extends Base {

    @PrimaryKey
    public long id;

    @ColumnInfo(name = "label")
    public String label;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "domain")
    public String domain;

}
