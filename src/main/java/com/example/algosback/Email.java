import javafx.util.Pair;

import java.util.*;

class Solution21 {

    String tokenize(String email) {
        String[] arr = email.split("@");
        StringBuilder sb = new StringBuilder();
        System.out.println(Arrays.toString(arr));
        for (int i = 0; i < arr[0].length(); i++) {
            if (arr[0].charAt(i) == '+') break;
            if (arr[0].charAt(i) == '.') continue;
            sb.append(arr[0].charAt(i));
        }
        return sb.toString() + "@" + arr[1];
    }

    public int numUniqueEmails(String[] emails) {
        Set<String> st = new HashSet();
        for (String email : emails) {
            st.add(tokenize(email));
        }
        return st.size();
    }
}

public class Email {
}

class Reachable {
    public int networkDelayTime(int[][] times, int n, int k) {
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>((a, b) -> a.getKey() - b.getKey());

        Map<Integer, List<Pair<Integer, Integer>>> mp = new HashMap<>();
        for (int[] time : times) {
            List<Pair<Integer, Integer>> x = mp.getOrDefault(time[0], new ArrayList<>());
            x.add(new Pair<>(time[1], time[2]));
            mp.put(time[0], x);
        }
        System.out.println(mp);
        Set<Integer> st = new HashSet<>();
        st.add(n);

        System.out.println(k);
        for (Pair<Integer, Integer> p : mp.get(k)) {
            System.out.println("p, " + p);
            pq.offer(new Pair<>(p.getValue(), p.getKey()));
        }
        while (!pq.isEmpty()) {
            System.out.println(pq.remove());
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(new Reachable().networkDelayTime(new int[][]{{1, 1, 1}}, 1, 1));
    }
}

class NetworkDelayTime {
    public int networkDelayTime(int[][] times, int n, int k) {
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>((a, b) -> a.getKey() - b.getKey());
        Map<Integer, List<Pair<Integer, Integer>>> mp = new HashMap<>();
        for (int[] time : times) {
            List<Pair<Integer, Integer>> x = mp.getOrDefault(time[0], new ArrayList<>());
            x.add(new Pair<>(time[1], time[2]));
            mp.put(time[0], x);
        }
        Set<Integer> st = new HashSet<>();

        for (Pair<Integer, Integer> p : mp.getOrDefault(k, new ArrayList<>())) {
            pq.add(new Pair<>(p.getValue(), p.getKey()));
        }
        if (pq.isEmpty()) return -1;
        int ans = 0;
        st.add(k);

        Set<Integer> vis = new HashSet<>();
        vis.add(k);
        while (!pq.isEmpty()) {
            Pair<Integer, Integer> top = pq.remove();
            if (vis.contains(top.getValue())) {
                continue;
            }
            vis.add(top.getValue());
            st.add(top.getValue());
            System.out.println(top + "," + st + ", " + ans);
            ans = Math.max(ans, top.getKey());
            if (st.size() == n) break;
            for (Pair<Integer, Integer> p : mp.getOrDefault(top.getValue(), new ArrayList<>())) {
                pq.offer(new Pair<>(p.getValue() + top.getKey(), p.getKey()));
            }
        }

        return st.size() == n ? ans : -1;
    }

    public static void main(String[] args) {
        System.out.println(new NetworkDelayTime().networkDelayTime(new int[][]{{2, 1, 1}, {2, 3, 1}, {3, 4, 1}}, 4, 2));
    }
}