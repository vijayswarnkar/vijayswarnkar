package com.example.algos;

import java.util.Arrays;

public class PartitionEqualSubsetSum {
    static class Solution {
        int halfSum;
        Boolean[][] dp;

        boolean solve(int[] nums, int idx, int sum) {
            if (idx == nums.length || sum > halfSum) return false;
            if (sum == halfSum) return true;
            if(dp[idx][sum] != null) {
                return dp[idx][sum];
            }

            boolean ans = solve(nums, idx+1, sum) ||
                    solve(nums, idx+1, sum + nums[idx]);

            dp[idx][sum] = ans;
            return ans;
        }

        public boolean canPartition(int[] nums) {
            int tolSum = Arrays.stream(nums).sum();
            if (tolSum % 2 > 0) return false;
            halfSum = tolSum / 2;
            dp = new Boolean[nums.length + 1][halfSum + 1];
            return solve(nums, 0, 0);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().canPartition(new int[]{1, 5, 11, 5}));
    }
}
