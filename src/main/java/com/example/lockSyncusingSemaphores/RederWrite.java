package com.example.lockSyncusingSemaphores;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
class Reader implements Runnable {
    final List<Integer> data;

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("Reader");
        synchronized (data) {
            while (data.isEmpty()) {
                System.out.println("Waiting");
                data.wait();
            }
            System.out.println(data);
        }
    }
}

@AllArgsConstructor
class Writer implements Runnable {
    final List<Integer> data;

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("Writer");
        synchronized (data) {
            if (data.isEmpty()) {
                Thread.sleep(1000);
                data.add(1);
                System.out.println(data.isEmpty());
                data.notifyAll();
            }
        }
    }
}

public class RederWrite {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> data = new ArrayList<>();
        new Thread(new Reader(data)).start();
        new Thread(new Reader(data)).start();
        new Thread(new Reader(data)).start();
        Thread.sleep(1000);
        new Thread(new Writer(data)).start();
        new Thread(new Writer(data)).start();
        new Thread(new Writer(data)).start();
    }
}
