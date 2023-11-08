package com.bitwormhole.passport.components.bo;

import androidx.room.ColumnInfo;

import com.bitwormhole.passport.data.dxo.UserSpaceID;

public class UserSpaceBO extends BaseBO {


    public final static String SelectionCurrent = "current";


    public UserSpaceID id;
    public String email;
    public String domain;
    public String selection;
    public boolean enabled;
    public boolean exists;

}
