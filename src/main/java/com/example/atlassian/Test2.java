package com.example.atlassian;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Winner {
    String Winner(List<String> list) {
        if (list == null) {
            return null;
        }
        Map<String, Integer> map = new HashMap<>();
        int mx = 0;
        String winnerName = null;

        for (String name : list) {
            name = name.toLowerCase();
            if (!map.containsKey(name)) {
                map.put(name, 0);
            }
            int ctr = map.get(name) + 1;
            map.put(name, ctr);
            if (ctr >= mx) {
                mx = ctr;
                winnerName = name;
            }
        }
        return winnerName;
    }

    // map<String, Integer>
    String Winner2(List<List<String>> ballets) {
        if (ballets == null) {
            return null;
        }
        Map<String, Integer> map = new HashMap<>();
        int mx = 0;
        String winnerName = null;

        for (List<String> list : ballets) {
            int l = list.size();
            for (int i = 0; i < list.size(); i++) {
                String name = list.get(i);
                name = name.toLowerCase();
                if (!map.containsKey(name)) {
                    map.put(name, 0);
                }
                int ctr = map.get(name) + (l-i);
                map.put(name, ctr);
                if (ctr >= mx) {
                    mx = ctr;
                    winnerName = name;
                }

            }
        }
        System.out.println(ballets);
        System.out.println(map);
        return winnerName;
    }
}
