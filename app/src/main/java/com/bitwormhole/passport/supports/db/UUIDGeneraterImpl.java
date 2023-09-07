package com.bitwormhole.passport.supports.db;

import com.bitwormhole.passport.data.dxo.UUID;
import com.bitwormhole.passport.services.UUIDGenerater;
import com.bitwormhole.passport.utils.HashUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UUIDGeneraterImpl implements UUIDGenerater {


    private long startupAt;
    private long indexer;
    private final Random rand;


    public UUIDGeneraterImpl() {
        long now = System.currentTimeMillis();
        this.startupAt = now;
        this.rand = new Random(now);
        this.indexer = 0;
    }

    @Override
    public UUID generate() {
        return this.generate("p:none");
    }

    @Override
    public UUID generate(String... params) {
        Builder b = this.newBuilder(params);
        return b.create();
    }

    private final class Builder {

        final List<String> headers;

        public Builder(String[] params) {
            this.headers = new ArrayList<>();
            if (params != null) {
                for (String p : params) {
                    headers.add(p);
                }
            }
        }

        public UUID create() {
            List<String> src = this.headers;
            StringBuilder sb = new StringBuilder();
            Collections.sort(src);
            for (String s : src) {
                sb.append(s).append('\n');
            }
            String str = sb.toString();
            byte[] data = str.getBytes(StandardCharsets.UTF_8);
            byte[] sum = HashUtils.computeMD5sum(data);
            return new UUID(sum);
        }
    }

    private synchronized Builder newBuilder(String[] params) {
        long t0 = this.startupAt;
        long t1 = System.currentTimeMillis();
        long index = this.indexer++;
        long nonce = this.rand.nextLong();
        Builder b = new Builder(params);
        b.headers.add("t0:" + t0);
        b.headers.add("t1:" + t1);
        b.headers.add("nonce:" + nonce);
        b.headers.add("index:" + index);
        return b;
    }

}
