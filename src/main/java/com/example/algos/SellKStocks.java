package com.example.algos;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class SellKStocks {
    static class Solution {

        int dp[][];
        Map<Pair<Pair<Integer, Integer>, Integer>, Integer> map;

        Pair<Pair<Integer, Integer>, Integer> getKey(int idx, int k, int state) {
            return new Pair<>(new Pair<>(idx, k), state);
        }

        int solve(int idx, int k, int state, int[] prices, int buyIndex) {
            if (idx >= prices.length || k == 0) {
                return 0;
            }
            if (map.containsKey(getKey(idx, k, buyIndex))) {
                return map.get(getKey(idx, k, buyIndex));
            }
//            System.out.println(idx + "," + k + "," + state + "," + buyPrice);
            int ans = 0;
            if (state == 0) {
                ans = Math.max(
                        solve(idx + 1, k, 1, prices, idx),
                        solve(idx + 1, k, 0, prices, -1)
                );
            } else {
                ans = Math.max(
                        prices[idx] - prices[buyIndex] + solve(idx + 1, k - 1, 0, prices, -1),
                        solve(idx + 1, k, 1, prices, buyIndex)
                );
            }
            ans = Math.max(ans, 0);
            map.put(getKey(idx, k, buyIndex), ans);
            return ans;
        }

        public int maxProfit(int k, int[] prices) {
            map = new HashMap<>();
            int ans = solve(0, k, 0, prices, -1);
            return ans;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().maxProfit(2, new int[]{3, 2, 6, 5, 0, 3}));
        System.out.println(new Solution().maxProfit(2, new int[]{2, 4, 1}));
    }
}
