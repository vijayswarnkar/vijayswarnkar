package com.example.algos;

public class RegularExpressionMatching {
    static class Solution {
        int[][] dp;

        boolean solve(String s, String p, int i, int j) {
            if(dp[i][j] != -1){
                return dp[i][j] == 1 ? true : false;
            }
            boolean ans = false;
            if(i >= s.length() && j >= p.length()) return true;
//            System.out.println(i + "," + j);
            if (i < s.length() && j < p.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.')) {
                if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                    ans |= solve(s, p, i, j + 2); // * = 0
                    ans |= solve(s, p, i + 1, j); // * > 0
                } else {
                    ans |= solve(s, p, i + 1, j + 1);
                }
            } else {
                if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                    ans |= solve(s, p, i, j + 2);
                }
            }
            dp[i][j] = ans == true ? 1 : 0;
            return ans;
        }

        public boolean isMatch(String s, String p) {
            int m = s.length() + 1;
            int n = p.length() + 1;
            dp = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    dp[i][j] = -1;
                }
            }
            return solve(s, p, 0, 0);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().isMatch("aa", "a"));
        System.out.println(new Solution().isMatch("aa", "a*"));
        System.out.println(new Solution().isMatch("ab", ".*"));
        System.out.println(new Solution().isMatch("aab", "c*a*b"));
        System.out.println(new Solution().isMatch("", "c*c*")); // true
    }
}
