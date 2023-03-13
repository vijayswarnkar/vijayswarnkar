package com.example.algos;

import java.util.Arrays;


class StonePiles {
    static class Solution {

        int[][] dp;

        int winner(int[] piles, int s, int e) {
            if (s == e - 1) return dp[s][e] = Math.max(piles[s], piles[e]);
            if (dp[s][e] != 0) return dp[s][e];
            int x = Math.min(winner(piles, s + 2, e), winner(piles, s + 1, e - 1)) + piles[s];
            int y = Math.min(winner(piles, s + 1, e - 1), winner(piles, s, e - 2)) + piles[e];
            return dp[s][e] = Math.max(x, y);
        }

        boolean stoneGame(int[] piles) {
            int n = piles.length;
            int x = 0;
            dp = new int[n][n];
            for (int i = 0; i < n; i++) {
                Arrays.fill(dp[i], 0);
                x += piles[i];
            }
            int y = winner(piles, 0, n - 1);
            System.out.println(y);
            int z = x - y;
            return y > z;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().stoneGame(new int[]{5, 3, 4, 5}));
    }
}
