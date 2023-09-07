package com.bitwormhole.passport.web.dto;

public class KeyPairDTO extends BaseDTO {

    private String domain;
    private String username;
    private String alias;
    private String publicKeyFingerprint;
    private String x500name;

    public KeyPairDTO() {
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPublicKeyFingerprint() {
        return publicKeyFingerprint;
    }

    public void setPublicKeyFingerprint(String publicKeyFingerprint) {
        this.publicKeyFingerprint = publicKeyFingerprint;
    }

    public String getX500name() {
        return x500name;
    }

    public void setX500name(String x500name) {
        this.x500name = x500name;
    }
}
