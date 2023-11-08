package com.bitwormhole.passport.profiles;

import com.bitwormhole.passport.web.dto.ProfileDTO;

public class ProfileFactoryBase {

    public static ProfileDTO get() {
        ProfileDTO p = new ProfileDTO();
        p.debug = false;
        p.restApiSchema = "https://api.bitwormhole.com/webapis/passport-rest-api.json";
        return p;
    }

}
