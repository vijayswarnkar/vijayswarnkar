package com.example.algos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinaryLifting {
    static class TreeAncestor {
        int LEVELS = 20;
        Map<Integer, List<Integer>> childArray = new HashMap<>();
        int[][] up;

        // "a,b,c" -> class<String[]>, class<int>, class<float>

        void binary_lifting(int src, int parent) {
            up[src][0] = parent;
            for (int i = 1; i < LEVELS; i++) {
                if (up[src][i - 1] == -1) {
                    up[src][i] = -1;
                } else {
                    up[src][i] = up[up[src][i - 1]][i - 1];
                }
            }
            if (childArray.containsKey(src)) {
//                System.out.println(src);
                List<Integer> childs = childArray.get(src);
                for (Integer child : childs) {
                    binary_lifting(child, src);
                }
            }
        }

        public TreeAncestor(int n, int[] parentArray) {
            for (int i = 0; i < parentArray.length; i++) {
                List<Integer> x = childArray.getOrDefault(parentArray[i], new ArrayList());
                x.add(i);
                childArray.put(parentArray[i], x);
            }
            System.out.println(childArray);

            int root = (int) childArray.get(-1).get(0);
            int parent = -1;
            up = new int[n][LEVELS];

            binary_lifting(root, parent);
//            for (int i=0;i<n;i++){
//                System.out.print(i + ":");
//                for(int j=0;j<LEVELS;j++){
//                    System.out.print(up[i][j] + " ");
//                }
//                System.out.println();
//            }
        }

        public int getKthAncestor(int node, int k) {
//            System.out.println(Arrays.toString(new Object[]{Arrays.stream(Arrays.stream(up).toArray()).collect(Collectors.toList())}));
            for (int i = 0; i < LEVELS; i++) {
                int x = k & (1 << i);
                System.out.println(i + "," + node + "," + x);
                if (x > 0) {
                    if (up[node][i] == -1) return -1;
                    else node = up[node][i];
                }
            }
            return node;
        }
    }

    public static void main(String[] args) {
//        TreeAncestor obj = new TreeAncestor(7, new int[]{-1, 0, 0, 1, 1, 2, 2});
        TreeAncestor obj = new TreeAncestor(7, new int[]{-1, 0, 1, 2, 3, 4, 5});
        System.out.println("Ans:" + obj.getKthAncestor(5, 3));
    }

/**
 * Your TreeAncestor object will be instantiated and called as such:
 * TreeAncestor obj = new TreeAncestor(n, parent);
 * int param_1 = obj.getKthAncestor(node,k);
 */
}
