import java.util.Arrays;

public class Bit {
    int[] arr;
    int[] bit;

    Bit(int[] arr) {
        this.arr = arr;
        bit = new int[arr.length + 1];
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(bit));
        this.init();
    }

    void init() {
        for (int i = 0; i < arr.length; i++) {
            update(arr[i], i + 1);
        }
        System.out.println(Arrays.toString(bit));
    }

    void update(int val, int idx) {
        while (idx <= arr.length) {
            bit[idx] += val;
            idx += (idx & -idx);
        }
    }

    int sum(int st, int ed) {
        return sum(ed) - sum(st + 1);
    }

    int sum(int idx) {
        int sum = 0;
        while (idx > 0) {
            sum += bit[idx];
            idx -= (idx & -idx);
        }
        return sum;
    }

    public static void main(String[] args) {
        Bit bit = new Bit(new int[]{1, 2, 3, 4, 5, 6});
        for (int i = 0; i < 6; i++) {
            System.out.println(bit.sum(i + 1));
        }
    }
}
