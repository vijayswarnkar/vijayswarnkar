package com.example.algos;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Puzzle {
    static class Solution {
        Set<String> set = new HashSet<>();
        int ans = Integer.MAX_VALUE;

        String getKey(int[][] board) {
            String key = "";
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    key += board[i][j];
                }
            }
            return key;
        }

        boolean isSolved(int[][] board) {
            return getKey(board).equals("123450");
        }

        class Obj {
            public int[][] board;
            public int ctr;
            public int x;
            public int y;
            public Obj(int[][] board, int ctr, int x, int y){
                this.board = board;
                this.ctr = ctr;
                this.x = x;
                this.y = y;
            }
        }
        int solve(int[][] board, int x, int y) {
            Queue<Obj> Q = new LinkedList<>();
            Q.add(new Obj(board, 0, x, y));
            set.add(getKey(board));
            int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            while (!Q.isEmpty()) {
                Obj front = Q.poll();
                if(isSolved(front.board)){
                    return front.ctr;
                }
                x = front.x;
                y = front.y;
                board = front.board;
                int ctr = front.ctr;
                for (int[] m : dir) {
                    int nx = x + m[0];
                    int ny = y + m[1];
                    if (nx >= 0 && nx < 2 && ny >= 0 && ny < 3) {
                        int[][] b = new int[2][3];
                        for(int i=0;i< board.length;i++){
                            for(int j=0;j< board[i].length;j++){
                                b[i][j] = board[i][j];
                            }
                        }
                        int tmp = b[nx][ny];
                        b[nx][ny] = b[x][y];
                        b[x][y] = tmp;
                        if (!set.contains(getKey(b))) {
                            set.add(getKey(b));
                            Q.add(new Obj(b, ctr+1, nx, ny));
                        }
                    }
                }
            }
            return -1;
        }

        public int slidingPuzzle(int[][] board) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        return solve(board, i, j);
                    }
                }
            }
            return ans;
        }

        public static void main(String[] args) {
            System.out.println(new Solution().slidingPuzzle(new int[][]{
                    {1, 2, 3},
                    {4, 0, 5}
            }));
            System.out.println(new Solution().slidingPuzzle(new int[][]{
                    {3,2,4},
                    {1,5,0}
            }));
            System.out.println(new Solution().slidingPuzzle(new int[][]{
                    {1,2,3},
                    {5,4,0}
            }));
        }
    }
}
