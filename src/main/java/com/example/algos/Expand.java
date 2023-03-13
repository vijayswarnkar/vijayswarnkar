package com.example.algos;

import javafx.util.Pair;

import java.util.*;

public class Expand {
    static class Solution {
        Set<String> set = new TreeSet<>();
        Map<Integer, Integer> match = new HashMap<>();
        int ct = 0;

        void matchBraces(String str) {
            Stack<Integer> st = new Stack<>();
            int i = 0;
            while (i < str.length()) {
                char ch = str.charAt(i);
                if (ch == '{') {
                    st.add(i);
                }
                if (ch == '}') {
                    match.put(st.peek(), i);
                    st.pop();
                }
                i++;
            }
            System.out.println(match);
        }

        void solve(String str, int i, String pre, Stack<Pair<Integer, String>> st) {
//            System.out.println(i + ":" + pre);
            if (i >= str.length()) {
                set.add(pre);
                return;
            }
            char ch = str.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                solve(str, i + 1, pre + ch, st);
            } else if (ch == '{') {
                st.push(new Pair<>(i, pre));
                ct++;
                System.out.println(i +  ": push, " + ct);
                solve(str, i + 1, pre, st);
            } else if (ch == '}') {
//                if(!st.empty())
                st.pop();
                ct--;
                System.out.println(i +  ": pop, " + ct);
                solve(str, i + 1, pre, st);
            } else if (ch == ',') {
                if (!st.empty()) {
                    Pair<Integer, String> last = st.peek();
                    int j = match.get(last.getKey()) + 1;
                    st.pop();
                    ct--;
                    System.out.println(i +  ": pop2, " + ct);
                    solve(str, j, pre, st);
                    pre = last.getValue();
                    st.push(last);
                    ct++;
                    System.out.println(i +  ": push2, " + ct);
                    solve(str, i + 1, pre, st);
                } else {
                    solve(str, i + 1, "", st);
                }
            }
        }

        public List<String> braceExpansionII(String expression) {
            matchBraces(expression);
            Stack<Pair<Integer, String>> stack = new Stack<>();
            solve(expression, 0, "", stack);
            return new ArrayList<>(set);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().braceExpansionII("{{a,z},a{b,c},{ab,z}}"));
    }
}
/*
if(ch >= 'a' && ch <= 'z') {
                solve(str, i+1, pre+ch, st);
            } else if(ch == '{') {
                st.push(new Pair<>(i, pre));
                solve(str, i+1, pre, st);
            } else if(ch == '}') {
                st.pop();
                solve(str, i+1, pre, st);
            } else if(ch == ',') {
                if(!st.empty()) {
                    Pair<Integer, String> last = st.peek();
                    int j = match.get(last.getKey())+1;
                    st.pop();
                    solve(str, j, pre, st);
                    pre = last.getValue();
                    st.push(last);
                    solve(str, i+1, pre, st);
                } else {
                    solve(str, i+1, "", st);
                }
            }
 */