package com.bitwormhole.passport.web.dto;

public class EncryptedDTO {

    public byte[] plain; // 明文
    public byte[] encrypted; // 密文
    public byte[] iv; // 初始化向量

}
