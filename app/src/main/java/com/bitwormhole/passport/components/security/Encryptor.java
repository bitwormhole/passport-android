package com.bitwormhole.passport.components.security;

import com.bitwormhole.passport.components.bo.CryptBO;
import com.bitwormhole.passport.web.dto.EncryptedDTO;

public interface Encryptor {

    void encrypt(CryptBO o);

}
