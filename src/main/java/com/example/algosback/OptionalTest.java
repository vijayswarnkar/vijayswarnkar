package com.example.algosback;

import java.util.Optional;

public class OptionalTest {
    public static void main(String[] args) {
        Optional<Integer> optionalInteger = Optional.ofNullable(null);
        System.out.println(optionalInteger);
        System.out.println(optionalInteger.isEmpty());
        System.out.println(optionalInteger.get());
    }
}

class PrintSpiral {
    void print(int r, int c, int[][] mat, int sr, int sc) {
        if (sr <= 0 || sc <= 0) return;
        System.out.println(r + ":" + c);
        for (int i = c; i < c + sc; i++) {
            System.out.print(mat[r][i] + ", ");
        }
        for (int i = r + 1; i < r + sr; i++) {
            System.out.print(mat[i][c + sc - 1] + ", ");
        }
        if (sr > 1) {
            for (int i = c + sc - 2; i >= r; i--) {
                System.out.print(mat[r + sr - 1][i] + ", ");
            }
        }
        if (sc > 1) {
            for (int i = r + sr - 2; i > c; i--) {
                System.out.print(mat[i][c] + ", ");
            }
        }
        System.out.println();
        print(r + 1, c + 1, mat, sr - 2, sc - 2);
    }

    public static void main(String[] args) {
        int[][] mat = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {9, 10, 11, 12},
                {9, 10, 11, 12},
        };
        mat = new int[][]{
                {1, 2, 3},
                {5, 6, 7},
                {9, 10, 11},
                {9, 10, 11},
                {9, 10, 11},
        };
//        mat = new int[][]{
//                {1},
//        };
        new PrintSpiral().print(0, 0, mat, mat.length, mat[0].length);
    }
}

class TestBinary {
    int binary_search(int[] arr, int s, int e, int val){
        if(s > e) return -1;
        int m = (s+e)/2;
        if(arr[m] == val){
            return m;
        } else if(val < arr[m]){
            return binary_search(arr, s, m-1, val);
        } else {
            return binary_search(arr, m+1, e, val);
        }
    }
    int binary_search(int[] arr, int val){
        return binary_search(arr, 0, arr.length-1, val)+1;
    }
    public static void main(String[] args) {
        System.out.println(new TestBinary().binary_search(new int[]{1,2,3,4,5}, 5));
        System.out.println("Vijay");
    }
}