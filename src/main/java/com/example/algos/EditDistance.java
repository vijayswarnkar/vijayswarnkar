package com.example.algos;

// A Dynamic Programming based Java program to find minimum
// number operations to convert str1 to str2
public class EditDistance {
    static int min(int x, int y, int z) {
        if (x <= y && x <= z)
            return x;
        if (y <= x && y <= z)
            return y;
        else
            return z;
    }

    static int editDistDP(String str1, String str2, int m, int n) {
        // Create a table to store results of subproblems
        int dp[][] = new int[m + 1][n + 1];

        // Fill d[][] in bottom up manner
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                // If first string is empty, only option is
                // to insert all characters of second string
                if (i == 0)
                    dp[i][j] = j; // Min. operations = j

                    // If second string is empty, only option is
                    // to remove all characters of second string
                else if (j == 0)
                    dp[i][j] = i; // Min. operations = i

                    // If last characters are same, ignore last
                    // char and recur for remaining string
                else if (str1.charAt(i - 1)
                        == str2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];

                    // If the last character is different,
                    // consider all possibilities and find the
                    // minimum
                else
                    dp[i][j] = 1 + min(
                            dp[i][j - 1], // Insert
                            dp[i - 1][j], // Remove
                            dp[i - 1][j - 1]); // Replace
            }
//            for (int x : dp[i]) System.out.print(x + " ");
//            System.out.println();
        }

        return dp[m][n];
    }

    // Driver Code

    static int editRecursion(String str1, String str2, int m, int n) {
        if(m == 0) {
            return n;
        }
        if(n == 0) {
            return m;
        }
        if (str1.charAt(m-1) == str2.charAt(n-1)) {
            return editRecursion(str1, str2, m - 1, n - 1);
        } else {
            return 1 + min(
                    editRecursion(str1, str2, m - 1, n),
                    editRecursion(str1, str2, m, n - 1),
                    editRecursion(str1, str2, m - 1, n - 1)
            );
        }
    }

    public static void main(String args[]) {
        String str1 = "catttvbe";
        String str2 = "cccttbb";
        System.out.println(editDistDP(str1, str2, str1.length(), str2.length()));
        System.out.println(editRecursion(str1, str2, str1.length(), str2.length()));
    }
} /*This code is contributed by Rajat Mishra*/