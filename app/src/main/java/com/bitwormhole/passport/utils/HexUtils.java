package com.bitwormhole.passport.utils;

import java.io.ByteArrayOutputStream;

public class HexUtils {

    public static String stringify(byte[] data) {
        if (data == null) {
            return "";
        }
        final String ch_str = "0123456789abcdef";
        final char[] ch_set = ch_str.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (byte b : data) {
            int n = b & 0xff;
            char c1 = ch_set[(n >> 4) & 0x0f];
            char c2 = ch_set[(n) & 0x0f];
            builder.append(c1);
            builder.append(c2);
        }
        return builder.toString();
    }


    public static byte[] parse(String str) {
        if (str == null) {
            return new byte[0];
        }
        int h4bits = 0;
        int l4bits = 0;
        int count = 0;
        ByteArrayOutputStream builder = new ByteArrayOutputStream();
        char[] chs = str.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            final int the4bits = parse4bits(chs[i]);
            if (the4bits < 0) {
                continue;
            }
            if ((count & 0x01) == 0) {
                h4bits = the4bits;
            } else {
                l4bits = the4bits;
                builder.write(makeByte(h4bits, l4bits));
            }
            count++;
        }
        return builder.toByteArray();
    }


    private static int parse4bits(char ch) {
        if ('0' <= ch && ch <= '9') {
            return ch - '0';
        } else if ('a' <= ch && ch <= 'f') {
            return ch - 'a' + 0x0a;
        } else if ('A' <= ch && ch <= 'F') {
            return ch - 'A' + 0x0a;
        }
        return -1;
    }

    private static int makeByte(int h4, int l4) {
        return ((h4 << 4) & 0xf0) | (l4 & 0x0f);
    }
}
