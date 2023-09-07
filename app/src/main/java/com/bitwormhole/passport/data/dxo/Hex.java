package com.bitwormhole.passport.data.dxo;

import com.bitwormhole.passport.utils.BinaryUtils;
import com.google.android.gms.common.util.Base64Utils;

import java.util.Arrays;

public class Hex {

    private final byte[] raw;
    private String str;

    public Hex(byte[] data) {
        if (data == null) {
            data = new byte[0];
        }
        this.raw = data;
    }

    public String toString() {
        String s = str;
        if (s == null) {
            s = BinaryUtils.stringify(raw);
            str = s;
        }
        return s;
    }

    public String toBase64() {
        return Base64Utils.encode(raw);
    }

    public byte[] toBytes() {
        return Arrays.copyOf(raw, raw.length);
    }

}
