package com.example.lld;

//import javafx.util.Pair;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.Pair;

interface TaskSchedulerInterface {
    void addTask(Runnable task, Date date);
}

public class TaskScheduler implements TaskSchedulerInterface {
    ExecutorService executorService;
    Map<Integer, Runnable> tasks;
    PriorityQueue<Pair<Integer, Long>> pq;
    volatile int id = 0;

    TaskScheduler(){
        this(Executors.newSingleThreadExecutor());
    }

    TaskScheduler(ExecutorService executorService){
        this.executorService = executorService;
        this.tasks = new HashMap<>();
        this.pq = new PriorityQueue<>((a,b) -> (int) (a.getKey()-b.getKey()));
    }

    @Override
    public void addTask(Runnable task, Date date) {
        System.out.println(new Pair<>(new Date(), date));
        id = id + 1;
        tasks.put(id, task);
        long millis = date.getTime();
        scheduleTask(id, millis);
        pq.add(new Pair(id, millis));
    }

    private void showTasks(){
        System.out.println(tasks);
    }
    private void scheduleTask(int id, long millis){
        pq.add(new Pair<>(id, millis));
    }

    public static void main(String[] args) {
        TaskScheduler taskScheduler = new TaskScheduler();
        taskScheduler.addTask(()-> System.out.println("First Task"), new Date(System.currentTimeMillis()+5000));
    }
}

// test push