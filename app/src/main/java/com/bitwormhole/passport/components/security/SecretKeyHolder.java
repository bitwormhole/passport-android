package com.bitwormhole.passport.components.security;

import com.bitwormhole.passport.web.dto.SecretKeyDTO;

public interface SecretKeyHolder {

    Decrypter getDecrypter();

    Encryptor getEncryptor();

    SecretKeyDTO export();

}
