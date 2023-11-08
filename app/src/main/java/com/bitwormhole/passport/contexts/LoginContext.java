package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.web.dto.ServiceDTO;
import com.bitwormhole.passport.web.vo.ServicesVO;

import java.util.List;

public final class LoginContext {

    public String domain;
    public String email;
    public List<ServiceDTO> services;

}
