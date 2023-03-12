package com.example;

import javafx.util.Pair;

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
        CustomPair<Integer, Integer> customPair = CustomPair.of(1, 2);
        Pair<Integer, Integer> pair = new Pair<>(1, 2);
        TimeUnit.SECONDS.sleep(2);
        System.out.println("SplitWiseApp.main()");
        System.out.println(customPair);
        System.out.println(pair);
    }
}
