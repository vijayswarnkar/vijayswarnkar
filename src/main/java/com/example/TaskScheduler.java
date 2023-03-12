package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.stream.*;
// import javafx.util.*;
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
        TimeUnit.SECONDS.sleep(2);
        System.out.println("SplitWiseApp.main()");
    }
}
