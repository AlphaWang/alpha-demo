package com.alphawang.algorithm.dp;

/**
 * Given a string s. In one step you can insert any character at any index of the string.
 *
 * Return the minimum number of steps to make s palindrome.
 */
public class T1312_MinInsertionsPalindrome {

  /**
   * DP, 自上而下
   * timeout for "tldjbqjdogipebqsohdypcxjqkrqltpgviqtqz"?
   */
  Integer[][] memo;
  public int minInsertions(String s) {
    int n = s.length();
    memo = new Integer[n][n];
    return dp(s, 0, n - 1);
  }

  private int dp(String s, int i, int j) {
    if (i >= j || i < 0 || j >= s.length()) {
      return 0;
    }
    if (memo[i][j] != null) {
      return memo[i][j];
    }
    if (s.charAt(i) == s.charAt(j)) {
      return memo[i][j] = dp(s, i+1, j-1);
    } else {
      return memo[i][j] = Math.min(
        dp(s, i+1, j),
        dp(s, i, j-1)
      ) + 1;
    }
  }

  public static void main(String[] args) {
    T1312_MinInsertionsPalindrome sut = new T1312_MinInsertionsPalindrome();
    sut.test("zzazz"); // 0
    sut.test("mbadm"); // 2, "mbdadbm" or "mdbabdm"
    sut.test("leetcode"); // 5, "leetcodocteel"
    sut.test("tldjbqjdogipebqsohdypcxjqkrqltpgviqtqz"); //
  }

  private void test(String s) {
    System.out.println(s + " --> " + minInsertions(s));
  }

}
