package com.example.customThreadPoolExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

interface ExecutorServiceInterface{
    void submit(Runnable runnable) throws InterruptedException;
}

class CustomExecutorService implements ExecutorServiceInterface{
    private final BlockingQueue<Runnable> taskQueue;
    private final Thread[] threads;
    int capacity;

    public CustomExecutorService(int capacity){
        this.capacity = capacity;
        taskQueue = new LinkedBlockingQueue<>();
        threads = new Thread[capacity];

        for(Thread thread : threads){
            thread = new Worker();
            thread.start();
        }
    }

    @Override
    public void submit(Runnable runnable) throws InterruptedException {
         taskQueue.put(runnable);
    }

    class Worker extends Thread {
        @Override
        public void run() {
            while(true){
                if(taskQueue.size() > 0){
                    try {
                        taskQueue.take().run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
public class customThreadPoolExecutorNew {
    public static void main(String[] args) throws InterruptedException {
        CustomExecutorService customExecutorService = new CustomExecutorService(3);
        for(int i=0;i<10;i++){
            customExecutorService.submit(()->{
                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
