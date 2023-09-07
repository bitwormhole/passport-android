package com.bitwormhole.passport.supports.converting;

import com.bitwormhole.passport.components.bo.BaseBO;
import com.bitwormhole.passport.data.dxo.Timestamp;
import com.bitwormhole.passport.data.dxo.UUID;
import com.bitwormhole.passport.data.entity.BaseEntity;
import com.bitwormhole.passport.web.dto.BaseDTO;

public final class BaseFields {

    public static void convertBaseFields(BaseEntity src, BaseBO dst) {
        dst.uuid = convertUUID(src.uuid);
        dst.createdAt = convertTimestamp(src.createdAt);
        dst.deletedAt = convertTimestamp(src.deletedAt);
        dst.updatedAt = convertTimestamp(src.updatedAt);
    }

    public static void convertBaseFields(BaseBO src, BaseEntity dst) {
        dst.uuid = convertUUID(src.uuid);
        dst.createdAt = convertTimestamp(src.createdAt);
        dst.deletedAt = convertTimestamp(src.deletedAt);
        dst.updatedAt = convertTimestamp(src.updatedAt);
    }

    public static void convertBaseFields(BaseDTO src, BaseBO dst) {
        dst.uuid = convertUUID(src.uuid);
        dst.createdAt = convertTimestamp(src.createdAt);
        dst.deletedAt = convertTimestamp(src.deletedAt);
        dst.updatedAt = convertTimestamp(src.updatedAt);
    }

    public static void convertBaseFields(BaseBO src, BaseDTO dst) {
        dst.uuid = convertUUID(src.uuid);
        dst.createdAt = convertTimestamp(src.createdAt);
        dst.deletedAt = convertTimestamp(src.deletedAt);
        dst.updatedAt = convertTimestamp(src.updatedAt);
    }


    private static UUID convertUUID(String value) {
        return new UUID(value);
    }

    private static String convertUUID(UUID o) {
        if (o == null) {
            return "";
        }
        return o.toString();
    }


    private static long convertTimestamp(Timestamp o) {
        if (o == null) {
            return 0;
        }
        return o.t;
    }

    private static Timestamp convertTimestamp(long value) {
        return new Timestamp(value);
    }
}
