package com.example.algos;

import javafx.util.Pair;

import java.util.*;

public class Alphabet {
    static class Solution {
        char[][] board = new char[10][10];
        Map<Character, Pair<Integer, Integer>> map = new HashMap<>();
        int x, y;

        void fill(int i, int j) {
            x = i;
            y = j;

            char ch = 'a';
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 5 && ch <= 'z'; col++) {
                    board[x + row][y + col] = ch;
                    map.put(ch, new Pair<>(x + row, y + col));
                    ch++;
                }
            }
//            for (char[] row : board) {
//                System.out.println(Arrays.toString(row));
//            }
//            System.out.println(map);
        }

        String getMove(int x, int y, int xd, int yd) {
//            System.out.println(x + "," + y + ":" + xd + "," + yd);
            if (x == xd && y == yd) {
                return "!";
            } else {
                int[][] moves = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
                char[] movesC = {'R', 'L', 'D', 'U'};

                for (int i = 0; i < 4; i++) {
                    int[] move = moves[i];
                    int nx = x + move[0];
                    int ny = y + move[1];
                    int ndist = Math.abs(nx - xd) + Math.abs(ny - yd);
                    int dist = Math.abs(x - xd) + Math.abs(y - yd);
                    if (board[nx][ny] >= 'a' && board[nx][ny] <= 'z' && ndist < dist) {
                        return movesC[i] + getMove(nx, ny, xd, yd);
                    }
                }
                return "X";
            }
        }

        public String alphabetBoardPath(String target) {
            fill(1, 1);
            String ans = "";
            for (int i = 0; i < target.length(); i++) {
                char ch = target.charAt(i);
                ans = ans + getMove(x, y, map.get(ch).getKey(), map.get(ch).getValue());
                x = map.get(ch).getKey();
                y = map.get(ch).getValue();
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().alphabetBoardPath("leet")); //.equals("DDR!UURRR!!DDD!"));
    }
}

class Solution11 {
    public int[] gardenNoAdj(int n, int[][] paths) {
        Queue<Pair<Integer, Integer>> Q = new LinkedList<>();
        List<List<Integer>> list = new ArrayList<>();

        int[] ans = new int[n];
        int[] vis = new int[n+1];

        for (int i = 0; i < n+1; i++) {
            vis[i] = 0;
            list.add(new ArrayList<>());
        }
        for (int[] path : paths) {
            list.get(path[0]).add(path[1]);
            list.get(path[1]).add(path[0]);
        }
//        System.out.println(list);

        for(int i=1;i<n+1;i++){
            if (vis[i] == 0) {
                Q.add(new Pair<>(i, 0));
                vis[i] = 1;
                while (!Q.isEmpty()) {
                    Pair<Integer, Integer> x = Q.poll();
                    vis[x.getKey()] = 1;
                    ans[x.getKey()-1] = x.getValue() + 1;

                    int ctr = x.getValue();
                    for (Integer child : list.get(x.getKey())) {
                        if (vis[child] == 0) {
                            System.out.println(x.getKey() + ":" + child);
                            vis[child] = 1;
                            Q.add(new Pair<>(child, (ctr + 1) % 4));
                            ctr++;
                        }
                    }
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Solution11().gardenNoAdj(5, new int[][]{
                {4, 1}, {4, 2}, {4, 3}, {2, 5}, {1, 2}, {1, 5}

        })));
    }
}
