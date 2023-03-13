package com.example.algos;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;

public class MinimumAreaRectangle {
    static class Solution {
        public int minAreaRect(int[][] points) {
            Set<Pair<Integer, Integer>> set = new HashSet<>();
            int area = Integer.MAX_VALUE;
            for (int i = 0; i < points.length; i++) {
                int x1 = points[i][0], y1 = points[i][1];
                set.add(new Pair<>(x1, y1));
                for (int j = i + 1; j < points.length; j++) {
                    int x2 = points[j][0], y2 = points[j][1];
                    set.add(new Pair<>(x2, y2));
                    if (x1 == x2 || y1 == y2) continue;
                    else {
                        if(set.contains(new Pair<>(x1, y2)) && set.contains(new Pair<>(x2, y1))){
                            area = Math.min(area, Math.abs(x1-x2)*Math.abs(y1-y2));
                        }
                    }
                }
            }
            return (area == Integer.MAX_VALUE ? 0 : area);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().minAreaRect(new int[][]{
                {1, 1}, {1, 3}, {3, 1}, {3, 3}, {2, 2}
        }));
    }
}
