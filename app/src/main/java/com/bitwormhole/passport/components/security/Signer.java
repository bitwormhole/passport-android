package com.bitwormhole.passport.components.security;

import com.bitwormhole.passport.web.dto.SignatureDTO;

public interface Signer {

    void sign(SignatureDTO s) throws SecurityException;

}
