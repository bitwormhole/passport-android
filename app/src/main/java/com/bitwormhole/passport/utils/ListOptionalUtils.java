package com.bitwormhole.passport.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListOptionalUtils {

    public static <T> Optional<T> forOptional(List<T> list) {
        if (list != null) {
            if (list.size() > 0) {
                T item = list.get(0);
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    public static <T> List<T> forList(Optional<T> op) {
        List<T> list = new ArrayList<>();
        if (op != null) {
            if (op.isPresent()) {
                list.add(op.get());
            }
        }
        return list;
    }

}
