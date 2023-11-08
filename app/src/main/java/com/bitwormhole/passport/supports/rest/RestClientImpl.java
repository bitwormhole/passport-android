package com.bitwormhole.passport.supports.rest;

import android.util.Log;

import com.bitwormhole.passport.tasks.Promise;
import com.bitwormhole.passport.tasks.Result;
import com.bitwormhole.passport.tasks.TaskContext;
import com.bitwormhole.passport.utils.IOUtils;
import com.bitwormhole.passport.web.HttpEntity;
import com.bitwormhole.passport.web.HttpHeaders;
import com.bitwormhole.passport.web.HttpStatus;
import com.bitwormhole.passport.web.RestClient;
import com.bitwormhole.passport.web.RestContext;
import com.bitwormhole.passport.web.RestRequest;
import com.bitwormhole.passport.web.RestResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

final class RestClientImpl implements RestClient {

    private RestContext context;

    public RestClientImpl() {
        context = new RestContext();
    }

    @Override
    public void setContext(RestContext ctx) {
        if (ctx == null) {
            return;
        }
        context = new RestContext(ctx);
    }

    @Override
    public RestContext getContext() {
        return new RestContext(context);
    }

    @Override
    public RestResponse Do(RestRequest request) throws IOException {
        if (request.context == null) {
            request.context = context;
        }
        URL url = this.makeURL(request);
        request.url = url.toString();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try {
            this.prepareRequest(conn, request);
            String method = conn.getRequestMethod();
            Log.d("REST", "HTTP " + method + " " + url);
            RestResponse resp = this.handleResponse(conn, request);
            if (!this.isHttpOK(resp)) {
                throw new RESTException(resp);
            }
            this.handleSetJWT(resp);
            return resp;
        } finally {
            conn.disconnect();
        }
    }


    private boolean isHttpOK(RestResponse resp) {
        int code = resp.status;
        return code == HttpStatus.OK;
    }

    private void handleSetJWT(RestResponse resp) {
        RestContext ctx = this.context;
        String jwt = resp.headers.get("x-set-jwt");
        if (jwt == null || ctx == null) {
            return;
        }
        ctx.jwt = jwt;
    }

    private URL makeURL(RestRequest request) throws MalformedURLException {
        StringBuilder builder = new StringBuilder();
        this.makeURLWithoutQuery(request, builder);
        this.makeQueryForURL(request, builder);
        URL url = new URL(builder.toString());
        return this.normalize(url);
    }

    private URL normalize(URL url) throws MalformedURLException {

        String protocol = url.getProtocol();
        String host = url.getHost();
        int port = url.getPort();
        String path = url.getPath();
        String query = url.getQuery();

        path = normalizePath(path);

        StringBuilder b = new StringBuilder();
        b.append(protocol).append(":");
        b.append("//").append(host);
        if (port > 0) {
            b.append(":").append(port);
        }
        b.append("").append(path);
        if (query != null) {
            b.append("?").append(query);
        }
        return new URL(b.toString());
    }

    private String normalizePath(String path) {
        if (path == null) {
            return "/";
        }
        StringBuilder b = new StringBuilder();
        String[] parts = path.split("/");
        for (String part : parts) {
            part = part.trim();
            if (part.length() == 0) {
                continue;
            }
            b.append("/").append(part);
        }
        if (b.length() == 0) {
            return "/";
        }
        return b.toString();
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

        b.append(this.getBaseURL(request.context));

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

    private String getBaseURL(RestContext ctx) {
        // todo
        // return "https://release.bitwormhole.com";
        return ctx.baseURL;
    }

    private String getDefaultApiPath() {
        // todo
        return "/api/v1";
    }

    private void prepareRequest(HttpURLConnection conn, RestRequest request) throws IOException {
        prepareRequestJWT(conn, request);
        prepareRequestMethod(conn, request);
        prepareRequestHeaders(conn, request);
        prepareRequestBody(conn, request);
    }

    private void prepareRequestJWT(HttpURLConnection conn, RestRequest request) throws ProtocolException {
        RestContext ctx = this.context;
        if (ctx == null) {
            return;
        }
        String jwt = ctx.jwt;
        if (jwt == null) {
            return;
        }
        request.headers.put("x-jwt", jwt);
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


    private RestResponse handleResponse(HttpURLConnection conn, RestRequest req) throws IOException {
        RestResponse resp = new RestResponse();
        int code = conn.getResponseCode();
        String msg = conn.getResponseMessage();
        resp.status = code;
        resp.message = msg;
        resp.request = req;
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
    public Promise Execute(TaskContext tc, RestRequest req) {
        final RestClientImpl self = this;
        return Promise.execute(tc, RestResponse.class, () -> {
            RestResponse resp = self.Do(req);
            return resp;
        });
    }
}
