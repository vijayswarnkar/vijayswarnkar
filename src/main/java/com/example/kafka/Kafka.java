package com.example.kafka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

public interface Kafka {
    Topic createTopic(TopicRequest topicRequest);

    void subscribeTopic(Consumer consumer);
}

interface Topic {
    void produce(Message message, Key key);

    Message consume(Key key);
}

@ToString
class PartitionManager<K> {
    Map<K, Partition<K>> partitionMap = new HashMap<>();
    
    void putMessage(Message message, K keyHash) {
        Partition<K> partition = partitionMap.getOrDefault(keyHash, new Partition<K>());
        partition.putMessage(message);
    }

    Message getMessage(K keyHash) {
        if(partitionMap.containsKey(keyHash)){
            return partitionMap.get(keyHash).getMessage(keyHash);
        } else {
            return null;
        }
    }

}

@AllArgsConstructor
class BaseTopic implements Topic {
    TopicRequest topicRequest;
    PartitionManager<String> partitionManager;

    @Override
    public void produce(Message message, Key key) {
        System.out.println(message.toString());
        partitionManager.putMessage(message, Key.getHash(key));
    }

    @Override
    public Message consume(Key key) {
        System.out.println(partitionManager);
        return partitionManager.getMessage(Key.getHash(key));
    }
}

@AllArgsConstructor
@Data
class TopicId {
    String name;

    public static TopicId getId(TopicRequest topicRequest) {
        return new TopicId(topicRequest.name);
    }
}

@Data
class TopicRequest {
    String name;
    TopicId topicId;
    int replicationFactor;

    TopicRequest(String name) {
        this.topicId = TopicId.getId(this);
    }
}

@ToString
@AllArgsConstructor
class KeyHash {
    String string;
}

@ToString
@AllArgsConstructor
class Key {
    List<Object> keys;

    public static String getHash(Key key) {
        if (key == null)
            return "null";
        String hash = "";
        for (Object obj : key.keys) {
            hash = obj.toString();
        }
        return hash;
    }
}

class Partition<K> {
    // Storage storage;
    List<Message> list = new ArrayList<>();
    
    void putMessage(Message message) {
        synchronized(list){
            list.add(message);
        }
    }

    Message getMessage(K keyHash) {
        synchronized(list){
            if (!list.isEmpty()) {
                return list.remove(0);
            }
            return null;    
        }
    }
}

@AllArgsConstructor
class Message {
    String string;
    Key partitionKey;

    @Override
    public String toString() {
        return string;
    }
}

interface Storage {

}

class BaseStorage implements Storage {

}

class Consumer {

}

@ToString
class TopicManager {
    Map<TopicId, Topic> map = new HashMap<>();

    Topic getTopic(TopicRequest topicRequest) {
        return map.getOrDefault(topicRequest.topicId, null);
    }

    Topic createTopic(TopicRequest topicRequest) {
        map.put(topicRequest.topicId, new BaseTopic(topicRequest, new PartitionManager<String>()));
        return getTopic(topicRequest);
    }
}

@Builder
class Config {
    int replicationFactor;
    int maxTopics;
    int minTimeToFlushToDisk;
    int writeAheadLogSize;
}

class BaseKafkaImpl implements Kafka {
    TopicManager topicManager;
    Config config;
    private static Kafka kafka;

    private BaseKafkaImpl(Config config) {
        this.topicManager = new TopicManager();
        this.config = config;
    }

    public static Kafka getInstance(Config config) {
        if (kafka == null) {
            kafka = new BaseKafkaImpl(config);
        }
        return kafka;
    }

    @Override
    public Topic createTopic(TopicRequest topicRequest) {
        return topicManager.createTopic(topicRequest);
    }

    @Override
    public void subscribeTopic(Consumer consumer) {
        throw new UnsupportedOperationException("Unimplemented method 'subscribeTopic'");
    }

}

@AllArgsConstructor
class Runner implements Runnable {
    Runnable runnable;
    String name;

    @Override
    public void run() {
        while (true) {
            System.out.println(name + " running");
            runnable.run();
        }
    }
}

class KafkaMainRunner {
    public static void main(String[] args) {
        System.out.println("KafkaMainRunner.main()");
        Kafka kafka = BaseKafkaImpl.getInstance(Config.builder().build());
        Topic topic = kafka.createTopic(new TopicRequest("topic1"));
        Runnable producer = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int randomNumber = new Random().nextInt();
            topic.produce(new Message("Message" + randomNumber, null), null);
        };
        Runnable consumer = () -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(topic.consume(null));
        };
        Runner c1 = new Runner(consumer, "consumer1");
        Runner c2 = new Runner(consumer, "consumer2");
        Runner p = new Runner(producer, "producer1");
        new Thread(p).start();
        new Thread(c1).start();
        new Thread(c2).start();
        System.out.println(kafka);
    }
}
