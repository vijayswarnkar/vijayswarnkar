package com.example.algos;

import java.util.LinkedList;
import java.util.Queue;

public class limit {
    static class Solution {
        public int longestSubarray(int[] nums, int limit) {
            Queue<Integer> min = new LinkedList<>();
            Queue<Integer> max = new LinkedList<>();

            min.add(0);
            max.add(0);

            int ans = 1;
            int j = 0;

            for (int i = 1; i < nums.length; i++) {
                while (!max.isEmpty() && nums[i] >= nums[max.peek()]) {
                    max.poll();
                }
                max.add(i);
                while (!min.isEmpty() && nums[i] <= nums[min.peek()]) {
                    min.poll();
                }
                min.add(i);
                while (true) {
                    int mx = nums[max.peek()];
                    int mn = nums[min.peek()];
                    System.out.println("mx=" + mx + ",mn=" + mn + ",i=" + i + ",j=" + j);
                    if (mx - mn <= limit) {
                        ans = Math.max(i - j + 1, ans);
                        break;
                    } else {
                        j++;
                        while (!max.isEmpty() && max.peek() < j) max.poll();
                        while (!min.isEmpty() && min.peek() < j) min.poll();
                    }
                }
            }
            return ans;
        }
    }

    public static void main(String[] args) {
//        System.out.println(new Solution().longestSubarray(new int[]{8, 2, 4, 7}, 4));
//        System.out.println(new Solution().longestSubarray(new int[]{10, 1, 2, 4, 7, 2}, 5));
//        System.out.println(new Solution().longestSubarray(new int[]{4,2,2,2,4,4,2,2}, 0));
        System.out.println(new Solution().longestSubarray(new int[]{2, 2, 2, 4, 4, 2, 5, 5, 5, 5, 5, 2}, 2));
    }
}
