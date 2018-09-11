package com.alphawang.redis.scenario.bloomfilter;

import com.alphawang.redis.support.rebloom.Client;

public class RedisBloomFilter {
    
    public static void main(String[] args) {
        Client client = new Client();

        client.delete("codehole");
        for (int i = 0; i < 100000; i++) {
            client.add("codehole", "user" + i);
            boolean ret = client.exists("codehole", "user" + (i + 1));
            if (ret) {
                System.out.println(i);
                break;
            }
        }

        client.close();
    }
    
}

