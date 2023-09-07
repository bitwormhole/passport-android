package com.bitwormhole.passport.data.dxo;

public class LongID {

    public long id;

    public LongID() {
    }

    public LongID(long id) {
        this.id = id;
    }

    public static long valueOf(LongID lid) {
        if (lid == null) {
            return 0;
        }
        return lid.id;
    }

}
