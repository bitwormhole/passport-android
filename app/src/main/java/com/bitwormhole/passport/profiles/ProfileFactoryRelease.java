package com.bitwormhole.passport.profiles;

import com.bitwormhole.passport.web.dto.ProfileDTO;

public class ProfileFactoryRelease {

    public static ProfileDTO get() {
        ProfileDTO p = ProfileFactoryBase.get();
        p.debug = false;
        p.httpsRequired = true;
        p.restApiSchema = "https://api.bitwormhole.com/webapis/passport-rest-api.json";
        p.profile = "release";
        return p;
    }

}
