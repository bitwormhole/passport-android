package com.bitwormhole.passport.web;

import com.bitwormhole.passport.tasks.Promise;
import com.bitwormhole.passport.tasks.TaskContext;

import java.io.IOException;

public interface RestClient {

    void setContext(RestContext ctx);

    RestContext getContext();

    RestResponse Do(RestRequest request) throws IOException;

    Promise<RestResponse> Execute(TaskContext tc, RestRequest request);

}
