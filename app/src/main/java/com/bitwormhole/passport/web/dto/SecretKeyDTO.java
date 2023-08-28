package com.bitwormhole.passport.web.dto;

public class SecretKeyDTO {


    public String algorithm;
    public String format;


    public byte[] data; // 字节串形式的数据

    public String data64; // base64 形式的数据

}
