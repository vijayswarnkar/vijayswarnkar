package com.example.atlassian;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

class ClientId {
    int id;

    ClientId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientId clientId = (ClientId) o;
        return id == clientId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

class Bucket {
    int maxRequests;
    int currRequests;
    long lastTs;
    int unit;
    TimeUnit timeUnit;
    long den;

    Bucket(int maxRequests, int unit, TimeUnit timeUnit) {
        this.maxRequests = maxRequests;
        this.unit = unit;
        this.timeUnit = timeUnit;
        currRequests = maxRequests;
        lastTs = System.nanoTime();
        den = timeUnit.toNanos(1);
//        switch (timeUnit) {
//            case SECONDS:
//                den = (long) 1e9;
//                break;
//            default:
//                den = (long) 1e9;
//                break;
//        }
    }

    void fill() {
        long now = System.nanoTime();
        int extra = maxRequests * (int) ((now - lastTs) / den);
        lastTs = System.nanoTime();
        this.currRequests = Math.min(maxRequests, extra + this.currRequests);
        System.out.println(now + "," + lastTs + "," + extra + "," + this.currRequests);
    }

    synchronized boolean isAllowed(int quota) {
        fill();
        if (this.currRequests >= quota) {
            this.currRequests -= quota;
            return true;
        } else {
            return false;
        }
    }
}

//class Config{
//    int maxReq;
//    int unit;
//}
//
//class ClientConfig{
//    ClientConfig(Map<ClientId, Config> map){
//
//    }
//    Config getConfig(ClientId id){
//
//    }
//}

class RateLimit {
    Map<ClientId, Bucket> map;

    RateLimit() {
        map = new HashMap<>();
    }

    boolean isAllowed(int id) {
        ClientId clientId = new ClientId(id);
//        System.out.println(clientId);
        if (!map.containsKey(clientId)) {
            map.put(clientId, new Bucket(60, 1, TimeUnit.MINUTES));
        }
        Bucket bucket = map.get(clientId);
        return bucket.isAllowed(1);
    }
}

public class Test {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(TimeUnit.MINUTES.toNanos(1));
        RateLimit rateLimit = new RateLimit();
        System.out.println(rateLimit.isAllowed(1));
        System.out.println(rateLimit.isAllowed(1));
        System.out.println(rateLimit.isAllowed(1));
        System.out.println(rateLimit.isAllowed(1));
        System.out.println(rateLimit.isAllowed(1));
        System.out.println(rateLimit.isAllowed(1));
        TimeUnit.SECONDS.sleep(2);
        System.out.println(rateLimit.isAllowed(1));
    }
}



