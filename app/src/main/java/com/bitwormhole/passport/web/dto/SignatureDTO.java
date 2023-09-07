package com.bitwormhole.passport.web.dto;


// 签名
public class SignatureDTO extends BaseDTO {

    public byte[] data;
    public byte[] signature;
    public String algorithm;
    public boolean ok;

}
