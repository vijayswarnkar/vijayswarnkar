package com.example.algos;

import java.util.Map;
import java.util.TreeMap;

public class MyCalendarII {
    static class MyCalendarTwo {

        Map<Integer, Integer> bookings;

        public MyCalendarTwo() {
            bookings = new TreeMap<>();
        }

        public boolean book(int start, int end) {
            Integer overlap = 0, max = 0;
            bookings.put(start, bookings.getOrDefault(start, 0) + 1);
            bookings.put(end, bookings.getOrDefault(end, 0) - 1);

            for (Integer key : bookings.keySet()) {
                if (key < end) {
                    overlap += bookings.get(key);
                    max = Math.max(max, overlap);
                }
            }
            if (max > 2) {
                bookings.put(start, bookings.getOrDefault(start, 0) - 1);
                bookings.put(end, bookings.getOrDefault(end, 0) + 1);
                return false;
            }
            return true;
        }
    }

    public static void main(String[] args) {
        MyCalendarTwo MyCalendar = new MyCalendarTwo();
        System.out.println(MyCalendar.book(10, 20)); // returns true
        System.out.println(MyCalendar.book(50, 60)); // returns true
        System.out.println(MyCalendar.book(10, 40)); // returns true
        System.out.println(MyCalendar.book(5, 15)); // returns false
        System.out.println(MyCalendar.book(5, 10)); // returns true
        System.out.println(MyCalendar.book(25, 55)); // returns true
    }
}
