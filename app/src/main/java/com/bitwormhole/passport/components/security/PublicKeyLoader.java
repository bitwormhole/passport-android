package com.bitwormhole.passport.components.security;

import com.bitwormhole.passport.web.dto.PublicKeyDTO;

public interface PublicKeyLoader {

    PublicKeyHolder load(PublicKeyDTO data);

}
