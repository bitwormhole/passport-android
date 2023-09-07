package com.bitwormhole.passport.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ResultSet<T> {

    private final List<T> list;

    public ResultSet() {
        list = new ArrayList<>();
    }

    public void add(T item) {
        if (item != null) {
            list.add(item);
        }
    }

    public void addAll(T... items) {
        if (items == null) {
            return;
        }
        for (T item : items) {
            this.add(item);
        }
    }

    public Optional<T> toOptional() {
        int count = 0;
        T one = null;
        List<T> src = list;
        for (T item : src) {
            if (item == null) {
                continue;
            }
            one = item;
            count++;
        }
        if (count < 1) {
            return Optional.empty();
        } else if (count > 1) {
            throw new RuntimeException("more than one items are in the result set");
        }
        return Optional.of(one);
    }

    public List<T> toList() {
        return list;
    }
}
