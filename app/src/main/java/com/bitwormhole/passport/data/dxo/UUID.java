package com.bitwormhole.passport.data.dxo;

import com.bitwormhole.passport.utils.HexUtils;

import java.util.Arrays;

public final class UUID {

    private final String str;

    public UUID() {
        this.str = format(null);
    }

    public UUID(String s) {
        byte[] bin = string2bytes(s);
        this.str = format(bin);
    }

    public UUID(byte[] d) {
        this.str = format(d);
    }


    public static String valueOf(UUID uuid) {
        if (uuid == null) {
            return "";
        }
        String s = uuid.str;
        if (s == null) {
            return "";
        }
        return s;
    }

    public String toString() {
        return this.str;
    }

    public byte[] toBytes() {
        return string2bytes(this.str);
    }


    private static String format(byte[] b) {
        final int wantLengthInBytes = 128 / 8;
        if (b == null) {
            b = new byte[wantLengthInBytes];
        } else if (b.length != wantLengthInBytes) {
            b = Arrays.copyOf(b, wantLengthInBytes);
        }
        final int[] partLenList = new int[]{8, 4, 4, 4, 12};
        final char[] hexList = HexUtils.stringify(b).toCharArray();
        final StringBuilder builder = new StringBuilder();
        int ihex = 0;
        for (int partLen : partLenList) {
            if (builder.length() > 0) {
                builder.append('-');
            }
            for (int i = 0; i < partLen; i++) {
                builder.append(hexList[ihex++]);
            }
        }
        return builder.toString();
    }

    private static byte[] string2bytes(String s) {
        return HexUtils.parse(s);
    }

}
