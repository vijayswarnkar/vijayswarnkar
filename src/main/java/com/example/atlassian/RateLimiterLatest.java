package com.example.atlassian;


// Rate limiter service which works on cust_id{integer} - already fed to system
// isAllowed(1) - t/f

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.example.Pair;

interface RateLimiter {
    boolean isAllowed(int id);
}
enum Strategy {
    TOKEN_BUCKET_STRATEGY,
    FIXED_WINDOW_COUNTER_STRATEGY
};

@AllArgsConstructor
class Config {
    int id; // 1
    TimeUnit timeUnit; // SECONDS
    int duration; // 5
    int numRequests; // 5
}


class ConfigManager {
    Map<Integer, Config> configMap = new ConcurrentHashMap<>();
    void add(Config config){
        configMap.put(config.id, config);
    }
    Config get(int id){
        return configMap.get(id);
    }
}

abstract class RateLimiterImpl implements RateLimiter {
    ConfigManager configManager = new ConfigManager();

    abstract public void applyStrategy(int id);

    RateLimiterImpl(List<Config> configs) {
        for (Config config : configs) {
            configManager.add(config);
        }
    }
    abstract public boolean isAllowed(int id);
}
class RateLimiterImplTokenBucket extends RateLimiterImpl {
    Map<Integer, Pair<Integer, Long>> counterMap = new ConcurrentHashMap<>(); // id -> (count, ts) // ConcurrentHashMap

    RateLimiterImplTokenBucket(List<Config> configs) {
        super(configs);
    }

    @Override
    public void applyStrategy(int id) {
        Config config = configManager.get(id);
        if(!counterMap.containsKey(id)){
            counterMap.put(id, new Pair<>(config.numRequests, System.nanoTime()));
        } else {
            Pair<Integer, Long> data = counterMap.get(id);
            long lastTs = data.getValue();
            long currTime = System.nanoTime();
            int count = data.getKey();
            int refillValue = (int) (config.numRequests * ((currTime - lastTs) / (config.timeUnit.toNanos(config.duration))));
            int totalCount = Math.min(config.numRequests, refillValue + count);
            counterMap.put(id, new Pair<>(totalCount, currTime));
        }
    }

    @Override
    public boolean isAllowed(int id) {
        applyStrategy(id);
        System.out.println(counterMap);
        if(counterMap.get(id).getKey() > 0) {
            counterMap.put(id, new Pair<>(counterMap.get(id).getKey()-1, System.nanoTime()));
            return true;
        }
        return false;
    }
}

class RateLimiterImplFactory {
    public static RateLimiter getInstance(Strategy strategy, List<Config> configs) throws Exception {
        switch (strategy) {
            case TOKEN_BUCKET_STRATEGY:
                return new RateLimiterImplTokenBucket(configs);
            case FIXED_WINDOW_COUNTER_STRATEGY:
                throw new Exception("not implemented");
            default:
                // not a valid choice - throw exception
                throw new Exception("not valid");
        }
    }
}
public class RateLimiterLatest {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");
        
    }
}