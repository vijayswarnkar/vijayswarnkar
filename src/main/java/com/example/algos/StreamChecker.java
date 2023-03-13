package com.example.algos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StreamChecker {
    static class StreamChecker_ {
        Trie root;
        List<Character> list;

        static class Trie {
            public boolean word;
            public Map<Character, Trie> map;

            public Trie() {
                word = false;
                map = new HashMap<>();
            }
        }

        public void printTrie(){
            System.out.println("printing trie...");
            printTrie(root, "");
        }

        public void printTrie(Trie root, String st){
            if(root.word) {
                System.out.println(st);
            }
            root.map.keySet().forEach(ch-> {
                printTrie(root.map.get(ch), st+ch);
            });
        }

        // stores word in reverse order
        public void add(Trie root, String word, int i) {
            char ch = word.charAt(i);
            Trie node = root.map.getOrDefault(ch, new Trie());
            if (i == word.length() - 1) {
                node.word = true;
                root.map.put(ch, node);
            } else {
                root.map.put(ch, node);
                add(node, word, i + 1);
            }
        }

        public StreamChecker_(String[] words) {
            root = new Trie();
            list = new ArrayList<>();
            for (String word : words) {
                word = new StringBuilder(word).reverse().toString();
                System.out.println("Adding..." + word);
                add(root, word, 0);
                printTrie();
            }
        }

        public boolean search(Trie root, int i, int k) {
            if(root.word) return  true;
            if(i < 0 || k < 0) return false;
            char ch = list.get(i);
            if(root.map.containsKey(ch)){
                return search(root.map.get(ch), i-1, k-1);
            } else {
                return false;
            }
        }

        public boolean query(char letter) {
            list.add(letter);
            return search(root, list.size()-1, 2000);
        }
    }

    public static void main(String[] args) {
        StreamChecker_ streamChecker = new StreamChecker_(new String[]{"ab","ba","aaab","abab","baa"}); // init the dictionary.
        streamChecker.printTrie();
        System.out.println(streamChecker.query('l'));
    }

}
