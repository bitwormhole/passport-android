package com.bitwormhole.passport.services;

import com.bitwormhole.passport.data.dxo.UUID;

public interface UUIDGenerater {

    UUID generate();

    UUID generate(String... params);

}
