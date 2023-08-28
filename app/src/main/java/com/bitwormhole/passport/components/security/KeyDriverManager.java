package com.bitwormhole.passport.components.security;

public interface KeyDriverManager {

    KeyDriver[] findByName(String name);

}
