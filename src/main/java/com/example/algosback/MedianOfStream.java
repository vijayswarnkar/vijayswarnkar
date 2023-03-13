import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class MedianOfStream {
    List<Integer> arr = new ArrayList<>();
    PriorityQueue<Integer> left = new PriorityQueue<>((a, b) -> b - a);
    PriorityQueue<Integer> right = new PriorityQueue<>();

    void add(int x) {
        arr.add(x);
        if (left.isEmpty()) {
            left.add(x);
        } else {
            if (x <= left.peek()) {
                if (left.size() > right.size()) {
                    right.add(left.remove());
                    left.add(x);
                } else {
                    left.add(x);
                }
            } else {
                right.add(x);
                if (left.size() < right.size()) {
                    left.add(right.remove());
                }
            }
        }
        System.out.println("==========");
//        System.out.println(arr);
//        System.out.println(left.peek());
//        System.out.println(right.peek());
        System.out.println(getMedian());
    }

    double getMedian() {
        if (left.isEmpty()) {
            return 0;
        }
        if (left.size() > right.size()) {
            return left.peek();
        } else {
            return (left.peek() + right.peek()) / 2.0;
        }
    }

    public static void main(String[] args) {
        MedianOfStream medianOfStream = new MedianOfStream();
        medianOfStream.add(1);
        medianOfStream.add(2);
        medianOfStream.add(4);
        medianOfStream.add(5);
        medianOfStream.add(3);
    }
}

class TestPQ {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        pq.add(1);System.out.println(pq.peek());
        pq.add(2);System.out.println(pq.peek());
        pq.add(0);System.out.println(pq.peek());
        pq.add(-1);System.out.println(pq.peek());
        pq.remove();System.out.println(pq.peek());
    }
}

class MedianOfStreamCode {
    PriorityQueue<Integer> left, right;
    MedianOfStreamCode() {
        right = new PriorityQueue<>((a, b) -> a - b);
        left = new PriorityQueue<>((a, b) -> b - a);
    }

    void add(int val){
        if(left.size() == 0) left.add(val);
        else if(val <= left.peek()) {
            if(left.size() == right.size()){
                left.add(val);
            } else {
                left.add(val);
                right.add(left.remove());
            }
        } else {
            if(left.size() == right.size()){
                right.add(val);
                left.add(right.remove());
            } else {
                right.add(val);
            }
        }
        System.out.println(left.size() + "," + right.size() + "=" + median());
    }

    double median(){
        if(left.size() == 0) return -1;
        if(left.size() > right.size()) return left.peek();
        else {
            return ((double)left.peek()+right.peek())/2;
        }
    }

    public static void main(String[] args){
        MedianOfStreamCode obj = new MedianOfStreamCode();
        obj.add(4);
        obj.add(5);
        obj.add(1);
        obj.add(2);
        obj.add(3);
        System.out.println("Test");
    }
}