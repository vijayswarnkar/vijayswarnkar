package com.example.lockSyncusingSemaphores;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;

class Buffer<T> {

    List<T> items;
    private final int capacity;

    private final Semaphore consumeSemaphore = new Semaphore(0);
    private final Semaphore produceSemaphore = new Semaphore(1);
    private final Semaphore pcCoordinatorSemaphore = new Semaphore(1);

//    private final Mutex mutex = new Mutex();
//    mutex.lock();
//    mutex.unlock();

    Buffer(int capacity) {
        this.items = new ArrayList<>(capacity);
        this.capacity = capacity;
    }

    T getItem() throws InterruptedException {
        T item = null;

        consumeSemaphore.acquire();
        pcCoordinatorSemaphore.acquire();
        while (items.size() == 0) {
            pcCoordinatorSemaphore.release();
            Thread.sleep(100);
            pcCoordinatorSemaphore.acquire();
        }

//        System.out.println("Consume availablePermits: " + consumeSemaphore.availablePermits());
        item = items.remove(items.size() - 1);

        pcCoordinatorSemaphore.release();
        produceSemaphore.release();
        return item;
    }

    void addItem(T item) throws InterruptedException {
        produceSemaphore.acquire();
        pcCoordinatorSemaphore.acquire();

        while (items.size() > capacity) {
            pcCoordinatorSemaphore.release();
            Thread.sleep(100);
            pcCoordinatorSemaphore.acquire();
        }

        items.add(item);

        pcCoordinatorSemaphore.release();
        consumeSemaphore.release();
        produceSemaphore.release();
        System.out.println("Consume availablePermits: " + consumeSemaphore.availablePermits());
        System.out.println("produceSemaphore availablePermits: " + produceSemaphore.availablePermits());
    }

}

class Producer extends Thread{

    public String producerName;
    Buffer<String> buffer;

    public Producer(String name, Buffer<String> buffer) {
        this.producerName = name;
        this.buffer = buffer;
    }

    @SneakyThrows
    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            System.out.println(producerName + " produces item " + i);
            buffer.addItem(i + " produced by: " + producerName);
        }

    }
}

class Consumer extends Thread{

    public String consumerName;
    Buffer<String> buffer;

    public Consumer(String name, Buffer<String> buffer) {
        this.consumerName = name;
        this.buffer = buffer;
    }

    @SneakyThrows
    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            String item = null;
            item = buffer.getItem();
            System.out.println("The item \"" + item + "\" consumed by " + consumerName);
        }

    }
}
public class SyncUsingSemaphores {
    public static void main(String[] args) throws InterruptedException {

        Buffer<String> buffer = new Buffer<>(5);

        Set<Thread> producers = new HashSet<>();
        Set<Thread> consumers = new HashSet<>();


        for(int i = 0; i < 1; i++) {
            producers.add(new Producer("Producer"+i, buffer));
        }
        for(int i = 0; i < 1; i++) {
            consumers.add(new Consumer("Consumer"+i, buffer));
        }

        consumers.forEach(Thread::start);
        producers.forEach(Thread::start);

        for (Thread producer : producers) {
            producer.join();
        }

        for (Thread consumer : consumers) {
            consumer.join();
        }
    }
}

