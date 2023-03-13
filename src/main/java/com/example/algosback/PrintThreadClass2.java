import java.util.concurrent.TimeUnit;

class PrintThread2 extends Thread{
    volatile int counter = 0;

    int getNumber() {
        String n = Thread.currentThread().getName().split("-")[1];
        int x = 0;
        for (int i = 0; i < n.length(); i++) {
            x += (x * 10 + (n.charAt(i) - '0'));
        }
        return x;
    }

    synchronized void print() {
        while (counter < 100) {
            int x = getNumber();
            if(counter % 4 == x) {
                System.out.println(x + ":" + counter);
                counter++;
                try{
                    TimeUnit.MILLISECONDS.sleep(100);
                }catch (Exception ex){

                }
                notifyAll();
            } else {
                try{
                    wait();
                }catch (Exception ex){

                }
            }
        }
    }
}

public class PrintThreadClass2 {
    public static void main(String[] args) {
        try {
            PrintThread2 obj = new PrintThread2();
            new Thread(obj::print).start();
            new Thread(obj::print).start();
            new Thread(obj::print).start();
            new Thread(obj::print).start();
        } catch (Exception ex){
            System.out.println(ex);
        }
    }
}

