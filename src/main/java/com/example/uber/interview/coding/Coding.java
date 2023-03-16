package com.example.uber.interview.coding;

import java.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.*;

// Design an Asynchronous Task Management Library
// Requirements:
// Should allow users to define a task 
// Tasks can have dependencies on other tasks
// Tasks get added to a Queue
// Should have a main task runner (run once for this problem) that runs the tasks in the queue.
// Program exits when the queue is empty.


// Test your solution by performing the following operations
// Task 1 , depends on Task 2
// Task2 prints  “Running Task2”
// Task 1 prints “Running Task1” 
// Task 3 prints “Running Task 3”


// And then validate order of task execution - with the following output
// Task 2
// Task 1
// Task 3
// 
/*
{t1->[t2, t3]}

t1 -> 
[t1 t2 t3 t4 t5]

*/

/*
Write a Counter class that will hold an expiring count for a each element. The expiration that is limited by a time window is defined when the class is constructed., say 5 mins, meaning we only need to keep data for the last 5 mins.
The functions that need to be implemented are:
putcount(key) - no return value
get_count(key) - returns integer
get_total_count() - returns integer

Example:
+------------+--------------------+-----------------------------------------------------------+
| timestamp  |     operation      |                          result                           |
+------------+--------------------+-----------------------------------------------------------+
| 1647718624 | count(‘a’)         | -                                                         |
| 1647718630 | count(‘a’)         | -                                                         |
| 1647718730 | count(‘b’)         | -                                                         |
| 1647718730 | get_count(‘a’)     | 2                                                         |
| 1647718731 | get_total_count()  | 3 (2 ‘a’ and 1 ‘b’)                                       |
| 1647718925 | get_count(‘a’)     | 1 (‘a’ at timestamp 1647718624 is out of the time window) |
| 1647718925 | get_total_count()  | 2 (1 ‘a’ and 1 ‘b’)                                       |
+------------+--------------------+-----------------------------------------------------------+


*/

// Main class should be named 'Solution' and should not be public.
class Task {
    String name;
    Runnable runnable;
    Task(Runnable runnable){
        this.runnable = runnable;
    }
}

class RunTasks implements Runnable {
    Stack<Task> tasks;
    RunTasks(Stack<Task> tasks){
        this.tasks = tasks;
    }
    @Override
    public void run(){
        while(!tasks.isEmpty()){
            tasks.pop().runnable.run();
        }
    }
}
public class Coding {
    static void findTaskOrder(Task task, Map<Task, List<Task>> dependencies, Set<Task> vis, Stack<Task> stack){       
        if(vis.contains(task)) return;
        vis.add(task);
        stack.push(task);
        for(Task child: dependencies.getOrDefault(task, new ArrayList<>())){
            findTaskOrder(child, dependencies, vis, stack);
        }
    }
    public static void main(String[] args) {
        Task task1 = new Task(() -> System.out.println("Task 1"));
        Task task2 = new Task(() -> System.out.println("Task 2"));
        Task task3 = new Task(() -> System.out.println("Task 3"));
        List<Task> tasks = List.of(task1, task2, task3);
        Map<Task, List<Task>> dependencies = Map.of(
            task1, List.of(task2)            
        );   
        Set<Task> vis = new HashSet<>();
        for(Task task: tasks){
            if(!vis.contains(task)){
                Stack<Task> stack = new Stack<>();
                findTaskOrder(task, dependencies, vis, stack);   
                // System.out.print(stack);         
                new Thread(new RunTasks(stack)).start();
            }
        }
        // System.out.println("Hello, World");
    }
}