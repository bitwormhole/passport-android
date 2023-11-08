package com.bitwormhole.passport.supports.rest;

import com.bitwormhole.passport.web.RestResponse;

public class RESTException extends RuntimeException {

    private final RestResponse resp;

    public RESTException(RestResponse resp) {
        super(makeMessage(resp));
        this.resp = resp;
    }

    public RESTException(RestResponse resp, String msg) {
        super(msg);
        this.resp = resp;
    }

    private static String makeMessage(RestResponse resp) {
        final String tag = "REST Exception: ";
        if (resp == null) {
            return tag + "no response";
        }
        int code = resp.status;
        String msg = resp.message;
        return tag + "HTTP " + code + " " + msg;
    }

    public RestResponse getResponse() {
        return resp;
    }
}
