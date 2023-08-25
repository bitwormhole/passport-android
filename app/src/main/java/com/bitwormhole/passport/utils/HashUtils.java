package com.bitwormhole.passport.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashUtils {

    public static byte[] computeSHA256sum(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

    public static byte[] computeSHA1sum(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return md.digest(input);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

}
