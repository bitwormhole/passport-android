package com.bitwormhole.passport.utils;

public final class BinaryUtils {

    public static String stringify(byte[] data) {
        final String chstr = "0123456789abcdef";
        final char[] chset = chstr.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (byte b : data) {
            int n = b & 0xff;
            char c1 = chset[(n >> 4) & 0x0f];
            char c2 = chset[(n) & 0x0f];
            builder.append(c1);
            builder.append(c2);
        }
        return builder.toString();
    }

}
