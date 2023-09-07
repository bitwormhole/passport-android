package com.bitwormhole.passport.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bitwormhole.passport.data.dxo.CredentialID;

@Entity(inheritSuperIndices = true)
public class CredentialEntity extends BaseEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "label")
    public String label;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "domain")
    public String domain;


    public CredentialEntity() {
    }

    public CredentialID getID() {
        CredentialID dst = new CredentialID();
        dst.id = this.id;
        return dst;
    }

}
