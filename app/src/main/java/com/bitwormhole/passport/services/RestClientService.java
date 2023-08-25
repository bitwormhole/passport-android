package com.bitwormhole.passport.services;

import com.bitwormhole.passport.tasks.Promise;
import com.bitwormhole.passport.web.RestRequest;
import com.bitwormhole.passport.web.RestResponse;

import java.io.IOException;

public interface RestClientService {

    RestResponse Do(RestRequest request) throws IOException;

    Promise<RestResponse> Execute(RestRequest request);

}
