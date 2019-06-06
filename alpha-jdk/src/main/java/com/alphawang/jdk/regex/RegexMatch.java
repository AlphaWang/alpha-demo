package com.alphawang.jdk.regex;

public class RegexMatch {

    public static void main(String[] args) {
        String s1 = "/data/vendorItemMap/3013282608,2701306885/apiUrl/vendorItemDetail";
        String s2 = "/data/vendorItemMap/3013282608:2701306885:2701306885/apiUrl/vendorItemDetail";
        String s3 = "/data/vendorItemMap/3013282608,2701306885,2701306885/apiUrl/vendorItemDetail";
        String r = "/data/vendorItemMap/[\\s\\S]*/apiUrl/vendorItemDetail";

        System.out.println(s1.matches(r));
        System.out.println(s2.matches(r));
        System.out.println(s3.matches(r));
    }
}
