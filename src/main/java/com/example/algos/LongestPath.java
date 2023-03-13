package com.example.algos;

import javafx.util.Pair;

import java.util.*;

class Solution {
    public int kEmptySlots(int[] flowers, int k) {
        int[] days = new int[flowers.length];
        for (int i = 0; i < flowers.length; i++) {
            days[flowers[i] - 1] = i + 1;
        }

        int ans = Integer.MAX_VALUE;
        int left = 0, right = k+1;

        search: while (right < days.length) {
            for (int i = left+1; i < right; ++i) {
                if (days[i] < days[left] || days[i] < days[right]) {
                    left = i;
                    right = i + k + 1;
                    continue search;
                }
            }
            ans = Math.min(ans, Math.max(days[left], days[right]));
            left = right;
            right = left + k + 1;
        }

        return ans < Integer.MAX_VALUE ? ans : -1;
    }
}

public class LongestPath {
    static class Solution {

        int solve(String input) {
            String[] arr = input.split("\n");
            Stack<Pair<Integer, Integer>> st = new Stack<>();
            int total = 0;
            int ans = 0;

            for (String line : arr) {
                int t = 0;
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '\t') t++;
                    else break;
                }
                while(!st.isEmpty()){
                    Pair<Integer, Integer> top = st.peek();
                    if(top.getKey() >= t) {
                        total -= top.getValue();
                        st.pop();
                    } else {
                        break;
                    }
                }

                int currLen = line.length()-t;
                if (line.indexOf('.') >= 0) {
                    ans = Math.max(ans, total + currLen + t);
                } else {
                    total += currLen;
                    st.add(new Pair(t, currLen));
                }
            }
            Set<String> set = new TreeSet();
            List<String> list = new ArrayList(set);
            Collections.sort(list);

            return ans;
        }

        public int lengthLongestPath(String input) {
            return solve(input);
        }

    }

    public static void main(String[] args) {
        System.out.println(new Solution().lengthLongestPath("dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext"));
        System.out.println(new Solution().lengthLongestPath("file1.txt\nfile2.txt\nlongfile.txt"));
        System.out.println(new Solution().lengthLongestPath("dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext"));
        System.out.println(new Solution().lengthLongestPath("a.txt"));
        System.out.println(new Solution().lengthLongestPath("dir\n        file.txt"));
        System.out.println(new Solution().lengthLongestPath("a"));
    }
}
