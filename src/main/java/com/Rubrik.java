import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

@Data
class AtomicObject {
    AtomicInteger atomicInteger = new AtomicInteger();

    void increment() {
        while(true) {
            int current = atomicInteger.get();
            int newValue = current + 1;
            if (atomicInteger.compareAndSet(current, newValue)) {
//            System.out.println("updated successfully from " + current + "->" + atomicInteger.get());
                break;
            } else {
                System.out.println("failed");
            }
        }
    }
}

@Data
class AtomicObject1 {
    int x = 0;

    void increment() {
        x++;
        System.out.println("updated successfully from " + (x-1) + "->" + (x));
    }
}

@AllArgsConstructor
class Runner implements Runnable {
    AtomicObject object;

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            object.increment();
        }
    }
}

public class Rubrik {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        AtomicObject object = new AtomicObject();
        new Thread(new Runner(object)).start();
        new Thread(new Runner(object)).start();
        new Thread(new Runner(object)).start();
        new Thread(new Runner(object)).start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(object.atomicInteger);
            }
        }, 2000);
    }
}
