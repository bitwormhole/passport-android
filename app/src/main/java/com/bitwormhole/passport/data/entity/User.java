package com.bitwormhole.passport.data.entity;

import androidx.room.*;

@Entity
public class User {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

}
