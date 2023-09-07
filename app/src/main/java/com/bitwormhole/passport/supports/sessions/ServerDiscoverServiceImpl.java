package com.bitwormhole.passport.supports.sessions;

import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.IComponent;
import com.bitwormhole.passport.services.RestClientService;
import com.bitwormhole.passport.services.ServerDiscoverService;
import com.bitwormhole.passport.web.RestRequest;
import com.bitwormhole.passport.web.RestResponse;
import com.bitwormhole.passport.web.vo.ServicesVO;

import java.io.IOException;

public class ServerDiscoverServiceImpl implements ServerDiscoverService, IComponent {

    private RestClientService rest;

    @Override
    public ServicesVO resolve(String domain) throws IOException {
        RestRequest req = new RestRequest();
        req.url = "https://" + domain + "/index.json";
        RestResponse resp = rest.Do(req);
        return resp.data.getPOJO(ServicesVO.class);
    }

    @Override
    public void init(ClientContext cc) {
        this.rest = cc.services.getRestClients();
    }
}
