package com.example.algos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class AnserQuery {
    TreeSet<Integer> set;

    void set(int idx){
        set.add(idx);
    }

    int get(int idx){
        if(set.contains(idx)){
            return idx;
        } else if(set.higher(idx) != null){
            return set.higher(idx);
        } else {
            return -1;
        }
    }

    List<Integer> query(List<List<Integer>> queries){
        System.out.println(queries);
        set = new TreeSet<>();
        List<Integer> ans = new ArrayList<>();
        for(List<Integer> query:queries){
            System.out.println(query);
            int type = query.get(0);
            int idx = query.get(1);
            if(type == 1){
                set(idx);
            } else {
                ans.add(get(idx));
            }
        }
        return ans;
    }

}

class testAnswer{
    public static void main(String[] args) {
        AnserQuery obj = new AnserQuery();
        List<List<Integer>> input = new ArrayList<>();
        input.add(Arrays.asList(2, 3));
        input.add(Arrays.asList(1, 2));
        input.add(Arrays.asList(2, 1));
        input.add(Arrays.asList(2, 3));
        input.add(Arrays.asList(2, 2));
        System.out.println(obj.query(input));
    }
}
