package com.bitwormhole.passport.components.bo;

public class SignatureBO extends BaseBO {

    public String algorithm;

    public boolean ok;

    public byte[] data;
    public byte[] signature;

}
