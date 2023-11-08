package com.bitwormhole.passport.web;

import java.util.HashMap;
import java.util.Map;

public class RestRequest {

    public RestContext context;

    public String method;

    public String url;
    public String path;
    public String api;
    public String type;
    public String id;

    public final Map<String, String> query = new HashMap<>();
    public final HttpHeaders headers = new HttpHeaders();

    public HttpEntity data;
}
