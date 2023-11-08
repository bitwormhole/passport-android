package com.bitwormhole.passport.services;

import com.bitwormhole.passport.tasks.Promise;
import com.bitwormhole.passport.tasks.TaskContext;
import com.bitwormhole.passport.web.dto.ServiceProvider;
import com.bitwormhole.passport.web.vo.ServicesVO;

import java.io.IOException;

public interface ServerDiscoverService {

    ServicesVO resolve(String domain) throws IOException;

    Promise<ServicesVO> resolveAsync(TaskContext tc, String domain);

    void initial(ServiceProvider provider) throws IOException;

    ServiceProvider[] listServiceProviders();

}
