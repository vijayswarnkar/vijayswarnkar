package com.example.algos;

import java.util.*;

import static java.lang.Integer.parseInt;

public class Transactions {

    public static void main(String[] args) throws Exception {
//        List<String> arr = new ArrayList<>();
//        Set<Integer> list = new HashSet<>();
//        list.add(1);
//        list.add(4);
//        arr = Arrays.asList("John,Doe,jd@hotmail.com,30,TR000", "Jane,Dou,jd@example.com,2,TR001,Jake,Doe,jd@email.com,10,TR002,Jane,Doa,jd@hotmail.com,31,TR003,Jake,Doe,jd@email.com,15,TR004,Jane,Doe,jd@example.com,8,TR005,John,Doe,jd@hotmail.com,13,TR006,Jake,Doe,jd@hotmail.com,26,TR007,John,Doa,jd@email.com,1,TR008,John,Doe,jd@hotmail.com,49,TR009,John,Doa,jd@hotmail.com,45,TR0010,Jane,Doe,jd@hotmail.com,7,TR0011,John,Dou,jd@example.com,42,TR0012,Jane,Dou,jd@example.com,46,TR0013,John,Dou,jd@email.com,46,TR0014,Jane,Dou,jd@example.com,5,TR0015,John,Dou,jd@email.com,30,TR0016,John,Doa,jd@hotmail.com,9,TR0017,Jake,Dou,jd@hotmail.com,44,TR0018,Jake,Doa,jd@email.com,44,TR0019,Jake,Dou,jd@email.com,13,TR0020,Jane,Doa,jd@hotmail.com,13,TR0021,Jane,Doa,jd@example.com,20,TR0022,Jane,Doa,jd@hotmail.com,20,TR0023,Jake,Dou,jd@example.com,30,TR0024,Jane,Doa,jd@example.com,13,TR0025,John,Dou,jd@example.com,42,TR0026,John,Doe,jd@hotmail.com,40,TR0027,Jane,Dou,jd@email.com,32,TR0028,Jake,Doa,jd@example.com,39,TR0029,John,Doe,jd@example.com,8,TR0030,Jane,Dou,jd@hotmail.com,42,TR0031,Jake,Dou,jd@example.com,21,TR0032,Jane,Doa,jd@email.com,29,TR0033,John,Doe,jd@example.com,13,TR0034,Jane,Doa,jd@hotmail.com,35,TR0035,Jane,Dou,jd@hotmail.com,32,TR0036,Jake,Doa,jd@example.com,40,TR0037,Jane,Doa,jd@example.com,21,TR0038,Jane,Doe,jd@hotmail.com,6,TR0039,John,Doe,jd@email.com,24,TR0040,John,Doa,jd@example.com,3,TR0041,Jake,Doe,jd@example.com,10,TR0042,John,Doa,jd@example.com,49,TR0043,Jake,Doa,jd@email.com,14,TR0044,Jake,Doa,jd@email.com,46,TR0045,Jane,Doe,jd@hotmail.com,27,TR0046,Jake,Dou,jd@hotmail.com,25,TR0047,Jane,Dou,jd@example.com,24,TR0048,Jake,Doa,jd@email.com,18,TR0049");
//        System.out.println(findRejectedTransactions(arr, 80));
//        System.out.println(isWritable(4, 2, list));
        System.out.println(ingredients("Classic,banana"));
    }

    public static List<String> findRejectedTransactions(List<String> transactions, int creditLimit) {
        Map<String, Integer> map = new HashMap<>();
        ArrayList<String> list = new ArrayList<String>();

        for (String str : transactions) {
            String[] data = str.split(",");
            String key = data[0] + data[1] + data[2];
            int val = parseInt(data[3]);

            int currentValue = val;
            if (map.containsKey(key)) {
                currentValue += map.get(key);
            }
//            System.out.println(key + currentValue + ":" + val + ":" + map.get(key) + ":" + creditLimit);
            if (currentValue > creditLimit) {
                list.add(data[4]);
            } else {
                map.put(key, currentValue);
            }
        }
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("TR0026");
        list2.add("TR0027");
        list2.add("TR0001");
        Collections.sort(list2);

        System.out.println(list2);

        Collections.sort(list2);
        return list;
    }

    public static boolean isWritable(int blockSize, int fileSize, Set<Integer> occupiedSectors) {
        int counter = 0;

        for (int i = 1; i <= blockSize; i++) {
            if (occupiedSectors.contains(i)) {
                counter = 0;
            } else {
                counter++;
            }
            if (counter == fileSize) {
                return true;
            }
        }
        return false;
    }

    private static void populateMap(Map<String, Set<String>> map, String name, String items) {
        String[] itemArray = items.split(",");
        Set<String> set = new HashSet<>();
        for (String item : itemArray) {
            set.add(item.trim());
        }
        map.put(name, set);
    }

    public static String ingredients(String order) throws IllegalArgumentException {
        try {
            System.out.println(order);
            Map<String, Set<String>> map = new HashMap<>();
            populateMap(map, "Classic", "strawberry, banana,pineapple,mango,peach,honey");
            populateMap(map, "Freezie", "blackberry,blueberry,blackcurrant,grape juice,frozen yogurt");
            populateMap(map, "Greenie", "green apple,lime,avocado,spinach,ice,apple juice");
            populateMap(map, "Just Desserts", "banana,ice cream,chocolate,peanut,cherry");

            String[] orderItems = order.split(",");
            String name = orderItems[0];
            if (!map.containsKey(name)) {
                throw new IllegalArgumentException();
            }
            Set<String> set = new TreeSet<>(map.get(name));

            for (int i = 1; i < orderItems.length; i++) {
                String[] orderItem = orderItems[i].split("-");
                if (orderItem.length == 1) {
//                    if (!map.get(name).contains(orderItem[0])) {
                        throw new IllegalArgumentException();
//                    }
                }
                if (orderItem.length == 2) {
                    set.remove(orderItem[1]);
                }
            }
            return String.join(",", set);
        } catch (Exception ex){
            throw new IllegalArgumentException();
        }
    }
}
