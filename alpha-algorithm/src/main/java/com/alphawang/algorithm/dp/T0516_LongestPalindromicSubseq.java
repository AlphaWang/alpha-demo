package com.alphawang.algorithm.dp;

public class T0516_LongestPalindromicSubseq {

  /**
   * DP: 自上而下
   */
  Integer[][] memo;
  public int longestPalindromeSubseq(String s) {
    int n = s.length();
    memo = new Integer[n][n];
    return dp(s, 0, n - 1);
  }

  private int dp(String s, int i, int j) {
    if (i == j) {
      return 1;
    }
    if (i > j || i < 0 || j >= s.length()) {
      return 0;
    }

    if (memo[i][j] != null) {
      return memo[i][j];
    }

    if (s.charAt(i) == s.charAt(j)) {
      return memo[i][j] = dp(s, i + 1, j - 1) + 2;
    } else {
      return memo[i][j] = Math.max(
          dp(s, i + 1, j),
          dp(s, i, j - 1));
    }
  }


  /**
   * DP: 自下而上
   */
  public int longestPalindromeSubseq2(String s) {
    int n = s.length();
    int[][] dp = new int[n][n];

    for (int i = 0; i < n; i++) {
      dp[i][i] = 1;
    }

    for (int i = n - 2; i >= 0; i--) {
      for (int j = i + 1; j < n; j++) {
        if (s.charAt(i) == s.charAt(j)) {
          dp[i][j] = dp[i+1][j-1] + 2;
        } else {
          dp[i][j] = Math.max(
              dp[i+1][j],
              dp[i][j-1]
          );
        }
      }
    }


    return dp[0][n-1];
  }

  public static void main(String[] args) {
    T0516_LongestPalindromicSubseq sut = new T0516_LongestPalindromicSubseq();
    sut.test("bbbab"); //4 "bbbb"
    sut.test("cbbd"); //2 "bb"
  }

  private void test(String s) {
    System.out.println(s + " -->" + longestPalindromeSubseq2(s));
  }

}
