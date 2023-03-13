package com.example.Maps;

import lombok.Data;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class Maps {

}

interface MapInterface<K, V> {
    V get(K key);
    void put(K key, V value);
}

class MyMap<K, V> implements MapInterface<K, V> {
    @Data
    class Node {
        K key;
        V val;
    }

    int size;
    LinkedList<Node>[] arr;

    int hash(K key){
        return 0;
    }

    public MyMap(int size) {
        this.size = size;
        arr = new LinkedList[10];
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public void put(K key, V value) {

    }
}

class MainRunner {
    public static void main(String[] args) {
        MyMap<String, Integer> myMap = new MyMap<String, Integer>(10);
        myMap.put("1", 1);
        myMap.put("2", 2);
        System.out.println(myMap.get("1"));

    }
}

class MainRunner2 {
    public static void main(String[] args) {
        Map<String, Integer> myMap = new TreeMap<>();
        myMap.put("1", 1);
        myMap.put("2", 2);
        System.out.println(myMap.get("1"));

    }
}
class MainRunner3 {
    public static void main(String[] args) {
        Map<String, Integer> myMap = new ConcurrentHashMap<>();
        myMap.put("1", 1);
        myMap.put("2", 2);
        System.out.println(myMap.get("1"));

    }
}
class MainRunner4 {
    public static void main(String[] args) {
        Map<String, Integer> myMap = new Hashtable<>();
        myMap.put("1", 1);
        myMap.put("2", 2);
        System.out.println(myMap.get("1"));
    }
}
