package com.example.algos;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Exam {
    static class ExamRoom {
        Map<Integer, Integer> map = new TreeMap<>();
        int n;
        public ExamRoom(int N) {
            this.n = N;
        }

        public int seat() {
            Iterator it = map.entrySet().iterator();
            int start = 0;
            while(it.hasNext()) {
                Map.Entry x = (Map.Entry) it.next();
                x.getKey();
            }
            return -1;
        }

        public void leave(int p) {

        }
    }

    public static void main(String[] args) {
        ExamRoom obj = new ExamRoom(10);
    }
}
