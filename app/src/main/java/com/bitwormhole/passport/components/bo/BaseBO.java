package com.bitwormhole.passport.components.bo;

import com.bitwormhole.passport.data.dxo.Timestamp;
import com.bitwormhole.passport.data.dxo.UUID;

public class BaseBO {

    public UUID uuid;

    public Timestamp createdAt;
    public Timestamp updatedAt;
    public Timestamp deletedAt;

}
