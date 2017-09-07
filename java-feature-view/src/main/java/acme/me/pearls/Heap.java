package acme.me.pearls;

public class Heap {

    private int[] array;
    private int length;

    public Heap(int size) {
        array = new int[size + 1];
        length = 0;
    }

    /**
     * 插入值
     * @param x
     */
    public void insert(int x) {
        length++;
        array[length] = x;
        shiftUp(length);
    }

    /**
     * 取最小
     * @return
     * @throws Exception
     */
    public int extractMin() throws Exception {
        if (length > 0) {
            int result = array[1];
            array[1] = array[length];
            length--;
            shiftDown(1);
            return result;
        } else {
            throw new Exception("No nodes now!");
        }
    }

    /**
     * 上移node
     * @param n
     */
    private void shiftUp(int n) {
        while (n > 1) {
            int i = n / 2;
            if (array[n] < array[i]) {
                BinarySearch.swap(array, n, i);
            } else {
                break;
            }
            n = i;
        }
    }

    /**
     * 下移node
     * @param n
     */
    private void shiftDown(int k) {
        int n = k;
        while (2 * n <= length) {
            int i = 2 * n;
            /**
             * 找出2n+1和2n中较大的
             */
            if (i + 1 <= length && array[i] > array[i + 1]) {
                i++;
            }
            if (array[n] <= array[i]) {
                break;
            }
            BinarySearch.swap(array, n, i);
            n = i;
        }
    }

    /**
     * 对外的打印命令接口
     */
    public void print() {
        for (int node : array) {
            System.out.print(node + " ");
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        BinarySearch bs = new BinarySearch(10);
        int[] m = bs.getArrray();
        bs.print();
        Heap heap = new Heap(10);
        for (int i : m) {
            heap.insert(i);
        }
        heap.print();
        try {
            while (true) {
                System.out.print(heap.extractMin() + " ");
            }
        } catch (Exception e) {
            System.out.println("");
            e.printStackTrace();
        }
    }
}
