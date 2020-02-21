package com.alphawang.jdk.regex;

import static com.alphawang.jdk.regex.RegexFinder.find;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMode {

    private static Pattern pattern_greedy = Pattern.compile("ab{1,3}c");
    private static Pattern pattern_reluctant = Pattern.compile("ab{1,3}?c");
    private static Pattern pattern_possessive = Pattern.compile("ab{1,3}+c");

    public static void main(String[] args) {
        System.out.println("-------------");
        find("abc", pattern_greedy);
        find("abc", pattern_reluctant);  //不会回溯
        find("abc", pattern_possessive);

        System.out.println("-------------");
        find("abbc", pattern_greedy);   //发生回溯
        find("abbc", pattern_reluctant);
        find("abbc", pattern_possessive);
        find("abbc", Pattern.compile("ab{1,3}+bc")); //不匹配，不会回溯

        System.out.println("-------------");
        find("abbbc", pattern_greedy);
        find("abbbc", pattern_reluctant);
        find("abbbc", pattern_possessive);

        System.out.println("-------------");
        find("abcdabbbcd", pattern_greedy);
        find("abcdabbbcd", pattern_reluctant);
        find("abcdabbbcd", pattern_possessive);
    }

    
}
