package com.example.algos;

import java.util.ArrayList;
import java.util.List;

public class TextJustification {

    int solve(String[] arr, int s, int e, int w) {
        System.out.println(s + "," + e + ":");
        if(s == e) {
            return (w - arr[s].length()) * (w - arr[s].length());
        }
        int min = Integer.MAX_VALUE;
        for (int k = s; k < e; k++) {
            min = Math.min(solve(arr, s, k, w) + solve(arr, k+1, e, w), min);
        }
        System.out.println(s + "," + e + ":" + min);
        return min;
    }

    int minSpace(String[] arr, int w) {
        int ans = solve(arr, 0, arr.length-1, w);
        return ans;
    }

    static public String licenseKeyFormatting(String S, int K) {
        List<Character> lc = new ArrayList();
        StringBuilder sb = new StringBuilder();

        int ctr = 0;
        for(int i=S.length()-1;i >= 0;i--){
            char c= S.charAt(i);
            if(c != '-') {
                ctr++;
                c = Character.toUpperCase(c);
                sb.append(c);
                lc.add(c);
            }
            if(ctr == K) {
                ctr = 0;
                if(i > 0) sb.append('-');
                System.out.println(sb);
            }
        }

        sb = sb.reverse();
        if(sb.charAt(0) == '-') sb.deleteCharAt(0);
        return new String(sb);
    }
    public static void main(String[] args) {
        System.out.println(licenseKeyFormatting("---a-a-a-a--", 2
        ) );

    }
}
