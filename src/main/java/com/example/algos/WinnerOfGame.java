package com.example.algos;

import java.util.HashMap;
import java.util.Map;

class WinnerOfGame {
    public int getWinner(int[] arr, int k) {
        int ans = 0;
        Map<Integer, Integer> mp = new HashMap<>();
        Integer winner;
        for (int i = 0; i < arr.length-1; i++) {
            if(arr[i] > arr[i+1]){
                winner = arr[i];
                arr[i+1] = arr[i];
            } else {
                winner = arr[i+1];
            }
            if(mp.containsKey(winner)){
                Integer count = mp.get(winner);
                mp.put(winner, count+1);
            } else {
                mp.put(winner, 1);
            }
//            System.out.println(winner +","+mp.get(winner));
            if(mp.get(winner) >= k){
                return winner;
            }
        }
        winner = arr[arr.length-1];
        return winner;
    }
}

class MainClass {
    public static void main(String[] args) {
        System.out.println(new WinnerOfGame().getWinner(new int[]{1,11,22,33,44,55,66,77,88,99}, 1000000000));
    }
}
