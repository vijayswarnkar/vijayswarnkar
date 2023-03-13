package com.example.algos;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class MinCostToPaint {
    static class Solution {
        int n;
        int colors = 3;
        Map<Pair<Integer, Integer>, Integer> dp = new HashMap<>();

        int solve(int[][] costs, int id, int prevColor) {
            if (id == n) {
                return 0;
            }
            if (dp.containsKey(new Pair<>(id, prevColor))) {
                return dp.get(new Pair<>(id, prevColor));
            }
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < colors; i++) {
                if (i != prevColor) {
                    min = Math.min(min, costs[id][i] + solve(costs, id + 1, i));
                }
            }
            dp.put(new Pair<>(id, prevColor), min);
            return min;
        }

        public int minCost(int[][] costs) {
            n = costs.length;
            int ans = Integer.MAX_VALUE;

            ans = Math.min(solve(costs, 0, -1), ans);
            return ans;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().minCost(new int[][]{
                {17, 2, 17}, {16, 16, 5}, {14, 3, 19}
        }));
    }
}
