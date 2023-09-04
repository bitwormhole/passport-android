package com.bitwormhole.passport.components.security;

import com.bitwormhole.passport.web.dto.SignatureDTO;

public interface Verifier {

    void verify(SignatureDTO s) throws SecurityException;

}
