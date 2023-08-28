package com.bitwormhole.passport.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashUtils {


    private static byte[] compute(byte[] input, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            return md.digest(input);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
    }


    public static byte[] computeSHA256sum(byte[] input) {
        return compute(input, "SHA-256");
    }

    public static byte[] computeSHA1sum(byte[] input) {
        return compute(input, "SHA-1");
    }

    public static byte[] computeMD5sum(byte[] input) {
        return compute(input, "MD5");
    }

}
