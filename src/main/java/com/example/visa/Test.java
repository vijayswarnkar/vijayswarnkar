package com.example.visa;

import java.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

// Main class should be named 'Solution' and should not be public.
// sqrt(4) = 2
// sqrt(3) = 1.732xx
// x -> y->x/2 -> x/4 -> x/8
// 

public class Test {
    static double squareRoot(int x){   
        return squareRoot(x, 0.0, x);     
    }

    static double squareRoot(int x, double y, double z){        
        double m = (y+z)/2;      
        System.out.println(m);  
        if(m*m == x){
            return (double) y;
        } else if(m*m <= x) {
            if(x - m*m <= 0.0001){ // log2(n)*10^4
                return m;
            }
            return squareRoot(x, m, z);
        } else {
            return squareRoot(x, y, m);
        }        
    }
    static boolean match(String input, String regex){
        int n = input.length();
        int m = regex.length();
        boolean[][] dp = new boolean[m+1][n+1];
        for(int i=0;i<m+1;i++){
            for(int j=0;j<n+1;j++){
                if(i == 0 || j == 0) {
                    dp[i][j] = true;
                    continue;   
                }
                if(j < i) {
                    dp[i][j] = true;
                     continue;   
                }    
                dp[i][j] = dp[i-1][j-1];
                char ch = regex.charAt(i-1);
                char curr = input.charAt(j-1);
                // if(i == 4 && j == 5){
                //     System.out.println(input.charAt(i-1));
                //     System.out.println(regex.charAt(j-1));
                //     System.out.println(curr + "," + ch);
                // }
                switch(ch){
                    case '.':
                        // dp[i][j] &= dp[i][j-1];
                    break;

                    case '*':
                        char prev = input.charAt(j-2);                                                            
                        if(prev == '.'){
                        } else {
                             if(dp[i][j-1] == true){
                                dp[i][j] = true;    
                             } else {
                                 dp[i][j] &= (prev == curr);                             
                             }
                        }
                    
                    break;

                    default:
                        dp[i][j] &= (curr == ch);
                    break;
                }
            }            
        }
        for(int i=0;i<m+1;i++){
            for(int j=0;j<n+1;j++){
                System.out.print(dp[i][j]== true ? 1 : 0);
            }
            System.out.println();
        }
        return dp[m][n];
    }
    public static void main(String[] args) {
        System.out.println("Hello, World");
        System.out.println(squareRoot(9));
        System.out.println(match("ababcab", "a.*ab"));
    }
}
// a* -> aaaaaa -> abbbbb
// asdlkfjasldf , a.*j.*f
// * - or more
// . one character
// ababab - a.* - true - .*)
// hello - hm* - false a* .*
// func(input, reg, i, j) -> t/f
// a -> i++, j++
// . -> i++, j++
// * -> (i, j++),(i++, j) 
/*
  a b a b a b c
a t f f f f f f
. - t f f f f f  
* - - t t t t t
c - - - f f f t -- yes
d - - - f f f f -- no


*/