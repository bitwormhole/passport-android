package com.bitwormhole.passport.services;

import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.tasks.Promise;
import com.bitwormhole.passport.web.RestClient;
import com.bitwormhole.passport.web.RestContext;
import com.bitwormhole.passport.web.RestRequest;
import com.bitwormhole.passport.web.RestResponse;

import java.io.IOException;

public interface RestClientService extends RestClient {

    RestClient createNewClient(RestContext ctx);

    RestClient createNewClient(ISession session);

}
