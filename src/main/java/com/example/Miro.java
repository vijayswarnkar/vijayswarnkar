// Storage - per topic wise
// producer
// consumer - consume

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Topic {
    String name;
}

@AllArgsConstructor
class Content {
    String string;
}

@AllArgsConstructor
class Message {
    int id;
    Content content;
    List<Topic> topics;
}
interface Storage {
    void put(Message message);
    Message get(List<Topic> topics);
    Message get();
}
class Link {
    Topic A;
    Topic B;
}
/*

A-B-C-D
|
E-F-G-H

 */
class LinkManager {
    Set<Topic> getTopicsForAConsumer(){
        return Set.of();
    }

    void addLink(Link link){

    }
}
class ConsumerMetaData {
    int topicsLength;
    int lastAccessedTopicIndex;
    Map<Integer, Integer> topicIndexMap;
}

@AllArgsConstructor
class BaseStorage implements Storage {
    Map<Topic, List<Integer>> messages;
    Map<Integer, Message> messageMap;
    Set<Topic> topics;
    LinkManager linkManager;

    void updateStorage(Topic topic, Message message){
        synchronized (topic){
            topics.add(topic);
            List<Integer> ids = messages.getOrDefault(topic, new ArrayList<>());
            ids.add(message.id);
            messages.put(topic, ids);
            messageMap.put(message.id, message);
            notifyAll();
        }
    }

    @Override
    public void put(Message message) {
        List<Topic> topics = message.topics;
        for(Topic topic: topics){
            updateStorage(topic, message);
        }
    }

    @Override
    public Message get(List<Topic> topics) {
        return null;
    }

    @Override
    public Message get() {
        return null;
    }
}
interface Consumer {
    Message consume();
}

class ConsumerManager {
    Storage storage;
    Map<Consumer, ConsumerMetaData> map;
    Message fetch(Consumer consumer){
        return null;
    }
}

class BaseConsumer implements Consumer {
    ConsumerManager consumerManager;

    BaseConsumer(List<Topic> topics){

    }

    BaseConsumer(){

    }

    void updateMetaData(){

    }
    @Override
    public Message consume() {
        Message message = consumerManager.fetch(this);
        return message;
    }
}

class ConsumerRunner implements Runnable {

    Topic topic;
    @SneakyThrows
    @Override
    public void run() {
        while(true) {
            synchronized (topic){
                if(true){ // this is empty
                    wait();
                }
            }
        }
    }
}
interface Producer {
    void produce(Message message);
}

class BaseProducer implements Producer {

    @Override
    public void produce(Message message) {

    }
}

public class Miro {
    public static void main(String[] args) {
        System.out.println("Miro.main");
    }
}
