import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

class PrintInOrder {
    int CURRENT_NUMBER = 0;
    ReentrantLock lock = new ReentrantLock();

     synchronized void printer() throws InterruptedException {
        while (true) {
//            lock.lock();
            int id = Integer.parseInt(Thread.currentThread().getName().split("-")[1]);
            if (id == CURRENT_NUMBER) {
                System.out.println("ID:" + id + "-->" + CURRENT_NUMBER);
                CURRENT_NUMBER++;
//                lock.unlock();
                notifyAll();
                break;
            } else {
//                lock.unlock();
                wait();
            }
        }
    }
}

public class Test2023 {
    public static void main(String[] args) throws InterruptedException {
        int k = 5;
        PrintInOrder printInOrder = new PrintInOrder();
        Thread[] threads = new Thread[k];
        for (int i = 0; i < k; i++) {
            threads[i] = new Thread(() -> {
                try {
//                    printInOrder.printer();
                    new PrintInOrder().printer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Arrays.stream(threads).forEach(Thread::start);
        System.out.println("All done");
    }
}
class ObjectLevelLockTest implements Runnable {
    @Override
    public void run() {
        objectLock();
    }
    synchronized public void objectLock() {
        System.out.println(Thread.currentThread().getName());
//        synchronized(this) {
//            System.out.println("Synchronized block " + Thread.currentThread().getName());
//            System.out.println("Synchronized block " + Thread.currentThread().getName() + " end");
//        }
//        synchronized(ObjectLevelLockTest.class) {
//            System.out.println("Synchronized class block " + Thread.currentThread().getName());
//            System.out.println("Synchronized class block " + Thread.currentThread().getName() + " end");
//        }
        System.out.println("Synchronized class block " + Thread.currentThread().getName());
        System.out.println("Synchronized class block " + Thread.currentThread().getName() + " end");
    }
    public static void main(String[] args) {
        ObjectLevelLockTest test1 = new ObjectLevelLockTest();
        Thread t1 = new Thread(test1);
        Thread t2 = new Thread(test1);
        ObjectLevelLockTest test2 = new ObjectLevelLockTest();
        Thread t3 = new Thread(test2);
        t1.setName("t1");
        t2.setName("t2");
        t3.setName("t3");
        t1.start();
        t2.start();
        t3.start();
    }
}