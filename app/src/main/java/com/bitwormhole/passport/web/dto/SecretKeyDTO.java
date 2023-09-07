package com.bitwormhole.passport.web.dto;

import com.bitwormhole.passport.data.dxo.SecretKeyID;

public class SecretKeyDTO extends BaseDTO {

    public long id;


    public String algorithm;
    public String format;


    public byte[] data; // 字节串形式的数据

    public String data64; // base64 形式的数据

}
