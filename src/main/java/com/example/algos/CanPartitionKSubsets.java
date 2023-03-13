package com.example.algos;


import javafx.util.Pair;

import java.util.*;

public class CanPartitionKSubsets {
    static class Solution {
        boolean flag = false;
        Map<Pair<Set<Integer>, Integer>, Boolean> dp = new HashMap<>();

        boolean solve(int[] nums, int idx, int k, int currSum, int halfSum, Set<Integer> vis) {
            if (flag) {
                return true;
            }
            boolean ans = false;
            if (currSum == halfSum) {
//                System.out.println("found:" + k + ", set:" + vis);
                if (k == 1) {
                    flag = true;
                    return true;
                }
                ans = solve(nums, 0, k - 1, 0, halfSum, vis);
            } else if (idx >= nums.length || currSum > halfSum) {
                ans = false;
            } else if (dp.containsKey(new Pair<>(vis, k))) {
                return dp.get(new Pair<>(vis, k));
            } else {
                if (!vis.contains(idx)) {
                    vis.add(idx);
                    ans |= solve(nums, idx + 1, k, currSum + nums[idx], halfSum, vis);
                    vis.remove(idx);
                }
                ans |= solve(nums, idx + 1, k, currSum, halfSum, vis);
            }
            dp.put(new Pair<>(vis, k), ans);
            return ans;
        }

        public boolean canPartitionKSubsets(int[] nums, int k) {
            int total = Arrays.stream(nums).sum();
            if (total % k != 0) return false;
            Set<Integer> vis = new HashSet<>();
            return solve(nums, 0, k, 0, total / k, vis);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().canPartitionKSubsets(new int[]{4, 3, 2, 3, 5, 2, 1}, 4));
        System.out.println(new Solution().canPartitionKSubsets(new int[]{10, 5, 5}, 4));
    }
}
