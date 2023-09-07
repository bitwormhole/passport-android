package com.bitwormhole.passport.components.security;

import com.bitwormhole.passport.components.bo.CryptBO;
import com.bitwormhole.passport.web.dto.EncryptedDTO;

public interface Decrypter {

    void decrypt(CryptBO o);

}
