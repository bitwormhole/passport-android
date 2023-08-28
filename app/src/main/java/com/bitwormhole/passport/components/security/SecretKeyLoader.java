package com.bitwormhole.passport.components.security;

import com.bitwormhole.passport.web.dto.SecretKeyDTO;

public interface SecretKeyLoader {

    SecretKeyHolder load(SecretKeyDTO data);

}
