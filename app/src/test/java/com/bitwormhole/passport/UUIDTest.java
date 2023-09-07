package com.bitwormhole.passport;

import com.bitwormhole.passport.data.dxo.UUID;
import com.bitwormhole.passport.services.UUIDGenerater;
import com.bitwormhole.passport.supports.db.UUIDGeneraterImpl;
import com.bitwormhole.passport.utils.HexUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UUIDTest {

    @Test
    public void testUUIDwithString() {

        String[] src = new String[]{
                null, "", "0", "01", "0123456",
                "0123456789abcdef",
                "0123456789abcdef0123456789abcdef",
                "0123456789abcdef0123456789abcdef0123456789abcdef",
        };

        for (String s1 : src) {
            UUID u = new UUID(s1);
            String s2 = u.toString();
            System.out.println("new UUID:" + s2 + " with_string:" + s1);
        }


        // assertEquals(4, 2 + 2);
    }

    @Test
    public void testUUIDwithBytes() {

        List<byte[]> src = new ArrayList<>();
        src.add(null);
        src.add(new byte[0]);
        src.add(new byte[]{0});
        src.add(new byte[]{0, 1});
        src.add(new byte[]{0, 1, 2, 3, 4, 5, 6});
        src.add(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f});
        src.add(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f, 0x7f, 0x7f});

        for (byte[] b1 : src) {
            UUID u = new UUID(b1);
            // byte[] b2 = u.toBytes();
            String s1 = HexUtils.stringify(b1);
            String s2 = u.toString();
            System.out.println("new UUID: " + s2 + " with_bytes: " + s1);
        }

        // assertEquals(4, 2 + 2);
    }

    @Test
    public void testUUIDGen() {

        UUIDGenerater g = new UUIDGeneraterImpl();
        List<UUID> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            UUID u1 = g.generate();
            UUID u2 = g.generate("a:1", "b:2");
            list.add(u1);
            list.add(u2);
        }

        for (UUID u : list) {
            System.out.println("gen UUID: " + u);
        }
    }
}
