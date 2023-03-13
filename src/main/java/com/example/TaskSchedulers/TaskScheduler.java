package com.example.TaskSchedulers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.*;

interface taskScheduler {
    void addTask(int taskId, int time) throws InterruptedException;
}

interface taskExecutor {
    void execute() throws InterruptedException;
}

@AllArgsConstructor
class TimedExecutor extends Thread implements taskExecutor {
    final Queue<TaskObject> priorityQueue;
    int MAX_SIZE;

    @Override
    public void execute() throws InterruptedException {
        synchronized (priorityQueue) {
            while (priorityQueue.isEmpty()) {
                System.out.println("checking empty: " + priorityQueue.isEmpty());
                priorityQueue.wait();
            }
            System.out.println("Not empty");
            if(!priorityQueue.isEmpty()) {
                TaskObject taskObject = priorityQueue.peek();
//                System.out.println(taskObject.executionTime - System.currentTimeMillis());
                if (taskObject.executionTime <= System.currentTimeMillis()) {
                    taskObject.run();
//                    new Thread(taskObject).start(); // runs on a different thread
                    priorityQueue.remove();
                    priorityQueue.notifyAll();
                } else {
//                    System.out.println("Timed waiting");
                    priorityQueue.wait(taskObject.executionTime - System.currentTimeMillis());
                }
            }
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            this.execute();
        }
    }
}

@AllArgsConstructor
class TaskObject implements Runnable {
    int id;
    long executionTime;

    @Override
    public String toString() {
        return "TaskObject{" +
                "id=" + id +
                ", executionTime=" + executionTime +
                '}';
    }

    @Override
    public void run() {
        System.out.println("Executed: " + id + ", scheduled for: " +
                executionTime + ", at System.currentTimeMillis(): " + System.currentTimeMillis()
                + Thread.currentThread().getName());
    }
}

@AllArgsConstructor
class Scheduler1 implements taskScheduler {
    final Queue<TaskObject> priorityQueue;
    int MAX_SIZE;

    @Override
    public void addTask(int taskId, int delay) throws InterruptedException {
        synchronized (priorityQueue) {
            if (priorityQueue.size() < MAX_SIZE) {
                priorityQueue.add(new TaskObject(taskId, System.currentTimeMillis() + (delay * 1000)));
//                System.out.println("Added " + taskId + " to queue");
                priorityQueue.notifyAll();
//                System.out.println("checking empty2: " + priorityQueue.isEmpty());
            }
        }
    }

    public void print() {
        System.out.println(priorityQueue.peek());
//        System.out.println("Print...");
//        while (!priorityQueue.isEmpty()) {
//            System.out.println(priorityQueue.remove());
//        }
    }
}

class TaskScheduler2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        int MAX_SIZE = 10;

        Queue<TaskObject> priorityQueue = new PriorityQueue<>(MAX_SIZE,
                (a, b) -> (int) (a.executionTime - b.executionTime));
        new Thread(new TimedExecutor(priorityQueue, MAX_SIZE), "Executor1").start();
        new Thread(new TimedExecutor(priorityQueue, MAX_SIZE), "Executor2").start();
        Thread.sleep(200);
        Scheduler1 scheduler = new Scheduler1(priorityQueue, MAX_SIZE);
//        System.out.println(System.currentTimeMillis());
        scheduler.addTask(1, 3);
        scheduler.addTask(2, 2);
        scheduler.addTask(3, 1);
        scheduler.addTask(4, 1);
        scheduler.addTask(5, 1);
        scheduler.print();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture scheduledFuture = executor.schedule(() -> {
            System.out.println("1 second" + Thread.currentThread().getName());
            return new Date().getTime();
        }, 1, TimeUnit.SECONDS);
        System.out.println(scheduledFuture.get(2, TimeUnit.SECONDS) + Thread.currentThread().getName());

        System.out.println(new Date().getTime());
        System.out.println(new Date().toString().split(" ")[3]);
 // your code here
        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        System.out.println("5 seconds");
                    }
                },
                5000
        );

    }
}

