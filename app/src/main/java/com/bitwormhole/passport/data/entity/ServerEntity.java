package com.bitwormhole.passport.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(
        inheritSuperIndices = true,
        indices = {
             //   @Index(value = {"uri"}, unique = true)
        }
)
public class ServerEntity extends BaseEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "namespace")
    public String namespace;


    @ColumnInfo(name = "protocol")
    public String protocol;

    @ColumnInfo(name = "host")
    public String host;

    @ColumnInfo(name = "port")
    public int port;

    @ColumnInfo(name = "url")
    public String url;

}
