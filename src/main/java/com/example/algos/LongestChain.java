package com.example.algos;

import java.util.HashMap;
import java.util.Map;

public class LongestChain {
    public int LongestStrChain(String[] words) {
        Map<String, Integer> mp = new HashMap<>();
        for (String word : words) {
            mp.put(word, 1);
        }

        return 0;
    }
}

class longestChainTest {
    public static void main(String[] args) {
        String[] words = {"a", "b", "ba", "bca", "bda", "bdca"};
        System.out.println(new LongestChain().LongestStrChain(words));
    }
}
