package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Pair<K,V> implements Serializable {
    private K key;
    private V value;
    public static<K,V> Pair<K,V> of(K key, V value){
        return new Pair<K,V>(key, value);
    }
    public String toString() {
        return this.key + "=" + this.value;
    }

    public int hashCode() {
        byte var1 = 7;
        int var2 = 31 * var1 + (this.key != null ? this.key.hashCode() : 0);
        var2 = 31 * var2 + (this.value != null ? this.value.hashCode() : 0);
        return var2;
    }

    public boolean equals(Object var1) {
        if (this == var1) {
            return true;
        } else if (!(var1 instanceof Pair)) {
            return false;
        } else {
            Pair var2 = (Pair)var1;
            if (this.key != null) {
                if (!this.key.equals(var2.key)) {
                    return false;
                }
            } else if (var2.key != null) {
                return false;
            }

            if (this.value != null) {
                if (!this.value.equals(var2.value)) {
                    return false;
                }
            } else if (var2.value != null) {
                return false;
            }

            return true;
        }
    }
}
