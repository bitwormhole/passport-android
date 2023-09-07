package com.bitwormhole.passport.utils;

public final class BinaryUtils {

    public static String stringify(byte[] data) {
        return HexUtils.stringify(data);
    }

}
