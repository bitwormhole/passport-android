package com.bitwormhole.passport.data.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.bitwormhole.passport.data.dxo.SecretKeyID;
import com.bitwormhole.passport.data.dxo.UserSpaceID;


@Entity(inheritSuperIndices = true, indices = {@Index(value = {"uri"}, unique = true), @Index(value = {"current"}, unique = true)})
public class UserSpaceEntity extends BaseEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;


    @ColumnInfo(name = "domain")
    public String domain;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "uri")
    public String uri; // uri like 'passport:mail-user@mail.host/domain'

    // 已废弃：直接用 uuid 作为 alias
    //  @ColumnInfo(name = "key_pair_alias")
    //    public String keyPairAlias;

    @ColumnInfo(name = "secret_key_id")
    public long secretKeyID;

    @ColumnInfo(name = "current")
    public long current;   // current==1 表示是当前的用户空间


    public UserSpaceEntity() {
    }

    public UserSpaceID getID() {
        UserSpaceID dst = new UserSpaceID();
        dst.id = this.id;
        return dst;
    }

    public SecretKeyID getSecretKeyID() {
        SecretKeyID dst = new SecretKeyID();
        dst.id = this.secretKeyID;
        return dst;
    }

    public String computeURI() {
        // 'passport:mail-user@mail.host/domain'
        StringBuilder b = new StringBuilder();
        b.append("passport:");
        b.append(this.email);
        b.append("/");
        b.append(this.domain);
        return b.toString();
    }

}
