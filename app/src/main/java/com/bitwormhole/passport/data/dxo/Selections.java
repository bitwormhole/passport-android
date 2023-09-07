package com.bitwormhole.passport.data.dxo;


// Selections 提供用于 ‘selection’ 字段的一些常用取值
public final class Selections {

    public final static String CURRENT = "current";

    public final static String MAIN = "main";


    private Selections() {
    }

    public static boolean isCurrent(String s) {
        return CURRENT.equalsIgnoreCase(s);
    }

    public static boolean isMain(String s) {
        return MAIN.equalsIgnoreCase(s);
    }

}
