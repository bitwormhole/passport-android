package com.bitwormhole.passport.utils;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.security.PublicKey;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static byte[] computeFingerprintSHA256(PublicKey pk) {
        byte[] d = pk.getEncoded();
        return HashUtils.computeSHA256sum(d);
    }

    public static byte[] computeFingerprintSHA1(PublicKey pk) {
        byte[] d = pk.getEncoded();
        return HashUtils.computeSHA1sum(d);
    }

    public static String toPEM(PublicKey pk) {

        String prefix = "PUBLIC KEY";
        byte[] data = pk.getEncoded();

        StringWriter builder = new StringWriter();
        PemWriter pw = new PemWriter(builder);

        try {
            pw.writeObject(new PemObject(prefix, data));
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

}
