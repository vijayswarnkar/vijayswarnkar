package com.example.algos;

import java.util.Arrays;

public class MaxTransfers {
    static class Solution {
        int arr[][];
        int vis[];

        int solve(int id, int val) {
            if (vis[id] == 1) {
                return val;
            }
            vis[id] = 1;
            int x = 0;
            for (int i = 0; i < arr[id].length; i++) {
                if (arr[id][i] > 0) {
                    x += solve(i, arr[id][i]);
                }
            }
            return x;
        }

        public int maximumRequests(int n, int[][] requests) {
            int ans = 0;
            arr = new int[n][n];
            vis = new int[n];
            Arrays.stream(requests).forEach(request -> {
                arr[request[0]][request[1]]++;
            });
            Arrays.stream(arr).forEach(a -> {
                Arrays.stream(a).forEach(x -> System.out.print(x));
                System.out.println();
            });
            int x = solve(0, 0);
            System.out.println(x);
            return ans;
        }

    }

    public static void main(String[] args) {
        System.out.println(new Solution().maximumRequests(5, new int[][]{{0, 1}, {1, 0}, {0, 1}, {1, 2}, {2, 0}, {3, 4}}));
    }
}
