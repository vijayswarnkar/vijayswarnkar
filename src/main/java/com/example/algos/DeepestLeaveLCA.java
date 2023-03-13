package com.example.algos;

import javafx.util.Pair;

public class DeepestLeaveLCA {
    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    static class Solution {

        Pair<TreeNode, Integer> solve(TreeNode root) {
            if (root == null) {
                return new Pair<>(null, 0);
            }
            Pair<TreeNode, Integer> left = solve(root.left);
            Pair<TreeNode, Integer> right = solve(root.right);

            if (left.getValue().equals(right.getValue())) {
                return new Pair<>(root, left.getValue() + 1);
            } else if (left.getValue() > right.getValue()) {
                return new Pair<>(root.left == null ? root : left.getKey(), left.getValue() + 1);
            } else {
                return new Pair<>(root.right == null ? root : right.getKey(), right.getValue() + 1);
            }
        }

        public TreeNode lcaDeepestLeaves(TreeNode root) {
            return solve(root).getKey();
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode();
        System.out.println(new Solution().lcaDeepestLeaves(root));
    }
}
