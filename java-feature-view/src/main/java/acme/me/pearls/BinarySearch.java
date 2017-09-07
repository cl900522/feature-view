package acme.me.pearls;

public class BinarySearch {
    private int[] array;

    public BinarySearch() {
    }

    public BinarySearch(int size) {
        generateArray(size);
    }

    public int[] getArrray() {
        return array;
    }

    public static void swap(int[] array, int source, int target) {
        int temp = array[source];
        array[source] = array[target];
        array[target] = temp;
    }

    private static long max(long maxSofar, long maxEndingHere) {
        if (maxSofar > maxEndingHere) {
            return maxSofar;
        } else {
            return maxEndingHere;
        }
    }

    private void generateArray(int arraySize) {
        array = new int[arraySize];
        int rangeSize = arraySize * 2;
        int[] biggerSourceArray = new int[rangeSize];
        for (int i = 0; i < biggerSourceArray.length; i++) {
            biggerSourceArray[i] = i;
        }
        for (int j = 0; j < arraySize; j++) {
            swap(biggerSourceArray, j, (int) (Math.random() * (rangeSize - j)) + j);
            if (Math.random() < 0.5) {
                array[j] = 0 - biggerSourceArray[j];
            } else {
                array[j] = biggerSourceArray[j];
            }
        }
    }

    /**
     * 使用快速排序法排序数组，由小到大
     * @param start
     * @param end
     */
    private void quickSort(int start, int end) {
        if (start < end) {
            int tempSample = array[start];
            int executeStart = start, executeEnd = end;

            while (executeStart < executeEnd) {
                while (executeStart < executeEnd && array[executeEnd] >= tempSample) {
                    executeEnd--;
                }
                if (executeStart < executeEnd) {
                    array[executeStart] = array[executeEnd];
                    executeStart++;
                }

                while (executeStart < executeEnd && array[executeStart] < tempSample) {
                    executeStart++;
                }
                if (executeStart < executeEnd) {
                    array[executeEnd] = array[executeStart];
                    executeEnd--;
                }
            }
            array[executeStart] = tempSample;
            quickSort(start, executeStart - 1);
            quickSort(executeStart + 1, end);
        }
    }

    /**
     * 使用冒泡排序法排序数组，由小到大
     * @param start
     * @param end
     */
    private void popSort(int start, int end) {
        for (int i = 0; i < end; i++) {
            for (int j = 0; j < end - i; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                }
            }
        }
    }

    /**
     * 插入排序数组array，由小到大
     * @param start
     * @param end
     */
    private void insertSort(int start, int end) {
        for (int i = 1; i < this.array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    swap(array, j, j - 1);
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 使用二分搜索对已经排序的数组搜索target值
     * @param targetValue
     * @param start
     * @param end
     * @return
     */
    private int binarySearch(int targetValue, int start, int end) {
        if (array[start] == targetValue)
            return start;
        if (array[end] == targetValue)
            return end;
        /**
         * Attention to the border condition
         */
        end--;
        if (start <= end && array[start] <= targetValue && array[end] >= targetValue) {
            int middleIndex = (start + end) / 2;
            if (targetValue <= array[middleIndex]) {
                return binarySearch(targetValue, start, middleIndex);
            } else {
                return binarySearch(targetValue, middleIndex, end);
            }
        } else {
            return -1;
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

    /**
     * 对外的排序命令接口
     */
    public void sort(SortMethod method) {
        long start = System.nanoTime();
        switch (method) {
            case POP:
                popSort(0, array.length - 1);
                break;
            case QUICK:
                quickSort(0, array.length - 1);
                break;
            case INSERT:
                insertSort(0, array.length - 1);
                break;
            default:
                break;
        }
        System.out.println("Using " + (System.nanoTime() - start) + " nanoSeconds!");
    }

    /**
     * 对外的搜索命令接口
     */
    public int search(int target) {
        return binarySearch(target, 0, array.length - 1);
    }

    /**
     * 最大连续区域累加和
     */
    public long maxsum() {
        long maxSofar = 0;
        long maxEndingHere = 0;
        for (int node : array) {
            maxEndingHere = max(maxEndingHere + node, 0);
            maxSofar = max(maxSofar, maxEndingHere);
        }
        return maxSofar;
    }

    public static void main(String[] args) {
        BinarySearch sample = new BinarySearch(200);
        sample.print();
        sample.sort(SortMethod.INSERT);
        sample.print();
        System.out.println("12 in the array index is:" + sample.search(12));
        sample.generateArray(10);
        sample.print();
        System.out.println("Max range sum is:" + sample.maxsum());
    }
}
