import javafx.util.*;
import java.util.*;

// IN [[1,10],[15,20],[11,11], [17, 18], [20,1000]]
// Out = [[1,11], [15,1000]]

// (1, 10), (11, 11), (15, 20), (17, 18), (20, 1000)
// 1 - 11
// 15 - 1000
class Solution111 {
    List<Pair<Integer, Integer>> merge(int[][] vals) {
        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
        for (int[] pair : vals) {
            pairs.add(new Pair(pair[0], pair[1]));
        }
        pairs.sort((a, b) -> a.getKey() - b.getKey());
        int st = pairs.get(0).getKey();
        int ed = pairs.get(0).getValue();
        int i = 1;

        List<Pair<Integer, Integer>> ans = new ArrayList<>();
        while (i < pairs.size()) {
            int newSt = pairs.get(i).getKey();
            if (newSt >= st && newSt <= ed + 1) {
                ed = Math.max(ed, pairs.get(i).getValue());
            } else {
                ans.add(new Pair(st, ed));
                st = newSt;
                ed = pairs.get(i).getValue();
            }
            i++;
        }
        ans.add(new Pair(st, ed));
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(new Solution111().merge(new int[][]{{1, 10}, {20, 21}, {22, 30}}));
    }
}

class Latest {
    Set<String> list = new HashSet<>();

    void perms(char[] arr, String val, int ct) {
        if (ct == 4) {
            list.add(val);
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            char ch = arr[i];
            if(arr[i] != 'x') {
                arr[i] = 'x';
                if (ct == 2) {
                    perms(arr, val + ":" + ch, ct + 1);
                } else {
                    perms(arr, val + ch, ct + 1);
                }
                arr[i] = ch;
            }
        }
    }

    int StoI(String s) {
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            val = val * 10 + (s.charAt(i) - '0');
        }
        return val;
    }

    List<String> filterInvalid(List<String> times) {
        List<String> x = new ArrayList<>();
        for (String time : times) {
            String[] arr = time.split(":");
            int a = StoI(arr[0]);
            int b = StoI(arr[1]);
            if (a >= 0 && a <= 23 && b >= 0 && b <= 59) {
                x.add(time);
            }
        }
        return x;
    }

    public String largestTimeFromDigits(int[] time) {
        char[] arr = new char[4];
        for(int i=0;i<4;i++){
            arr[i] = (char) ('0' + time[i]);
        }
//        System.out.println(Arrays.toString(arr));
        perms(arr, "", 0);
        List<String> l = new ArrayList<>(list);
        l.sort((a, b) -> a.compareTo(b));
//        System.out.println(l);
        l = filterInvalid(l);
//        System.out.println(l);

        return l.size() > 0 ? l.get(l.size()-1): "";
    }

    public static void main(String[] args) {
        System.out.println(new Latest().largestTimeFromDigits(new int[]{1, 2, 3, 4}));
        System.out.println(new Latest().largestTimeFromDigits(new int[]{5, 5, 5, 5}));
    }
}

class NextCLosestTime {
    Set<String> list = new HashSet<>();

    void perms(char[] arr, String val, int ct) {
        if (ct == 4) {
            list.add(val);
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            char ch = arr[i];
            if (ct == 2) {
                perms(arr, val + ":" + ch, ct + 1);
            } else {
                perms(arr, val + ch, ct + 1);
            }
        }
    }

    int StoI(String s) {
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            val = val * 10 + (s.charAt(i) - '0');
        }
        return val;
    }

    List<String> filterInvalid(List<String> times) {
        List<String> x = new ArrayList<>();
        for (String time : times) {
            String[] arr = time.split(":");
            int a = StoI(arr[0]);
            int b = StoI(arr[1]);
            if (a >= 0 && a <= 23 && b >= 0 && b <= 59) {
                x.add(time);
            }
        }
        return x;
    }

    public String nextClosestTime(String time) {
        char[] arr = new char[]{time.charAt(0), time.charAt(1), time.charAt(3), time.charAt(4)};
        perms(arr, "", 0);
        List<String> l = new ArrayList<>(list);
        l.sort((a, b) -> a.compareTo(b));
        l = filterInvalid(l);
        int idx = -1;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).equals(time)) {
                idx = i;
                break;
            }
        }
        return l.get((idx + 1) % (l.size()));
    }

    public static void main(String[] args) {
        System.out.println(new NextCLosestTime().nextClosestTime("19:34"));
        System.out.println(new NextCLosestTime().nextClosestTime("23:59"));
    }
}

class Typed {
    public boolean isLongPressedName(String name, String typed) {
        int i = 0;
        int j = 0;

        while(i < name.length() && j < typed.length()){
            char a = name.charAt(i);
            char b = typed.charAt(i);
            int c1 = 1;
            int c2 = 1;

            if(a != b){
                return false;
            }
            while(name.charAt(i) == a && i < name.length()){
                c1++;
                i++;
            }
            while(typed.charAt(j) == b && j < typed.length()){
                c2++;
                j++;
            }
            if(c2 < c1) {
                return false;
            }
        }
        if(i < name.length() || j < typed.length()){
            return false;
        }
        return true;
    }

    static int test(){
        int i = 1;
        while (i == 1){
            return 5;
        }
        return 6;
    }
    public static void main(String[] args) {
        System.out.println("finished" + test());
    }
}