package com.bitwormhole.passport.supports.sessions;

import com.bitwormhole.passport.contexts.IClient;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.services.LoginService;
import com.bitwormhole.passport.services.RestClientService;
import com.bitwormhole.passport.services.ServerDiscoverService;
import com.bitwormhole.passport.tasks.Promise;
import com.bitwormhole.passport.web.RestClient;
import com.bitwormhole.passport.web.RestRequest;
import com.bitwormhole.passport.web.RestResponse;
import com.bitwormhole.passport.web.dto.ServiceDTO;
import com.bitwormhole.passport.web.vo.ServicesVO;

import java.io.IOException;

public class LoginServiceImpl implements LoginService {

    @Override
    public ISession connect(Params p) throws IOException {

        Connection connection = new Connection();
        Connector connector = new Connector(connection);
        connection.params = p;

        connector.discover();
        connector.prepareRestClient();
        connector.login();
        connector.bind();

        return connection.session;
    }

    @Override
    public void reconnect(ISession session) {

        Connection conn = new Connection();

    }

    private static class Connector {
        final Connection connection;

        public Connector(Connection c) {
            this.connection = c;
        }

        void discover() throws IOException {
            ServerDiscoverService ser = connection.client.getServices().getServerDiscoverService();
            String domain = connection.params.domain;
            ServicesVO result = ser.resolve(domain);
            ServiceDTO s0 = result.services.get(0);
            connection.server = s0;
        }

        void prepareRestClient() {
            RestClientService ser = connection.client.getServices().getRestClients();


        }

        void login() throws IOException {

            RestRequest req = new RestRequest();
            RestResponse resp = connection.rest.Do(req);

        }

        void bind() {
        }

    }


    private static class Connection {
        RestClient rest;
        IClient client;
        ISession session;
        Params params;
        ServiceDTO server;
    }
}
