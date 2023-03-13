import java.util.*;

public class Garden {

    void solve(Map<Integer, List<Integer>> mp, int node, int[] color, Map<Integer, Integer> vis) {
        int[] col = new int[5];
        if(mp.containsKey(node)) {
            for (int x : mp.get(node)) {
                if (vis.containsKey(x)) {
                    int c = vis.get(x);
                    col[c] = 1;
                }
            }
        }

        for(int i=1;i<=5;i++){
            if(col[i] == 0){
                vis.put(node, i);
                break;
            }
        }

        if(mp.containsKey(node)) {
            for (int x : mp.get(node)) {
                if (!vis.containsKey(x)) {
                    solve(mp, x, color, vis);
                }
            }
        }
    }

    public int[] gardenNoAdj(int n, int[][] paths) {
        Map<Integer, List<Integer>> mp = new HashMap<>();
        Map<Integer, Integer> vis = new HashMap<>();
        for (int[] path : paths) {
            List<Integer> x = mp.getOrDefault(path[0], new ArrayList<>());
            x.add(path[1]);
            mp.put(path[0], x);

            List<Integer> y = mp.getOrDefault(path[1], new ArrayList<>());
            y.add(path[0]);
            mp.put(path[1], y);
        }
//        System.out.println(mp);
        for (int i = 1; i <= n; i++) {
            if (!vis.containsKey(i)) {
                solve(mp, i, new int[]{1, 2, 3, 4}, vis);
            }
        }
//        System.out.println(vis);
        int[] ans = new int[n];
        for(Integer key : vis.keySet()){
            ans[key-1] = vis.get(key);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test");
        System.out.println(Arrays.toString(
                new Garden().gardenNoAdj(4,
                        new int[][]{{1, 2}, {3, 4}})));
    }
}
