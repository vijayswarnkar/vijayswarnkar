package com.example.algos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordBreak {
    static class Solution {
        Map<String, Boolean> map = new HashMap<>();
        Map<String, Boolean> dp = new HashMap<>();

        boolean solve(int id, String s, String st){
//            System.out.println(id + "," + st + ":");
            if(dp.containsKey(st+id)){
                return dp.get(st+id);
            }
            if(id == s.length()){
                if(st.equals("") || map.containsKey(st))
                    return true;
                else
                    return false;
            }
            boolean ans = false;
            st = st + s.charAt(id);
            if(map.containsKey(st)){
                System.out.println(id + "," + st + ":");
                ans = solve(id+1, s, "") || solve(id+1, s, st);
            } else {
                ans = solve(id+1, s, st);
            }
            dp.put(st+id, ans);
//            System.out.println(id + "," + st + ":" + ans);
            return ans;
        }
        public boolean wordBreak(String s, List<String> wordDict) {
            for(String str: wordDict){
                map.put(str, true);
            }
            System.out.println(map);
            return solve(0, s, "");
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().wordBreak("leetcode", Arrays.asList("leet", "code")));
    }
}
