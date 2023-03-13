import java.util.concurrent.TimeUnit;

class PrintThread {
    volatile int counter = 0;
    int n;

    PrintThread(int n) {
        this.n = n;
    }

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
            if (counter % n == x) {
                System.out.println(x + ":" + counter);
                counter++;
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (Exception ex) {

                }
                notifyAll();
            } else {
                try {
                    wait();
                } catch (Exception ex) {

                }
            }
        }
    }
}

public class PrintThreadClass {
    public static void main(String[] args) {
        try {
            int n = 3;
            PrintThread obj = new PrintThread(n);
            for (int i = 0; i < n; i++) {
                new Thread(obj::print).start();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}

