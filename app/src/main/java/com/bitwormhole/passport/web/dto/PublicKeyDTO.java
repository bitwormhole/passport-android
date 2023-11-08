package com.bitwormhole.passport.web.dto;

import android.util.Base64;

public class PublicKeyDTO extends BaseDTO {

    public String algorithm;

    public String format;

    public String data; // base64


    public byte[] getKeyData() {
        String str = this.data;
        if (str == null) {
            return new byte[0];
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        return Base64.decode(str, 0);
    }

    public void setKeyData(byte[] d) {
        if (d == null) {
            this.data = null;
        }
        this.data = Base64.encodeToString(d, 0);
    }

}
