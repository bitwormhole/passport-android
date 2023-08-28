package com.bitwormhole.passport.components.security;

import com.bitwormhole.passport.web.dto.PublicKeyDTO;

public interface PublicKeyHolder {

    PublicKeyDTO export();

    Verifier getSignatureVerifier();

    Encryptor getEncryptor();

}
