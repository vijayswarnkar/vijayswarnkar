package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.stream.*;
import javafx.util.*;
import java.util.*;

interface TaskSchedulerInterface {
    void addTask(Runnable task, Date date);
    List<Runnable> getTasks();
}

class TaskSchedulerApp {
    
}

public class TaskScheduler {
    public static void main(String[] args) {
        System.out.println("SplitWiseApp.main()");
    }
}
