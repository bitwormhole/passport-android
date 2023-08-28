package com.bitwormhole.passport.components.security;

import com.bitwormhole.passport.web.dto.EncryptedDTO;

public interface Encryptor {

    void encrypt(EncryptedDTO o);

}
