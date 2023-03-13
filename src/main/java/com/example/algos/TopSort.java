package com.example.algos;

import java.util.*;

public class TopSort {
    static class Solution {
        boolean[] vis;
        List<Integer>[] graph;
        Stack<Integer> topSort;
        boolean cycle;

        void dfs(int node, Set<Integer> parent) {
            if (cycle) return;
            if (!vis[node]) {
                vis[node] = true;
                parent.add(node);
                for (Integer child : graph[node]) {
                    if (parent.contains(child)) {
                        cycle = true;
                        return;
                    }
                    dfs(child, parent);
                }
                parent.remove(node);
                topSort.add(node);
            }
        }

        boolean dfs_cycle(int node, Set<Integer> parent) {
            if (!vis[node]) {
                vis[node] = true;
                parent.add(node);
                for (Integer child : graph[node]) {
                    if (parent.contains(child)) {
                        return true;
                    }
                    boolean cycle = dfs_cycle(child, parent);
                    if(cycle) {
                        return true;
                    }
                }
                parent.remove(node);
                topSort.add(node);
            }
            return false;
        }

        public int[] findOrder(int numCourses, int[][] prerequisites) {
            vis = new boolean[numCourses];
            graph = new ArrayList[numCourses];
            topSort = new Stack<>();
            for (int i = 0; i < numCourses; i++) {
                graph[i] = new ArrayList<>();
            }
            for (int[] x : prerequisites) {
                graph[x[1]].add(x[0]);
            }
            cycle = false;
//            for (int i = 0; i < numCourses; i++) {
//                dfs(i, new HashSet<>());
//                if (cycle) return new int[0];
//            }
            for (int i = 0; i < numCourses; i++) {
                if(dfs_cycle(i, new HashSet<>())) return new int[0];
            }

            int[] order = new int[numCourses];
            int i = 0;
            while (!topSort.empty()) {
                order[i++] = topSort.pop();
            }
            return order;
        }
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Solution().findOrder(4, new int[][]{{1, 0}, {0, 1}, {2, 0}, {3, 1}, {3, 2}})));
        System.out.println(Arrays.toString(new Solution().findOrder(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}})));
        System.out.println(Arrays.toString(new Solution().findOrder(1, new int[][]{})));
    }
}
