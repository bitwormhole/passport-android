package com.bitwormhole.passport.data.entity;

import androidx.room.*;


@Entity(inheritSuperIndices = true, indices = {@Index(value = {"uri"}, unique = true)})
public class UserEntity extends Base {

    @PrimaryKey
    public long id;

    @ColumnInfo(name = "domain")
    public String domain;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "uri")
    public String uri; // uri=passport://user@mail/domain

    @ColumnInfo(name = "display_name")
    public String displayName;

    @ColumnInfo(name = "avatar")
    public String avatar;

}
