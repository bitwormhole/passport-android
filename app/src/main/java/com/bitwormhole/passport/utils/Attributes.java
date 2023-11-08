package com.bitwormhole.passport.utils;

public interface Attributes {

    /****
     * 获取属性 required
     * */
    Object getAttribute(String name);

    /****
     * 获取属性 optional
     * */
    Object getAttribute(String name, Object defaultValue);

    /****
     * 设置属性
     * */
    void setAttribute(String name, Object value);

}
