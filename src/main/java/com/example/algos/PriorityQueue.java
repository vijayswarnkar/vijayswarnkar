package com.example.algos;

import javafx.util.Pair;

import java.util.PriorityQueue;

class PriorityQueueExample {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((x,y) -> Integer.compare(y,x));
        pq.add(10);
        pq.add(5);
        pq.add(20);

//        Iterator it = pq.iterator();
//        while(it.hasNext()){
//            System.out.println(it.next());
//        }
        while(!pq.isEmpty()){
            System.out.println(pq.poll());
        }
    }
}

class PriorityQueuePairExample {
    public static void main(String[] args) {
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>((x, y) -> Integer.compare(y.getValue(),x.getValue()));
        pq.add(new Pair<>(10, 0));
        pq.add(new Pair<>(5, 1));
        pq.add(new Pair<>(20, 2));

        while(!pq.isEmpty()){
            System.out.println(pq.poll());
        }
    }
}
