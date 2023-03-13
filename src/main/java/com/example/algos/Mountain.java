package com.example.algos;

public class Mountain {
    static class MountainArray {
        //        int[] arr = {1, 2, 3, 9, 5, 4, 3, 1};
        int[] arr = {3,5,3,2,0};
        int length = arr.length;

        int get(int index) {
            return arr[index];
        }
        int length(){
            return length;
        }
    }

    static class Solution {
        int MAX = 999999;

        int findPivot(MountainArray arr) {
            int s = 0, e = arr.length()-1;

            while (s <= e) {
                int m = (s + e) / 2;
                if(s+1 == e){
                    if(arr.get(s) > arr.get(e)) return s;
                    else return e;
                }
                System.out.println(s+","+e+":"+m);
                if (arr.get(m - 1) > arr.get(m)) {
                    e = m - 1;
                } else if (arr.get(m + 1) > arr.get(m)) {
                    s = m + 1;
                } else {
                    return m;
                }
            }
            return -1;
        }

        int binarySearchAsc(MountainArray arr, int s, int e, int t) {
            if(s+1 == e){
                if(arr.get(s) == t) return s;
                if(arr.get(e) == t) return e;
                return MAX;
            }
            while (s <= e) {
                int m = (s + e) / 2;
                if (arr.get(m) == t) {
                    return m;
                } else if (arr.get(m) > t) {
                    e = m - 1;
                } else {
                    s = m + 1;
                }
            }
            return MAX;
        }

        int binarySearchDsc(MountainArray arr, int s, int e, int t) {
            if(s+1 == e){
                if(arr.get(s) == t) return s;
                if(arr.get(e) == t) return e;
                return MAX;
            }
            while (s <= e) {
                int m = (s + e) / 2;
                if (arr.get(m) == t) {
                    return m;
                } else if (arr.get(m) < t) {
                    e = m - 1;
                } else {
                    s = m + 1;
                }
            }
            return MAX;
        }

        int findInMountainArray(int target, MountainArray arr) {
            int pivot = findPivot(arr);
            if (pivot == -1) return -1;

            System.out.println(pivot);
            int l = binarySearchAsc(arr, 0, pivot, target);
            System.out.println(l);
            int r = binarySearchDsc(arr, pivot+1, arr.length()-1, target);
            System.out.println(r);
            int ans = Math.min(l, r);
            return ans == MAX ? -1 : ans;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().findInMountainArray(0, new MountainArray()));
    }

}
