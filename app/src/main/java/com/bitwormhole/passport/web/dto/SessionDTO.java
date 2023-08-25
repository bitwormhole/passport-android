package com.bitwormhole.passport.web.dto;

public class SessionDTO {

    private String name; // the user name
    private String domain;

    public SessionDTO() {
        name = "";
        domain = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
