package com.example.ConcurrencyPackage;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.*;
/*
BlockingQueue is thread-safe
 */
@AllArgsConstructor
class Producer2 implements Runnable {
    BlockingQueue<Integer> queue;
    int MAX_SIZE;
    static Integer i = 0;

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            TimeUnit.SECONDS.sleep(1);
            i++;
            queue.add(i);
            System.out.println(Thread.currentThread().getName() + "-- ADDED =" + i + ", QUEUE_SIZE =" + queue.size());
        }
    }
}

@AllArgsConstructor
class Consumer2 implements Runnable {
    BlockingQueue<Integer> queue;

    @SneakyThrows
    @Override
    public void run() {
        int i = 0;
        while (true) {
            if(queue.isEmpty()){
                System.out.println(Thread.currentThread().getName() + " is Empty");
            }
            TimeUnit.SECONDS.sleep(1);
            Integer x = queue.take();
            System.out.println(Thread.currentThread().getName() + " -- FOUND =" + x);
        }
    }
}

public class PC2 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()); // prints main

        int MAX_SIZE = 5;
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(MAX_SIZE);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for(int i=0;i<2;i++) {
            executorService.submit(new Producer2(queue, MAX_SIZE));
        }
        for(int i=0;i<2;i++) {
            executorService.submit(new Consumer2(queue));
        }
    }
}
