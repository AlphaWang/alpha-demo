package com.alphawang.algorithm.dp;

/**
 * Given an input string s and a pattern p,
 * implement regular expression matching with support for '.' and '*' where:
 *
 * '.' Matches any single character.​​​​
 * '*' Matches zero or more of the preceding element.
 */
public class T0010_RegexMatching {

  /**
   * TODO top-down DP
   */
  public boolean isMatch(String s, String p) {
    return dp(s, 0, p, 0);
  }

  private boolean dp(String s, int i, String p, int j) {
    if (i >= s.length() && j >= p.length()) {
      return true;
    }
    if (i >= s.length() || j >= p.length()) {
      return false;
    }

    //如果当前字符匹配
    if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') {
      //如果下一个patter = *，
      if (j < p.length() - 1 && p.charAt(j+1) == '*') {
        return dp(s, i, p, j + 2)  //*匹配多次
            || dp(s, i + 1, p, j); //*匹配零次
      } else {
        //正常情况 继续判断
        return dp(s, i+1, p, j+1);
      }
    }
    //如果当前字符不匹配
    else {
      //如果下一个patter = *，
      if (j < p.length() - 1 && p.charAt(j+1) == '*') {
        return dp(s, i, p, j+2);
      } else {
        return false;
      }
    }
  }

  public static void main(String[] args) {
    T0010_RegexMatching sut = new T0010_RegexMatching();
    sut.test("aa", "a"); //false
    sut.test("aa", "a*"); //true
    sut.test("ab", ".*"); //true
  }

  private void test(String s, String p) {
    System.out.println(String.format("%s matching %s --> %s", s, p, isMatch(s, p)));
  }



}
