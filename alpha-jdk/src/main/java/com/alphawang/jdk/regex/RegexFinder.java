package com.alphawang.jdk.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexFinder {

    public static void find(String source, Pattern pattern) {
        System.out.print("[Source] " + source + ", [Pattern] " + pattern + " : ");
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            System.out.print(matcher.group(0) + " ");
            System.out.print(matcher.group(1) + " ");
            System.out.print(matcher.group(2) + " ");
        }
        System.out.println();
    }

}
