package com.bitwormhole.passport.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HashAttribute implements Attributes {

    private final Map<String, Object> table;

    public HashAttribute(Mode mode) {
        Map<String, Object> t = new HashMap<>();
        if (Mode.Safe.equals(mode)) {
            t = Collections.synchronizedMap(t);
        }
        table = t;
    }


    @Override
    public Object getAttribute(String name) {
        Object value = table.get(name);
        if (value == null) {
            throw new RuntimeException("no attribute with name:" + name);
        }
        return value;
    }

    @Override
    public Object getAttribute(String name, Object defaultValue) {
        Object value = table.get(name);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    @Override
    public void setAttribute(String name, Object value) {
        table.put(name, value);
    }
}
