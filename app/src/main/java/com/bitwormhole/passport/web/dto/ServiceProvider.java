package com.bitwormhole.passport.web.dto;

public class ServiceProvider {

    public String domain;
    public String label;
    public boolean isDefault;
    public boolean isCustom;

    @Override
    public String toString() {
        return this.label;
    }
}
