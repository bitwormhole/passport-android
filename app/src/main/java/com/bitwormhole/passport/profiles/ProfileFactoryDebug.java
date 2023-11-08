package com.bitwormhole.passport.profiles;

import com.bitwormhole.passport.web.dto.ProfileDTO;

final class ProfileFactoryDebug {

    public static ProfileDTO get() {
        ProfileDTO p = ProfileFactoryBase.get();
        p.debug = true;
        p.httpsRequired = false;
        p.defaultDomain = "mimane.bitwormhole.com";
        p.restApiSchema = "https://api.bitwormhole.com/webapis/passport-rest-api.json";
        p.profile = "debug";
        return p;
    }

}
