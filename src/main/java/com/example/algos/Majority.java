package com.example.algos;

import javafx.util.Pair;

import java.util.*;

public class Majority {
    static class MajorityChecker {

        Map<Integer, List<Integer>> mp = new HashMap<>();
        List<Pair<Integer, List<Integer>>> sortedList = new ArrayList<>();

        public MajorityChecker(int[] arr) {
            for (int i = 0; i < arr.length; i++) {
                List<Integer> list = mp.getOrDefault(arr[i], new ArrayList<>());
                list.add(i);
                mp.put(arr[i], list);
            }
            for (int key : mp.keySet()) {
                sortedList.add(new Pair(key, mp.get(key)));
            }
            sortedList.sort((b, a) -> a.getValue().size() - b.getValue().size());
//            System.out.println(mp);
//            System.out.println(sortedList);
        }

        public int query(int left, int right, int threshold) {
            int ans = -1;
//            for (int key : mp.keySet()) {
//            List<Integer> list = mp.get(key);
            for (Pair<Integer, List<Integer>> x : sortedList) {
                int key = x.getKey();
                List<Integer> list = x.getValue();
                if (list.size() >= threshold) {
                    int l = Collections.binarySearch(list, left);
                    int h = Collections.binarySearch(list, right);

                    if (l < 0) l = -l - 1;
                    if (h < 0) h = -h - 2;

                    if (h - l + 1 >= threshold) {
                        ans = key;
                        break;
                    }
                }
            }

//            System.out.println("ans:" + ans);
            return ans;
        }
    }

    public static void main(String[] args) {
        MajorityChecker majorityChecker = new MajorityChecker(new int[]{1, 1, 2, 2, 1, 1});
        majorityChecker.query(0, 5, 4); // returns 1
        majorityChecker.query(0, 3, 3); // returns -1
        majorityChecker.query(2, 3, 2); // returns 2
    }
}