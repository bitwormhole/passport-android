package com.bitwormhole.passport.supports.sessions;

import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.IComponent;
import com.bitwormhole.passport.services.RestClientService;
import com.bitwormhole.passport.services.ServerDiscoverService;
import com.bitwormhole.passport.tasks.Promise;
import com.bitwormhole.passport.tasks.PromiseHolder;
import com.bitwormhole.passport.tasks.TaskContext;
import com.bitwormhole.passport.web.RestRequest;
import com.bitwormhole.passport.web.RestResponse;
import com.bitwormhole.passport.web.dto.ProfileDTO;
import com.bitwormhole.passport.web.dto.ServiceDTO;
import com.bitwormhole.passport.web.dto.ServiceProvider;
import com.bitwormhole.passport.web.vo.ServicesVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerDiscoverServiceImpl implements ServerDiscoverService, IComponent {

    private RestClientService rest;
    private String wantApiSchema;
    private String wantApiProfile;


    @Override
    public ServicesVO resolve(String domain) throws IOException {
        RestRequest req = this.prepareRequest(domain);
        RestResponse resp = rest.Do(req);
        ServicesVO vo = resp.data.bindJSON(ServicesVO.class);
        vo.services = filterServices(vo.services);
        return vo;
    }

    @Override
    public Promise<ServicesVO> resolveAsync(TaskContext tc, String domain) {
        final PromiseHolder<ServicesVO> ph = Promise.pend(tc, ServicesVO.class);
        final RestRequest req = this.prepareRequest(domain);
        rest.Execute(tc, req).onThen((res) -> {
            ServicesVO pojo = res.data.bindJSON(ServicesVO.class);
            pojo.services = filterServices(pojo.services);
            ph.dispatchResult(pojo);
            return Promise.resolve(tc, RestResponse.class, res);
        }).onCatch((res) -> {
            Throwable err = res;
            ph.dispatchError(err);
            return Promise.reject(tc, RestResponse.class, res);
        });
        return ph.get();
    }

    public RestRequest prepareRequest(String domain) {
        RestRequest req = new RestRequest();
        req.url = "https://" + domain + "/index.json";
        return req;
    }


    @Override
    public void initial(ServiceProvider provider) throws IOException {

        ServicesVO vo = this.resolve(provider.domain);

        ;

    }

    @Override
    public ServiceProvider[] listServiceProviders() {

        ServiceProvider sp0 = new ServiceProvider();
        sp0.domain = "www.bitwormhole.com";
        sp0.label = "默认";
        sp0.isCustom = false;
        sp0.isDefault = true;

        ServiceProvider sp1 = new ServiceProvider();
        sp1.domain = "www.bitwormhole.com";
        sp1.label = "比特虫洞";
        sp1.isCustom = false;
        sp1.isDefault = false;

        ServiceProvider sp2 = new ServiceProvider();
        sp2.domain = "";
        sp2.label = "自定义";
        sp2.isCustom = true;
        sp2.isDefault = false;

        return new ServiceProvider[]{sp0, sp1, sp2};
    }

    private List<ServiceDTO> filterServices(List<ServiceDTO> src) {
        List<ServiceDTO> dst = new ArrayList<>();
        if (src == null) {
            return dst;
        }
        for (ServiceDTO item : src) {
            if (acceptService(item)) {
                dst.add(item);
            }
        }
        return dst;
    }

    private boolean acceptService(ServiceDTO item) {
        if (item == null) {
            return false;
        }
        String profile1 = "" + this.wantApiProfile;
        String schema1 = "" + this.wantApiSchema;
        String schema2 = "" + item.schema;
        String profile2 = "" + item.profiles;
        if (profile1.trim().equals("")) {
            return false;
        }
        return schema1.equals(schema2) && profile2.contains(profile1);
    }

    @Override
    public void init(ClientContext cc) {
        ProfileDTO profile = cc.services.getProfiles().getCurrentProfile();
        this.rest = cc.services.getRestClients();
        this.wantApiProfile = profile.profile;
        this.wantApiSchema = profile.restApiSchema;
    }

}
