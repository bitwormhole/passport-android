package com.bitwormhole.passport.web;


import com.google.gson.Gson;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class HttpEntity {

    public final byte[] data;
    public final String type;
    public final Charset charset;

    private HttpEntity(byte[] rawData, String mediaType, Charset chs) {

        if (rawData == null) {
            rawData = new byte[0];
        }

        if (mediaType == null) {
            mediaType = "";
        }

        if (chs == null) {
            chs = StandardCharsets.UTF_8;
        }

        this.data = rawData;
        this.type = mediaType;
        this.charset = chs;
    }


    public static HttpEntity createWithBytes(byte[] rawData, String mediaType, Charset chs) {
        return new HttpEntity(rawData, mediaType, chs);
    }

    public static HttpEntity createWithString(String s, String mediaType, Charset chs) {
        if (chs == null) {
            chs = StandardCharsets.UTF_8;
        }
        byte[] raw = s.getBytes(chs);
        return new HttpEntity(raw, mediaType, chs);
    }

    public static HttpEntity createWithPOJO(Object o) {
        Gson gs = new Gson();
        String str = gs.toJson(o);
        Charset chs = StandardCharsets.UTF_8;
        byte[] data = str.getBytes(chs);
        String mediaType = "application/json";
        return new HttpEntity(data, mediaType, chs);
    }

    public String toString() {
        return "[HttpEntity length:" + this.data.length + "]";
    }

    public String getString() {
        return new String(data, charset);
    }

    public <T> T bindJSON(Class<T> t) {
        String ctype = "" + this.type;
        if (ctype.toLowerCase().contains("json")) {
            Gson gs = new Gson();
            String str = new String(data, charset);
            return gs.fromJson(str, t);
        }
        throw new NumberFormatException("want JSON, have content-type:" + ctype);
    }

}
