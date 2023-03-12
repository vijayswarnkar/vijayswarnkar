package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomPair<K,V> {
    private K key;
    private V value;
    public static<K,V> CustomPair<K,V> of(K key, V value){
        return new CustomPair<K,V>(key, value);
    }
}
