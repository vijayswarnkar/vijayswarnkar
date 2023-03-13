package com.example.algos;

import javafx.util.Pair;

import java.util.Stack;

public class MinSubArraySum {
    static class Solution {
        public int sumSubarrayMins(int[] A) {
            int ans = 0;
            int mod = 1_000_000_007;
            Stack<Pair<Integer, Integer>> stack = new Stack<>();
            int total = 0;
            for(int i=0;i<A.length;i++) {
                int ctr = 0;
                int sum = 0;
                while (!stack.isEmpty() && A[i] <= stack.peek().getKey()) {
                    Pair<Integer, Integer> p = stack.pop();
                    ctr += p.getValue();
                    sum -= p.getValue() * p.getKey();
                    if(sum < 0) sum += mod;
                }
                ctr += 1;
                sum = (int) ((sum + (A[i] * ctr)%mod)%mod);
                ans = (int) ((ans+sum)%mod);
                total = (int) ((total+ans)%mod);
                stack.add(new Pair<>(A[i], ctr));
//                System.out.println(stack + ": " + ans);
            }
            return total;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().sumSubarrayMins(new int[]{3,1,2,4}));
    }
}
