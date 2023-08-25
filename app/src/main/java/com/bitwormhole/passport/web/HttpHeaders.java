package com.bitwormhole.passport.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders {

    private final Map<String, String> table;

    public HttpHeaders() {
        this.table = new HashMap<>();
    }

    public List<String> keys() {
        return new ArrayList<>(table.keySet());
    }

    public String get(String key) {
        key = normalizeKey(key);
        return table.get(key);
    }

    public void put(String key, String value) {
        key = normalizeKey(key);
        table.put(key, value);
    }

    private String normalizeKey(String key) {
        if (key == null) {
            return "";
        }
        key = key.trim();
        key = key.toLowerCase();
        return key;
    }

}
