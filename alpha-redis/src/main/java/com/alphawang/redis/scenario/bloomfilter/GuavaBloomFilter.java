package com.alphawang.redis.scenario.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

public class GuavaBloomFilter {

    public static void main(String[] args) {
        BloomFilter bloomFilter = BloomFilter.create(Funnels.stringFunnel(
            Charset.defaultCharset()),
            1000000,
            0.001);

        boolean result = bloomFilter.put("alpha");
        boolean exists = bloomFilter.mightContain("alpha");
        boolean exists2 = bloomFilter.mightContain("beta");

        System.out.println("put alpha: " + result);
        System.out.println("alpha exists: " + exists);
        System.out.println("beta exists: " + exists2);
    }
}
