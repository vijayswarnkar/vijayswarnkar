package com.example.stripe;

// Step 1)
//
// Imagine an Airbnb-like vacation rental service, where users in different cities can exchange their apartment with
// another user for a week. Each user compiles a wishlist of the apartments they like. These wishlists are ordered,
// so the top apartment on a wishlist is that user's first choice for where they would like to spend a vacation.
// You will be asked to write part of the code that will help an algorithm find pairs of users who would like to
// swap with each other.
//
// Given a set of users, each with an *ordered* wishlist of other users' apartments:
//
// a's wishlist: c d
// b's wishlist: d a c
// c's wishlist: a b
// d's wishlist: c a b
//
// The first user in each wishlist is the user's first-choice for whose apartment they would like to swap into.
// Write a function called hasMutualFirstChoice() which takes a username and returns true if that user and
// another user are each other's first choice, and otherwise returns false.
//
// hasMutualFirstChoice('a') // true (a and c)
// hasMutualFirstChoice('b') // false (b's first choice does not *mutually* consider b as their first choice)
//
// Then expand the base case beyond just "first" choices, to include all "mutually ranked choices". Write another
// function which takes a username and an option called "rank" to indicate the wishlist rank to query on. If given
// a rank of 0, you should check for a first choice pair, as before. If given 1, you should check for a pair of
// users who are each others' second-choice. Call your new function hasMutualPairForRank() and when done,
// refactor hasMutualFirstChoice() to depend on your new function.
//
// hasMutualPairForRank('a', 0) // true (a and c)
// hasMutualPairForRank('a', 1) // true (a and d are mutually each others' second-choice)


import java.util.*;
import java.io.*;

class Tests {
    public static Map<String, String[]> data() {
        Map<String, String[]> data = new HashMap<String, String[]>();
        data.put("a", new String[]{"c", "d"}); // a-c-0, a-d-1
        data.put("b", new String[]{"d", "a", "c"});
        data.put("c", new String[]{"a", "b"}); // // c-a-0, c-b-1
        data.put("d", new String[]{"c", "a", "b"}); // a c b
        return data;
    }
    public static void testChangedPairings () {
        // if d's second choice becomes their first choice, a and d
        // will no longer be a mutually ranked pair
        assertEqual(new Solution(data()).changedPairings("d", 1), new String[]{"a"});

        // if b's third choice becomes their second choice, c and b
        // will become a mutually ranked pair (mutual second-choices)
        assertEqual(new Solution(data()).changedPairings("b", 2), new String[]{"c"});

        // if b's second choice becomes their first choice, no
        // mutually-ranked pairings are affected
        assertEqual(new Solution(data()).changedPairings("b", 1), new String[]{});
    }

    public static void assertEqual(boolean actual, boolean expected) {
        if (expected == actual) {
            System.out.println("PASSED");
        } else {
            throw new AssertionError(
                    "Expected:\n  " + expected +
                            "\nActual:\n  " + actual +
                            "\n");
        }
    }

    public static void assertEqual(String[] actual, String expected[]) {
        if (!String.join(",", expected).equals(String.join(",", actual))) {
            throw new AssertionError(
                    "Expected:\n  " + String.join(",", expected) +
                            "\nActual:\n  " + String.join(",", actual) +
                            "\n");
        }
        System.out.println("PASSED");
    }

    public static void testHasMutualFirstChoice() {
        assertEqual(new Solution(data()).hasMutualFirstChoice("a"), true);
        assertEqual(new Solution(data()).hasMutualFirstChoice("b"), false);
    }

    public static void testHasMutualPairForRank() {
        assertEqual(new Solution(data()).hasMutualPairForRank("a", 0), true);
        assertEqual(new Solution(data()).hasMutualPairForRank("a", 1), true);
    }
}

class Solution {

    Map<String, String[]> data;

    public Solution(Map<String, String[]> data) {
        this.data = data;
    }

    public boolean hasMutualFirstChoice(String username) {
        return hasMutualPairForRank(username, 0);
    }

    void swap(String username, int r1, int r2){
        String[] list = data.get(username);
        String tmp = list[r1];
        list[r1] = list[r2];
        list[r2] = tmp;
        data.put(username, list);
    }

    public String[] changedPairings(String username, int changedRank){
        List<String> ans = new ArrayList<>();
        if(changedRank > 0){

            boolean at = hasMutualPairForRank(username, changedRank);
            boolean bt = hasMutualPairForRank(username, changedRank-1);

            swap(username, changedRank, changedRank-1);

            boolean ats = hasMutualPairForRank(username, changedRank);
            boolean bts = hasMutualPairForRank(username, changedRank-1);

            if(at != ats) {
                if(ats){
                    ans.add(data.get(username)[changedRank]);
                } else {
                    ans.add(data.get(username)[changedRank-1]);
                }
            }
            if(bt != bts) {
                if(bts){
                    ans.add(data.get(username)[changedRank-1]);
                } else {
                    ans.add(data.get(username)[changedRank]);
                }
            }
            swap(username, changedRank-1, changedRank);
        }
        String[] arrString = new String[ans.size()];
        for(int i=0;i<ans.size();i++){
            arrString[i] = ans.get(i);
        }
        return arrString;
    }
    public boolean hasMutualPairForRank(String username, int rank) {
        if(rank < data.getOrDefault(username, new String[]{}).length) {
            String targetUsername = data.get(username)[rank];
            String[] list = data.getOrDefault(targetUsername, new String[]{});
            if (list.length >= rank + 1) {
                return list[rank].equals(username);
            }
        }
        return false;
    }

    
}

public class Test {
    public static void main(String[] args) {
        Tests.testHasMutualFirstChoice();
        Tests.testHasMutualPairForRank();
        Tests.testChangedPairings();
    }
}
