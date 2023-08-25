package com.bitwormhole.passport.supports.rest;

import com.bitwormhole.passport.services.RestClientService;
import com.bitwormhole.passport.tasks.Promise;
import com.bitwormhole.passport.tasks.Result;
import com.bitwormhole.passport.utils.IOUtils;
import com.bitwormhole.passport.web.HttpEntity;
import com.bitwormhole.passport.web.HttpHeaders;
import com.bitwormhole.passport.web.RestRequest;
import com.bitwormhole.passport.web.RestResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DefaultRestClient implements RestClientService {

    @Override
    public RestResponse Do(RestRequest request) throws IOException {
        URL url = this.makeURL(request);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try {
            this.prepareRequest(conn, request);
            return this.handleResponse(conn);
        } finally {
            conn.disconnect();
        }
    }

    private URL makeURL(RestRequest request) throws MalformedURLException {
        StringBuilder builder = new StringBuilder();
        this.makeURLWithoutQuery(request, builder);
        this.makeQueryForURL(request, builder);
        return new URL(builder.toString());
    }

    private void makeURLWithoutQuery(RestRequest request, StringBuilder b) {

        String url = request.url;
        String path = request.path;
        String api = request.api;
        String restType = request.type;
        String restId = request.id;

        // use: url
        if (url != null) {
            b.append(url);
            return;
        }

        b.append(this.getBaseURL());

        // use: path
        if (path != null) {
            b.append(path);
            return;
        }

        // use: api + type + id
        // api
        if (api == null) {
            api = getDefaultApiPath() + "";
        }
        if (api.startsWith("/")) {
            b.append(api);
        } else {
            b.append("/" + api);
        }

        // type
        b.append("/" + restType);

        // id
        if (restId != null) {
            b.append("/" + restId);
        }
    }

    private void makeQueryForURL(RestRequest request, StringBuilder b) {
        boolean hasQuery = b.toString().contains("?");
        String sep = hasQuery ? "&" : "?";
        Map<String, String> table = request.query;
        List<String> keys = new ArrayList<>(table.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            String value = table.get(key);
            b.append(sep);
            b.append(key);
            b.append('=');
            b.append(value);
        }
    }

    private String getBaseURL() {
        // todo
        return "https://release.bitwormhole.com";
    }

    private String getDefaultApiPath() {
        // todo
        return "/api/v1";
    }

    private void prepareRequest(HttpURLConnection conn, RestRequest request) throws IOException {
        prepareRequestMethod(conn, request);
        prepareRequestHeaders(conn, request);
        prepareRequestBody(conn, request);
    }

    private void prepareRequestMethod(HttpURLConnection conn, RestRequest request) throws ProtocolException {
        String method = request.method;
        if (method == null) {
            method = "";
        }
        method = method.trim();
        method = method.toUpperCase();
        if (method.equals("")) {
            method = "GET";
        }
        conn.setRequestMethod(method);
    }

    private void prepareRequestHeaders(HttpURLConnection conn, RestRequest request) {
        HttpHeaders src = request.headers;
        List<String> keys = src.keys();
        for (String key : keys) {
            String value = src.get(key);
            conn.setRequestProperty(key, value);
        }
    }

    private void prepareRequestBody(HttpURLConnection conn, RestRequest request) throws IOException {

        HttpEntity data = request.data;
        if (data == null) {
            return;
        }

        byte[] bin = data.data;
        if (bin == null) {
            return;
        }

        // type
        StringBuilder contentType = new StringBuilder();
        if (data.type == null) {
            contentType.append("application/octet-stream");
        } else {
            contentType.append(data.type);
        }
        if (data.charset != null) {
            contentType.append(";charset=");
            contentType.append(data.charset);
        }
        conn.setRequestProperty("Content-Type", contentType.toString());
        conn.setRequestProperty("Content-Length", String.valueOf(bin.length));

        // data
        conn.setDoOutput(true);
        OutputStream out = conn.getOutputStream();
        try {
            out.write(bin);
            out.flush();
        } finally {
            out.close();
        }
    }


    private RestResponse handleResponse(HttpURLConnection conn) throws IOException {
        RestResponse resp = new RestResponse();
        int code = conn.getResponseCode();
        String msg = conn.getResponseMessage();
        resp.status = code;
        resp.message = msg;
        this.handleResponseHeaders(conn, resp);
        if (code == HTTP.STATUS_OK) {
            resp.data = this.readResponseStream(conn.getInputStream(), resp);
        } else {
            resp.data = this.readResponseStream(conn.getErrorStream(), resp);
        }
        return resp;
    }

    private void handleResponseHeaders(HttpURLConnection conn, RestResponse resp) {
        Map<String, List<String>> src = conn.getHeaderFields();
        src.forEach((key, values) -> {
            for (String v : values) {
                resp.headers.put(key, v);
            }
        });
    }

    private HttpEntity readResponseStream(InputStream src, RestResponse resp) throws IOException {
        String ct = resp.headers.get("Content-Type") + "";
        String[] parts = ct.split(";");
        String type = "";
        Charset charset = null;
        for (int i = 0; i < parts.length; i++) {
            String str = parts[i].trim().toLowerCase();
            if (i == 0) {
                type = str;
            } else if (i == 1) {
                final String prefix = "charset=";
                if (str.startsWith(prefix)) {
                    str = str.substring(prefix.length());
                    charset = Charset.forName(str);
                }
            }
        }
        byte[] data = IOUtils.readAll(src, null);
        return HttpEntity.createWithBytes(data, type, charset);
    }


    @Override
    public Promise Execute(RestRequest req) {
        final DefaultRestClient self = this;
        return Promise.execute(() -> {
            RestResponse resp = self.Do(req);
            return new Result(resp);
        });
    }
}
