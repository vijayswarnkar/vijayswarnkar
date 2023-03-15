package com.example.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.Pair;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public interface Kafka {
    Topic createTopic(TopicRequest topicRequest);
    void subscribeTopic(Consumer consumer);
}
interface Topic{
    void produce(Message message);
    void consume();
}
@AllArgsConstructor
class BaseTopic implements Topic {
    TopicRequest topicRequest;
    @Override
    public void produce(Message message) {
    }

    @Override
    public void consume() {
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
    TopicRequest(String name){
        this.topicId = TopicId.getId(this);
    }
}
@AllArgsConstructor
class Key {
    List<Object> keys;
    String getHash(){
        String hash = "";
        for(Object obj: keys){
            hash = obj.toString();
        }
        return hash;
    }
}
@AllArgsConstructor
class Partition {
    Key key;
    Storage storage;
}
@Builder
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
@AllArgsConstructor
class PartitionManager {
    Map<Pair<TopicId, Key>, Message> data = new HashMap<>();
}
@AllArgsConstructor
abstract class BaseTopicImpl implements Topic{
    PartitionManager partitionManager;


    void publishMessage(Message message){
        long hash = message.partitionKey.hashCode();
        System.out.println(hash + "->" + message);
    }

    @Override
    public void produce(Message message) {
        
    }

    @Override
    public void consume() {
        
    }

}
class Consumer {

}
class TopicManager {   
    Map<TopicId, Topic> map = new HashMap<>(); 
    Topic getTopic(TopicRequest topicRequest) {
        return map.getOrDefault(topicRequest.topicId, null);
    }
    Topic createTopic(TopicRequest topicRequest) {
        map.put(topicRequest.topicId, new BaseTopic(topicRequest));
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
class BaseKafkaImpl implements Kafka{
    TopicManager topicManager;
    Config config;
    private static Kafka kafka;

    private BaseKafkaImpl(Config config){
        this.topicManager = new TopicManager();
        this.config = config;
    }
    public static Kafka getInstance(Config config){
        if(kafka == null) {
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
class KafkaMainRunner {
    public static void main(String[] args) {
        System.out.println("KafkaMainRunner.main()");
        Kafka kafka = BaseKafkaImpl.getInstance(Config.builder().build());
        kafka.createTopic(new TopicRequest("topic1"));
        System.out.println(kafka);
    }
}
