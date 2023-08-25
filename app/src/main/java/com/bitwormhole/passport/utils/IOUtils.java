package com.bitwormhole.passport.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class IOUtils {

    public static long copy(InputStream src, OutputStream dst, byte[] buffer) throws IOException {
        if (src == null || dst == null) {
            return 0;
        }
        if (buffer == null) {
            buffer = new byte[1024 * 4];
        }
        long count = 0;
        for (; ; ) {
            int cb = src.read(buffer);
            if (cb < 0) {
                break;
            } else if (cb > 0) {
                dst.write(buffer, 0, cb);
                count += cb;
            } else {
                sleep(100);
            }
        }
        return count;
    }

    public static byte[] readAll(InputStream src, byte[] buffer) throws IOException {
        ByteArrayOutputStream dst = new ByteArrayOutputStream();
        copy(src, dst, buffer);
        dst.flush();
        return dst.toByteArray();
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
