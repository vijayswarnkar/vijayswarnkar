package com.example.Linked;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

class MyClass {
    public static void main(String[] args) {
        LinkedList<String> cars = new LinkedList<String>();
        cars.add("Volvo");
        cars.add("BMW");
        cars.add("Ford");
        cars.addFirst("Mazda");

        System.out.println(cars);
        Iterator it = cars.iterator();
        while (it.hasNext()){
            String x = (String) it.next();
            System.out.println(x);
            if(x.equals("BMW")) {
                cars.remove(2);
                break;
            }
        }
        System.out.println(cars);
    }
}

class QueueImpl {
    public static void main(String[] args) {
        Queue<String> cars = new LinkedList<String>();
        cars.add("Volvo");
        cars.add("BMW");
        cars.add("Ford");

        System.out.println(cars);
        System.out.println(cars.peek());
        System.out.println(cars.poll());
        System.out.println(cars);
    }
}

class DueueImpl {
    public static void main(String[] args) {
        Deque<String> cars = new LinkedList<String>();
        cars.add("Volvo");
        cars.add("BMW");
        cars.add("Ford");
        System.out.println(cars);
        System.out.println(cars.peekFirst());

        cars.addLast("Ford1");
        cars.addFirst("Ford2");

        System.out.println(cars);
        System.out.println(cars.peek());
        System.out.println(cars.poll());
        System.out.println(cars);
    }
}

public class ListClass {

}
/*
 */