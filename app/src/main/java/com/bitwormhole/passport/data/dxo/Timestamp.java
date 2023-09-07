package com.bitwormhole.passport.data.dxo;

import java.time.format.DateTimeFormatter;

public class Timestamp {

    public final long t;

    public Timestamp() {
        this.t = System.currentTimeMillis();
    }

    public Timestamp(long time) {
        this.t = time;
    }

    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

    public String toString() {
        // = DateTimeFormatter.ofPattern( "" ) ;
        return "" + t;
    }

}
