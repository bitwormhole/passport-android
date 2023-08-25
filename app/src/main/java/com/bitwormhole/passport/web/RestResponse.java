package com.bitwormhole.passport.web;

public class RestResponse {

    public RestRequest request;

    public String message;
    public int status;

    public final HttpHeaders headers = new HttpHeaders();

    public HttpEntity data;
}
