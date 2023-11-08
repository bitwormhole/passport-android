package com.bitwormhole.passport.web;

public class RestContext {

    public String baseURL;
    public String jwt;

    public RestContext() {
        this.baseURL = "https://undefined/";
    }

    public RestContext(RestContext ctx) {
        this.baseURL = ctx.baseURL;
        this.jwt = ctx.jwt;
    }

}
