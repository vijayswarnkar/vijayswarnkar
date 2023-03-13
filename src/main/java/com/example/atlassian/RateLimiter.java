package com.example.atlassian;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

class UserManager {
    UserManager register(Request request){
        return this;
    }
}

@Builder
class Request {
    int userId;
    int ip;

    @Override
    public String toString() {
        return "Request{" +
                "userId=" + userId +
                ", ip=" + ip +
                '}';
    }
}

interface Key {
    String getKeyHash();

    int getUniqueId();
}

@AllArgsConstructor
class DefaultKey implements Key {
    int userId;

    @Override
    public String getKeyHash() {
        return Integer.toString(userId);
    }

    @Override
    public int getUniqueId() {
        return userId;
    }

    @Override
    public String toString() {
        return "DefaultKey{" +
                "userId=" + userId +
                '}';
    }
}

@AllArgsConstructor
class userIpKey implements Key {
    int userId;
    int ip;

    @Override
    public String getKeyHash() {
        return Integer.toString(userId) + "_" + Integer.toString(ip);
    }

    @Override
    public int getUniqueId() {
        return userId*ip;
    }

    @Override
    public String toString() {
        return "userIpKey{" +
                "userId=" + userId +
                ", ip=" + ip +
                '}';
    }
}

@AllArgsConstructor
class AuditLogs {
    List<Pair<Request, Boolean>> logs;

    void log(Request request, boolean isServed) {
        logs.add(new Pair<>(request, isServed));
    }

    void getUserLogs() {
    }
}

class BillingManager {

}

@AllArgsConstructor
class Quota {
    TimeUnit timeUnit;
    int duration;
    int nRequests;

    @Override
    public String toString() {
        return "Quota{" +
                "timeUnit=" + timeUnit +
                ", duration=" + duration +
                ", nRequests=" + nRequests +
                '}';
    }
}

@AllArgsConstructor
class Config {
    Key key;
    Quota quota;
    boolean isActive;
    StrategyEnums strategy;

    @Override
    public String toString() {
        return "Config{" +
                "key=" + key +
                ", quota=" + quota +
                ", isActive=" + isActive +
                ", strategy=" + strategy +
                '}';
    }
}

interface RateLimiterServiceInterface {
    boolean isAllowed(Request request);

    Config getUserConfig(Request request);

    void updateUserConfig(Config config);
}

enum StrategyEnums {
    SLIDING_WINDOW_STRATEGY,
    LEAKY_BUCKET_STRATEGY
}

class StrategyFactory {
    static Strategy getStrategy(StrategyEnums enums) {
        switch (enums) {
            case SLIDING_WINDOW_STRATEGY:
                return SlidingWindowStrategy.getInstance();
            case LEAKY_BUCKET_STRATEGY:
                return new LeakyBucketStrategy();
            default:
                return SlidingWindowStrategy.getInstance();
        }
    }
}

interface Strategy {
    boolean isAllowed(Request request, Config config);
}

class SlidingWindowStrategy implements Strategy {
    static SlidingWindowStrategy slidingWindowStrategy;
    StorageDao storageDao;

    private SlidingWindowStrategy() {
        System.out.println("New instance");
    }

    private SlidingWindowStrategy(StorageDao storageDao) {
        this.storageDao = storageDao;
        System.out.println("New instance");
    }

    public static SlidingWindowStrategy getInstance() {
        if (slidingWindowStrategy == null) {
            slidingWindowStrategy = new SlidingWindowStrategy(new Storage(new HashMap<>()));
        }
        return slidingWindowStrategy;
    }

    @Override
    public boolean isAllowed(Request request, Config config) {
        long currentNanos = System.nanoTime();
        long window = config.quota.timeUnit.toNanos(config.quota.duration);
        long prevNanos = currentNanos - window;
        int count = storageDao.getCount(prevNanos, currentNanos, config.key);
        if (count < config.quota.nRequests) {
            storageDao.saveEntry(currentNanos, config.key);
            return true;
        }
        return false;
    }
}

class LeakyBucketStrategy implements Strategy {
    @Override
    public boolean isAllowed(Request request, Config config) {
        return false;
    }
}

interface StorageDao {
    int getCount(Long fromNano, Long toNano, Key key);

    void saveEntry(Long currentNano, Key key);
}

@AllArgsConstructor
class Storage implements StorageDao {
    Map<Key, List<Long>> map;

    @Override
    public int getCount(Long fromNano, Long toNano, Key key) {
        List<Long> list = map.getOrDefault(key, new ArrayList<>());
        return (int) list.size();
    }

    @Override
    public void saveEntry(Long currentNano, Key key) {
        List<Long> list = map.getOrDefault(key, new ArrayList<>());
        list.add(currentNano);
        map.put(key, list);
    }
}

@AllArgsConstructor
class ConfigManager {
    Map<Integer, Config> map;

    void add(Config config) {
        map.put(config.key.getUniqueId(), config);
    }

    Key getKey(Request request) {
        return map.get(request.userId).key;
    }

    Config getConfig(Request request) {
        return map.get(request.userId);
    }
}

@AllArgsConstructor
class RateLimiterService implements RateLimiterServiceInterface {
    StorageDao storageManager;
    ConfigManager configManager;
    AuditLogs auditLogs;

    RateLimiterService addConfig(Config config) {
        configManager.add(config);
        return this;
    }

    @Override
    public boolean isAllowed(Request request) {
        Config config = configManager.getConfig(request);
        Strategy strategy = StrategyFactory.getStrategy(config.strategy);
        boolean response = strategy.isAllowed(request, config);
        auditLogs.log(request, response);
        return response;
    }

    @Override
    public Config getUserConfig(Request request) {
        return configManager.getConfig(request);
    }

    @Override
    public void updateUserConfig(Config config) {

    }
}

public class RateLimiter {
    public static void main(String[] args) {
        System.out.println("Rate limiter init...");

        RateLimiterService rateLimiterService = new RateLimiterService(
                new Storage(new HashMap<>()),
                new ConfigManager(new HashMap<>()),
                new AuditLogs(new ArrayList<>())
        ).addConfig(
                new Config(
                        new DefaultKey(1),
                        new Quota(TimeUnit.SECONDS, 10, 2),
                        true,
                        StrategyEnums.SLIDING_WINDOW_STRATEGY)
        ).addConfig(
                new Config(
                        new userIpKey(2, 2222),
                        new Quota(TimeUnit.SECONDS, 10, 2),
                        true,
                        StrategyEnums.SLIDING_WINDOW_STRATEGY))
        .addConfig(
                new Config(
                        new DefaultKey(3),
                        new Quota(TimeUnit.SECONDS, 10, 2),
                        true,
                        StrategyEnums.SLIDING_WINDOW_STRATEGY));

        System.out.println(rateLimiterService.isAllowed(new Request(1, 1111)));
        System.out.println(rateLimiterService.isAllowed(new Request(1, 1111)));
        System.out.println(rateLimiterService.isAllowed(new Request(1, 1111)));

        System.out.println(rateLimiterService.isAllowed(new Request(2, 2222)));
        System.out.println(rateLimiterService.isAllowed(new Request(2, 2222)));
        System.out.println(rateLimiterService.isAllowed(new Request(3, 1111)));
    }


}
