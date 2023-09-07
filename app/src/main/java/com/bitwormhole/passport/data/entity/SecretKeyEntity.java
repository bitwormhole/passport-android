package com.bitwormhole.passport.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bitwormhole.passport.data.dxo.SecretKeyID;


@Entity(inheritSuperIndices = true)
public class SecretKeyEntity extends BaseEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "public_key_fingerprint")
    public String publicKeyFingerprint;

    @ColumnInfo(name = "key")
    public String encryptedKey; // a base64 string

    @ColumnInfo(name = "native")
    public boolean nativeKey;

    @ColumnInfo(name = "imported")
    public boolean imported;

    @ColumnInfo(name = "exported")
    public boolean exported;

    public SecretKeyEntity() {
    }

    public SecretKeyID getID() {
        return new SecretKeyID(this.id);
    }

}
