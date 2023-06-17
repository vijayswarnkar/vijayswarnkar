package com.example.atlassian;
// Rate limiter service which works on cust_id{integer} - already fed to system
// isAllowed(1) - t/f

// import javafx.util.Pair;
import lombok.AllArgsConstructor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
}
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
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");
        RateLimiter rateLimiter = RateLimiterImplFactory.getInstance(Strategy.TOKEN_BUCKET_STRATEGY, List.of(
            new Config(1, TimeUnit.SECONDS, 2, 1),
            new Config(2, TimeUnit.DAYS, 2, 1)
        ));
        assertTrue(rateLimiter.isAllowed(1));
        assertFalse(rateLimiter.isAllowed(1));
        TimeUnit.SECONDS.sleep(2);
        assertTrue(rateLimiter.isAllowed(1));
        TimeUnit.SECONDS.sleep(6);
        assertTrue(rateLimiter.isAllowed(1));
        assertFalse(rateLimiter.isAllowed(1));
    }
}
/*
 * /*
Design an in memory library that facilities tagging and counting of any kind of Items.

You are given a bunch of Products with some tags, like id, size, price, category, etc. Implement a way to retrieve counts of these products based on filters, and additional grouping done on fields.

Sample product attributes:

{
	"product": "id1",
	"category": "book",
	"subCategory": "education",
	"price": 200,
	"size": 10
}
Sample Query 1:
Count of products with category=“book”, grouped by subcategory
Input:

{
	"filter": {
		"category": "book"
	}
	"groupedBy": "subCategory"
}
Output:

{
	"totalCount": 56,
	"subCategory": {
		"education":10,
		"crime": 34
		"horror": 12
	}
}
Sample Query 2:
Count of products with category=“book” and price=200, grouped by subcategory
Input:

{
	"filter": {
		"category": "book",
		"price": 200
	}
	"groupedBy": "subCategory"
}
Output:

{
	"totalCount": 7,
	"subCategory": {
		"education":2,
		"crime": 5
	}
}
Requirements P0:
Interfaces for accepting products
Interfaces for taking query, and return counts
Right models for Products, and how Queries are expressed
Requirements P1:
Index products on price range (200-500 ... 500+) and show counts based on that
Supporting complicated boolean queries with AND and OR (earlier in P0, the filter section only meant AND)
Constraints:
You are NOT allowed to use any database,
only simple in-memory data structures allowed
You are NOT allowed to use any third party libraries for search
You are NOT required to have a full-fledged web service or APIs exposed
Just a main class that simulates the above operations is enough
How you will be evaluated
Separation of concerns
Abstractions
Application of OO design principles
Testability
Code readability
Language proficiency
[execution time limit] 3 seconds (java)
 */
 */