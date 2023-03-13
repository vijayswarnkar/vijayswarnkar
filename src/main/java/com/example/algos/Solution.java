package com.example.algos;

class Main {
    public static int countSquares(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] dp = new int[m][n];

        int ans = matrix[0][0];
        dp[0][0] = ans;
        for (int i = 1; i < n; i++) {
            if (matrix[0][i] == 1) {
                dp[0][i] = 1;
                ans++;
            }
        }
        for (int i = 1; i < m; i++) {
            if (matrix[i][0] == 1) {
                dp[i][0] = 1;
                ans++;
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 1) {
                    int mn = Math.min(dp[i - 1][j], dp[i][j - 1]);
                    if (mn == 0) {
                        dp[i][j] = 1;
                    } else {
                        if (matrix[i - mn][j - mn] == 1) {
                            dp[i][j] = mn + 1;
                        } else {
                            dp[i][j] = mn;
                        }
                    }
                } else {
                    dp[i][j] = 0;
                }
                ans += dp[i][j];
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {0, 1, 1, 1},
                {1, 1, 1, 1},
                {0, 1, 1, 1}
        };
        System.out.println(countSquares(matrix));
    }
}
