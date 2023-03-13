package com.example.algos;

import java.util.Arrays;

public class DecodeWaysII {
    static class Solution {
        int MOD = 1000000007;
        char[] chars = {'a', 'b'};
        String str = new String(chars);

        int getCountDoubleDigit(String s, int i) {
            if (s.charAt(i) == '*' && s.charAt(i - 1) == '*') {
                return 15;
            } else if (s.charAt(i - 1) == '*') {
                return s.charAt(i) <= '6' ? 2 : 1;
            } else if (s.charAt(i) == '*') {
                return s.charAt(i - 1) == '1' ? 9 : (s.charAt(i - 1) == '2' ? 6 : 0);
            } else {
                String str = String.valueOf(s.charAt(i - 1)) + s.charAt(i);
                int x = Integer.parseInt(str);
                if (x < 10) {
                    return 0;
                } else if (x >= 10 && x <= 26) {
                    return 1;
                }
                System.out.println(str + x);
            }
            return 0;
        }

        int getCountSingleDigit(String s, int i) {
            if (s.charAt(i) == '*') {
                return 9;
            } else if (s.charAt(i) == '0') {
                return 0;
            } else {
                return 1;
            }
        }

        public int numDecodings(String s) {
            int n = s.length();
            int[] dp = new int[n];
            Arrays.fill(dp, 0);

            dp[0] = getCountSingleDigit(s, 0);
            if (s.length() > 1) {
                dp[1] = dp[0] * getCountSingleDigit(s, 1) + getCountDoubleDigit(s, 1);
            }
            for (int i = 2; i < n; i++) {
                dp[i] = ((dp[i - 1] * getCountSingleDigit(s, i)) % MOD +
                        (dp[i - 2] * getCountDoubleDigit(s, i)) % MOD) % MOD;
            }
//            Arrays.stream(dp).forEach(x -> System.out.print(x + ", "));
            System.out.println();
            return dp[n - 1];
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().numDecodings("*1"));
    }
}
