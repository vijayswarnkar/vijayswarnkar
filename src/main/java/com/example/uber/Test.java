package com.example.uber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

/*
input: (Task->Runnable(), duration, recurring(T/F))
    {task - oneTime/Recurring}
 */
@AllArgsConstructor
class Task {
    Runnable runnable;
    int duration;
    TimeUnit timeUnit;
    boolean recurring;
    long time;
}

interface Scheduler {
    void addTask(Task task);

    void cancelTask(Task task);

    void stopRecurring(Task task);

    void execute();
}

class SchedulerImpl implements Scheduler {
    final PriorityQueue<Task> pq = new PriorityQueue<>((a, b) -> (int) (a.time - b.time));
    Map<Task, Boolean> map = new HashMap<>(); //

    @Override
    public void addTask(Task task) {
        synchronized (pq) {
            task.time = System.currentTimeMillis() + task.timeUnit.toMillis(task.duration);
            pq.add(task);
            map.put(task, true);
            pq.notifyAll();
        }
    }

    void scheduleRecurring(Task task) {
        if (task.recurring) {
            addTask(task);
        }
    }

    @Override
    public void cancelTask(Task task) {
        map.put(task, false);
    }

    @Override
    public void stopRecurring(Task task) {

    }

    @Override
    public void execute() {

    }
}

class ExecuterClass implements Runnable {
    SchedulerImpl obj;

    ExecuterClass(SchedulerImpl obj) {
        this.obj = obj;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            synchronized (obj.pq) {
                if (!obj.pq.isEmpty()) {
                    Task top = obj.pq.peek();
                    if (top.time <= System.currentTimeMillis()) {
                        obj.scheduleRecurring(top);
                        new Thread(top.runnable).start();
                        obj.pq.remove();
                    } else {
                        obj.pq.wait(top.time - System.currentTimeMillis());
                    }
                } else {
                    obj.pq.wait();
                }
            }
        }
    }
}

public class Test {
    public static void main(String[] args) {
        System.out.println("Test.main");
        SchedulerImpl scheduler = new SchedulerImpl();
        ExecuterClass executerClass = new ExecuterClass(scheduler);
        new Thread(executerClass).start();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Runnable runnable = () -> System.out.println("task" + finalI);
            Task task = new Task(runnable, i + 1, TimeUnit.SECONDS, true, 0);
            scheduler.addTask(task);
        }
    }
}
