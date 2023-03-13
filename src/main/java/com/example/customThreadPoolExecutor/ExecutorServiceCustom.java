package com.example.customThreadPoolExecutor;

import lombok.AllArgsConstructor;

import java.util.concurrent.LinkedBlockingQueue;

interface MyExecutorService {
    void submit(Runnable r);
}

class MyThreadPool implements MyExecutorService {
    static int			        capacity;
    static int			        currentCapacity;
    static LinkedBlockingQueue<Runnable> linkedBlockingQueue;
    Execution				e;

    public MyThreadPool(int capacity) {
        this.capacity = capacity;
        currentCapacity = 0;
        linkedBlockingQueue = new LinkedBlockingQueue<Runnable>();
        e = new Execution();
    }

    @Override
    public void submit(Runnable r) {
        linkedBlockingQueue.add(r);
        e.executeMyMethod();
    }
}

class Execution implements Runnable {
    void executeMyMethod() {
        if (MyThreadPool.currentCapacity < MyThreadPool.capacity) {
            MyThreadPool.currentCapacity++;
            Thread t = new Thread(new Execution());
            t.start();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (MyThreadPool.linkedBlockingQueue.size() != 0) {
                MyThreadPool.linkedBlockingQueue.poll().run();
            }
        }
    }
}

class MyExecutors {
    static MyExecutorService myExecutorService;
    static MyExecutorService myNewFixedThreadPool(int capacity) {
        if(myExecutorService == null){
            myExecutorService = new MyThreadPool(capacity);
        }
        return myExecutorService;
    }
}

@AllArgsConstructor
class Mytask implements Runnable {
    int id;

    @Override
    public void run() {
        try {
//            System.out.println("started Thread_id: "+ id);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done Thread_id: "+ id+ " = " + Thread.currentThread().getName());
    }
}

public class ExecutorServiceCustom {
    public static void main(String[] args) {
        MyExecutorService service = MyExecutors.myNewFixedThreadPool(1000);
        for (int i = 0; i < 1000; i++) {
            service.submit(new Mytask(i));
        }
        System.out.println("All done");
//        MyExecutorService service2 = MyExecutors.myNewFixedThreadPool(3);
//        for (int i = 0; i < 10; i++) {
//            final int x = i;
//            service.submit(() -> {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("print = " + x);
//            });
//        }
    }
}