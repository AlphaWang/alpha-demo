package com.alphawang.jdk.string;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Slf4j
public class StringEncode {

    public static final String DEFAULT_CHARSET = "utf-8";

    public static String encode(String value) {
        if (value == null) {
            return null;
        }

        try {
            return URLEncoder.encode(value, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static String decode(String value) {
        try {
            return URLDecoder.decode(value, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        String msg = "Hello | World!";
        log.info("origin {}", msg);

        String encoded = encode(msg);
        log.info("encode {}", encoded);

        String decoded = decode(encoded);
        log.info("decode {}", decoded);

        String decoded2 = decode(decoded);
        log.info("decode2 {}", decoded2);

        log.info(StringUtils.substring("abc", 0, 256));

    }
}
