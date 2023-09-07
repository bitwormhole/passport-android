package com.bitwormhole.passport.components.security;

import com.bitwormhole.passport.components.bo.SignatureBO;

public interface Verifier {

    void verify(SignatureBO s) throws SecurityException;

}
