package com.bitwormhole.passport.supports.rest;

import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.IComponent;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.services.RestClientService;
import com.bitwormhole.passport.services.ServerDiscoverService;
import com.bitwormhole.passport.tasks.Promise;
import com.bitwormhole.passport.tasks.TaskContext;
import com.bitwormhole.passport.web.RestClient;
import com.bitwormhole.passport.web.RestContext;
import com.bitwormhole.passport.web.RestRequest;
import com.bitwormhole.passport.web.RestResponse;
import com.bitwormhole.passport.web.dto.ProfileDTO;
import com.bitwormhole.passport.web.dto.ServiceDTO;
import com.bitwormhole.passport.web.vo.ServicesVO;

import java.io.IOException;
import java.util.List;

public final class RestClients implements RestClientService, IComponent {

    private RestClient client;

    public RestClients() {
    }


    private RestClient forClient() {
        RestClient c = client;
        if (c == null) {
            c = new RestClientImpl();
            client = c;
        }
        return c;
    }


    @Override
    public RestClient createNewClient(RestContext ctx) {
        if (ctx == null) {
            ctx = forClient().getContext();
        }
        RestClient c = new RestClientImpl();
        c.setContext(ctx);
        return c;
    }

    @Override
    public RestClient createNewClient(ISession session) {
        String domain = session.getUserDomain();
        ServerDiscoverService discover = session.getClient().getServices().getServerDiscoverService();
        RestContext ctx = new RestContext();
        ctx.baseURL = null;
        try {
            ServicesVO vo = discover.resolve(domain);
            List<ServiceDTO> all = vo.services;
            for (ServiceDTO item : all) {
                ctx.baseURL = this.computeBaseURL(item);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (ctx.baseURL == null) {
            throw new RuntimeException("service not found at domain:" + domain);
        }
        return this.createNewClient(ctx);
    }

    private String computeBaseURL(ServiceDTO item) {
        StringBuilder b = new StringBuilder();
        b.append(item.protocol);
        b.append("://");
        b.append(item.host);
        if (item.port > 0) {
            b.append(":");
            b.append(item.port);
        }
        return b.toString();
    }

    @Override
    public void setContext(RestContext ctx) {
        forClient().setContext(ctx);
    }

    @Override
    public RestContext getContext() {
        return forClient().getContext();
    }

    @Override
    public RestResponse Do(RestRequest request) throws IOException {
        return forClient().Do(request);
    }

    @Override
    public Promise<RestResponse> Execute(TaskContext tc, RestRequest request) {
        return forClient().Execute(tc, request);
    }

    @Override
    public void init(ClientContext cc) {
        ProfileDTO profile = cc.facade.getServices().getProfiles().getCurrentProfile();
        RestClient c = forClient();
        RestContext ctx = c.getContext();
        // ctx.baseURL = profile.restApiBaseURL;
        c.setContext(ctx);
    }
}
