package com.example.asynCallsInJava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncJava {
    public static void main(String[] args) throws InterruptedException {
        List<CompletableFuture<Integer>> completableFutureList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            final int id = i;
            CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(()-> {
                try {
                    return fetchEmployeeId(id);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }).thenApplyAsync(x -> {
                try {
                    return calculateTax(x);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return x;
            });
            completableFutureList.add(completableFuture);
        }
        completableFutureList.forEach(x->{
            try {
                System.out.println("finally:" + x.get(2, TimeUnit.SECONDS)); // blocking
                if(x.isDone()){ // non-blocking
                    System.out.println("finally2:" + x.get());
                }
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        });
        System.out.println("done");
    }

    public static int fetchEmployeeId(int id) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("id:" + id);
        return id;
    }

    public static int calculateTax(int id) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("tax:" + id*2);
        return id*2;
    }
}
