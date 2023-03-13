package com.example.algos;

import javafx.util.Pair;

import java.util.PriorityQueue;
import java.util.Queue;

public class MaxScore {
    static class Solution {
        public int maxResult(int[] nums, int k) {
            int n = nums.length;
            int[] dp = new int[n];
            Queue<Pair<Integer, Integer>> pq = new PriorityQueue<>((a, b) -> b.getKey() - a.getKey());
            pq.add(new Pair<>(nums[n - 1], n - 1));
            dp[n - 1] = nums[n - 1];

            for (int i = n - 2; i >= 0; i--) {
                while (true) {
                    Pair<Integer, Integer> top = pq.peek();
                    if (top == null) break;
                    if (top.getValue() <= i + k) {
                        dp[i] = nums[i] + top.getKey();
                        pq.add(new Pair<>(dp[i], i));
                        break;
                    } else {
                        pq.poll();
                    }
                }
            }
            return dp[0];
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().maxResult(new int[]{1, -1, -2, 4, -7, 3}, 2));
    }
}
