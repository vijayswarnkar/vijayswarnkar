package com.example;

import java.util.*;
import java.util.concurrent.TimeUnit;

interface TaskSchedulerInterface {
    void addTask(Runnable task, Date date);
    List<Runnable> getTasks();
}

class TaskSchedulerApp {
    
}

public class TaskScheduler {
    public static void main(String[] args) throws InterruptedException {
        Pair<Integer, Integer> customPair = Pair.of(1, 2);
        TimeUnit.SECONDS.sleep(2);
        System.out.println("SplitWiseApp.main()");
        System.out.println(customPair);
    }
}
