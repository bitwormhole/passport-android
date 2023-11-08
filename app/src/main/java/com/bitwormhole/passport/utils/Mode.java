package com.bitwormhole.passport.utils;

import org.bouncycastle.math.raw.Mod;

/****
 * Mode 表示线程安全模式
 * */
public final class Mode {

    private static final int SAFE = 1;
    private static final int FAST = 2;


    public static final Mode Fast = new Mode(FAST);
    public static final Mode Safe = new Mode(SAFE);


    private final int value;

    private Mode(int mode) {
        value = mode;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Mode) {
            Mode o1 = this;
            Mode o2 = (Mode) other;
            return o1.value == o2.value;
        }
        return false;
    }

    @Override
    public String toString() {
        switch (value) {
            case FAST:
                return "thread_safe_mode:fast";
            case SAFE:
                return "thread_safe_mode:safe";
        }
        return "thread_safe_mode:undefine";
    }
}
