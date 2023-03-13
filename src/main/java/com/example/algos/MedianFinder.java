package com.example.algos;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

class MedianFinder {

    Queue<Integer> firstHalf;
    Queue<Integer> secondHalf;

    public MedianFinder() {
        Queue<Integer> firstHalf = new PriorityQueue<>(); // max
        Queue<Integer> secondHalf = new PriorityQueue<>(Comparator.reverseOrder()); //min
    }

    public void addNum(int num) {
        if(firstHalf.isEmpty() && secondHalf.isEmpty()){
            firstHalf.add(num);
        } else if(firstHalf.size() == secondHalf.size()){
            if(num <= firstHalf.peek()){
                firstHalf.add(num);
            } else {
                secondHalf.add(num);
            }
        } else if(firstHalf.size() <= secondHalf.size()){
            if(num <= firstHalf.peek()){
                secondHalf.add(firstHalf.remove());
                firstHalf.add(num);
            } else {
                secondHalf.add(num);
            }
        } else {
            if(num >= secondHalf.peek()){
                firstHalf.add(secondHalf.remove());
                secondHalf.add(num);
            } else {
                firstHalf.add(num);
            }
        }
    }

    public double findMedian() {
        if (firstHalf.size() == secondHalf.size()){
            return (firstHalf.peek() + secondHalf.peek()) / 2;
        } else if(firstHalf.size() > secondHalf.size()) {
            return firstHalf.peek();
        } else {
            return secondHalf.peek();
        }
    }

    public static void main(String[] args) {
        MedianFinder obj = new MedianFinder();
        obj.addNum(1);
        obj.addNum(2);
        obj.addNum(3);
        System.out.println(obj.firstHalf);
        System.out.println(obj.findMedian());
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
