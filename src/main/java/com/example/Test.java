package com.example;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("Test.main()");
        Set<Integer> set1 = new HashSet<Integer>(Arrays.asList(1,2,3,4,5));
        Set<Integer> set2 = new HashSet<Integer>();
        
        while(!set1.isEmpty()) {                        
            int x = set1.iterator().next();
            set2.add(x);
            System.out.println(set2);
            set1.remove(x);
        }
        System.out.println(set1);
    }
}
