package com.example.uber.interview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;

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
    int id;

    @Override
    public String toString() {
        return "Task Id:" + id;
    }
}

interface Scheduler {
    void addTask(Task task);

    void cancelTask(Task task);

    void stopRecurring(Task task);

    boolean execute(Task task);
}

@Data
@ToString
class SchedulerImpl implements Scheduler {
    final PriorityQueue<Task> pq = new PriorityQueue<>((a, b) -> (int) (a.time - b.time));
    final Map<Task, Boolean> map = new HashMap<>();
    Map<Integer, Task> tMap = new HashMap<>();

    @Override
    public void addTask(Task task) {
        synchronized (pq) {
            System.out.println("Task " + task.toString() + " Added");
            task.time = System.currentTimeMillis() + task.timeUnit.toMillis(task.duration);
            tMap.put(task.id, task);
            pq.add(task);
            map.put(task, true);
            pq.notifyAll();
        }
    }

    void scheduleRecurring(Task task) {
        if (task.recurring && map.get(task)) {
            addTask(task);
        }
    }

    @Override
    public void cancelTask(Task task) {
        synchronized (map) {
            System.out.println("Task " + task.toString() + " cancelled");
            map.put(task, false);
        }
    }

    @Override
    public void stopRecurring(Task task) {

    }

    @Override
    public boolean execute(Task task) {
        synchronized (map) {
            if (map.getOrDefault(task, false)) {
                scheduleRecurring(task);
                new Thread(task.runnable).start();
                return true;
            }
            map.remove(task);
        }
        return false;
    }
}

class ExecuterClass implements Runnable {
    SchedulerImpl obj;

    ExecuterClass(SchedulerImpl obj) {
        this.obj = obj;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            synchronized (obj.pq) {
                if (!obj.pq.isEmpty()) {
                    Task top = obj.pq.peek();
                    if (top.time <= System.currentTimeMillis()) {
                        obj.execute(top);
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
            Runnable runnable = () -> System.out.println("task completed" + finalI);
            Task task = new Task(runnable, finalI + 1, TimeUnit.SECONDS, true, 0, i);
            scheduler.addTask(task);
        }
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Task task = new Task(() -> scheduler.cancelTask(scheduler.tMap.get(finalI)), 5, TimeUnit.SECONDS, false, 0,
                    i + 10);
            scheduler.addTask(task);
        }
        // System.out.println(scheduler.toString());
    }
}
