package com.example.algos;

public class Knight {
    static class Solution {
        private final int[][] dir = new int[][]{{-2, -1}, {-1, -2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, 2}, {-2, 1}};
        private double[][][] dp;

        public double knightProbability(int N, int K, int r, int c) {
            dp = new double[N][N][K + 1];
            return find(N, K, r, c);
        }

        public double find(int N, int K, int r, int c) {
            if (r < 0 || r > N - 1 || c < 0 || c > N - 1) return 0;
            if (K == 0) return 1;
            if (dp[r][c][K] != 0) return dp[r][c][K];
            double prob = 0.0;
            for (int[] ints : dir) {
                prob += (double) 1  * (find(N, K - 1, r + ints[0], c + ints[1])) / 8;
            }
            dp[r][c][K] = prob;
            return prob;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().knightProbability(3, 2, 0, 0)); // 0.0625
    }
}
