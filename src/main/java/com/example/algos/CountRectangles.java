package com.example.algos;

public class CountRectangles {
    static class Solution {

        int numSubmat(int[][] mat) {
            int m = mat.length;
            int n = mat[0].length;
            int ans = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (mat[i][j] > 0) {
                        mat[i][j] += (i == 0 ? 0 : mat[i - 1][j]);
                        int min = mat[i][j];
                        for (int k = j; k >= 0; k--) {
                            min = Math.min(min, mat[i][k]);
                            ans += min;
                        }
                    }
                }
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().numSubmat(new int[][]{
                {1, 0, 1},
                {1, 1, 0},
                {1, 1, 0}
        }));
    }
}
