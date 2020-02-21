package com.alphawang.jdk.regex;

import static com.alphawang.jdk.regex.RegexFinder.find;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatch {

    public static void main(String[] args) {
        String s1 = "/data/vendorItemMap/3013282608,2701306885/apiUrl/vendorItemDetail";
        String s2 = "/data/vendorItemMap/3013282608:2701306885:2701306885/apiUrl/vendorItemDetail";
        String s3 = "/data/vendorItemMap/3013282608,2701306885,2701306885/apiUrl/vendorItemDetail";
        String r = "/data/vendorItemMap/[\\s\\S]*/apiUrl/vendorItemDetail";

        System.out.println(s1.matches(r));
        System.out.println(s2.matches(r));
        System.out.println(s3.matches(r));
        
        testMatch();
    }
    
    private static void testMatch() {
        String source = "/modular/v1/pages/1/link-categories/components/189408?cartSessionId=7ff9e955756a47e59f51835cdf5bc68d3224beeb&entrySize=0&filterVersion=V2";

        Pattern pattern = Pattern.compile("/modular/v1/pages/([0-9])(.*)\\?(.*)");
        
        System.out.print("[Source] " + source + ", [Pattern] " + pattern + " : ");
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            System.out.print(matcher.group(0) + " ");
            System.out.print(matcher.group(1) + " ");
            System.out.print(matcher.group(2) + " ");
            System.out.print(matcher.group(3) + " ");
        }
        System.out.println();
    }
}
