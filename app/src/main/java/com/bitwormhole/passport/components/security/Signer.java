package com.bitwormhole.passport.components.security;

import com.bitwormhole.passport.components.bo.SignatureBO;

public interface Signer {

    void sign(SignatureBO s) throws SecurityException;

}
