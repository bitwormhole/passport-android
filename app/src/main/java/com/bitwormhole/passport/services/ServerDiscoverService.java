package com.bitwormhole.passport.services;

import com.bitwormhole.passport.web.vo.ServicesVO;

import java.io.IOException;

public interface ServerDiscoverService {

    ServicesVO resolve(String domain) throws IOException;

}
