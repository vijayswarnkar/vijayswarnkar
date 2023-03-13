package com.example.LockSyncUsingLock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Buffer<T> {

    List<T> items;
    private int capacity;

    private Lock pcLock = new ReentrantLock();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
//        rwLock.readLock().lock();
//        rwLock.readLock().unlock();

    void readWrite() {
        rwLock.readLock().lock();
        rwLock.readLock().unlock();
    }

    Buffer(int capacity) {
        this.items = new ArrayList<>(capacity);
        this.capacity = capacity;
    }

    T getItem() {
        T item = null;

        while (true) {
            pcLock.lock();

            if (items.size() > 0) {
                item = items.remove(0);
                break;
            }

            pcLock.unlock();
        }

        pcLock.unlock();
        return item;
    }

    void addItem(T item) {
        while (true) {
            pcLock.lock();

            if (items.size() < capacity) {
                items.add(item);
                break;
            }
            pcLock.unlock();
        }
        pcLock.unlock();
    }

}

class Producer extends Thread {

    public String producerName;
    Buffer<String> buffer;

    public Producer(String name, Buffer<String> buffer) {
        this.producerName = name;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(producerName + " produces item " + i);
            buffer.addItem(i + " produced by: " + producerName);
        }

    }
}

class Consumer extends Thread {

    public String consumerName;
    Buffer<String> buffer;

    public Consumer(String name, Buffer<String> buffer) {
        this.consumerName = name;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            String item = null;
            item = buffer.getItem();
            System.out.println("The item \"" + item + "\" consumed by " + consumerName);
        }

    }
}

public class SyncWithLock {

    public static void main(String[] args) throws InterruptedException {

        Buffer<String> buffer = new Buffer<>(5);

        HashSet<Thread> producers = new HashSet<>();
        HashSet<Thread> consumers = new HashSet<>();

        for (int i = 0; i < 5; i++) {
            producers.add(new Producer("Producer" + i, buffer));
        }
        for (int i = 0; i < 5; i++) {
            consumers.add(new Consumer("Consumer" + i, buffer));
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