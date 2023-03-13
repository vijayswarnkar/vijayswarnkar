package algosback;

import javafx.util.Pair;

import java.util.*;

public class Binary_search {
    public static void main(String[] args) {

    }
}

class Solution {
    List<Integer> arr = new ArrayList<>();
    Map<Integer, Integer> ct = new HashMap<>();
    int[] fact = new int[10];
    int total = 0;

    int calcFact(int[] count) {
        int den = 1;
        int sum = 0;
        for (int x : count) {
            sum += x;
            den *= fact[x];
        }
        return fact[sum] / den;
    }

    int arrToInteger(int[] arr) {
        int val = 0;
        int mul = 1;
        for (int i = 0; i < arr.length; i++) {
            val += arr[arr.length - 1 - i] * mul;
            mul *= 10;
        }
        return val;
    }

    void solve(int[] arrCopy, int len) {
        if (len == 0) {
            int hash = arrToInteger(arrCopy);
            if (!ct.containsKey(hash)) {
//                System.out.print(Arrays.toString(arrCopy) + "," + hash);
                int ans = calcFact(arrCopy);
                total += ans;
                ct.put(hash, ans);
//                System.out.println("," + ans);
            }
            return;
        }
        for (int i = 0; i < arrCopy.length; i++) {
            if (arrCopy[i] < arr.get(i)) {
                arrCopy[i] += 1;
                solve(arrCopy, len - 1);
                arrCopy[i] -= 1;
            }
        }
    }

    public int numTilePossibilities(String tiles) {
        fact[0] = 1;
        for (int i = 1; i < fact.length; i++) {
            fact[i] = i * fact[i - 1];
        }
//        System.out.println(Arrays.toString(fact));
        Map<Character, Integer> mp = new HashMap<>();
        for (int i = 0; i < tiles.length(); i++) {
            Character ch = tiles.charAt(i);
            mp.put(ch, mp.getOrDefault(ch, 0) + 1);
        }
        for (Character ch : mp.keySet()) {
            arr.add(mp.get(ch));
        }
        int[] arrCopy = new int[arr.size()];
        arr.sort((a, b) -> a - b);

//        System.out.println(arr);
//        System.out.println(Arrays.toString(arrCopy));
        for (int i = 1; i < tiles.length() + 1; i++) {
//            System.out.println("i == " + i);
            solve(arrCopy, i);
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().numTilePossibilities("AAABBC"));
    }
}

class Solution3 {

    static class RollingHash {
        Long[] rollingHash;
        Long[] mul;
        int base;
        int MOD = 1000000009;
        String s;

        RollingHash(String s, int base) {
            this.base = base;
            this.s = s;
            rollingHash = new Long[s.length()];
            mul = new Long[s.length()];
            mul[0] = 1L;
            for (int i = 1; i < s.length(); i++) {
                mul[i] = mul[i - 1] * base % MOD;
            }
            rollingHash[0] = (long) (s.charAt(0));
            for (int i = 1; i < s.length(); i++) {
                rollingHash[i] = (rollingHash[i - 1] * base % MOD + s.charAt(i)) % MOD;
            }
//            System.out.println(Arrays.toString(mul));
//            System.out.println(Arrays.toString(rollingHash));
        }

        Long hash(int l, int r) {
            int len = r - l + 1;
            Long h = rollingHash[r];
//            System.out.println(s.substring(l, r+1) + ", " + len + ":" + h);
            if (l > 0) {
                h = (h - (rollingHash[l - 1] * mul[len]) % MOD + MOD) % MOD;
            }
            return h;
        }
    }

    public int countDistinct(String s) {
        RollingHash rollingHash = new RollingHash(s, 10);
        RollingHash rollingHash2 = new RollingHash(s, 15);
        Set<Pair<Long, Long>> st = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j <= i; j++) {
                st.add(new Pair<>(rollingHash.hash(j, i), rollingHash2.hash(j, i)));
            }
        }
        return st.size();
    }

    public static void main(String[] args) {
        System.out.println(new Solution3().countDistinct("txpzyrqryyedyipkcvwqgirsspnxywukhazbotqwbdmnqyrjbraumiakrawgceozirkarnknzhelszdzoxnpzgmcsrhgwbmladsivcikbpxzizyvakjggxjmnqthurcpowhkjsvehghctnyvekhbprtsjcabjioetyfcxojuoipra"));
//        System.out.println(new algosback.Solution3().countDistinct("onhhxcxrnyccznkycnaimmzyoqqputosrqlydwxmmbidghlqeqirmqfhbqhcehjivzebpnzk"));
//        System.out.println(new algosback.Solution3().countDistinct("meuyxurlmxydjuevbctlduraogmdgokaadngbdpbp"));
//        System.out.println(new algosback.Solution3().countDistinct("aabbaba"));
//        System.out.println(new algosback.Solution3().countDistinct("abcdefg"));
    }
}

