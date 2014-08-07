package acme.me.pearls;

public class BinarySearch {
    private static void swap(int[] array, int source, int target) {
        int temp = array[source];
        array[source] = array[target];
        array[target] = temp;
    }

    private static int[] generateSortedIntArray(int arraySize, int rangeSize) {
        int[] resultArray = new int[arraySize];
        int[] biggerSourceArray = new int[rangeSize];
        for (int i = 0; i < biggerSourceArray.length; i++) {
            biggerSourceArray[i] = i;
        }
        for (int j = 0; j < arraySize; j++) {
            swap(biggerSourceArray, j, (int) (Math.random() * (rangeSize - j)) + j);
            resultArray[j] = biggerSourceArray[j];
        }

        return resultArray;
    }

    private static void quickSort(int[] source, int start, int end) {
        if (start < end) {
            int tempSample = source[start];
            int executeStart = start, executeEnd = end;

            while (executeStart < executeEnd) {
                while (executeStart < executeEnd && source[executeEnd] >= tempSample) {
                    executeEnd--;
                }
                if (executeStart < executeEnd) {
                    source[executeStart] = source[executeEnd];
                    executeStart++;
                }

                while (executeStart < executeEnd && source[executeStart] < tempSample) {
                    executeStart++;
                }
                if (executeStart < executeEnd) {
                    source[executeEnd] = source[executeStart];
                    executeEnd--;
                }
            }
            source[executeStart] = tempSample;
            quickSort(source, start, executeStart - 1);
            quickSort(source, executeStart + 1, end);
        }
    }

    private static int search(int[] array, int targetValue, int start, int end) {
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
                return search(array, targetValue, start, middleIndex);
            } else {
                return search(array, targetValue, middleIndex, end);
            }
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        int[] array = generateSortedIntArray(10, 20);
        quickSort(array, 0, array.length - 1);
        System.out.println("12 in the array index is:" + search(array, 12, 0, array.length - 1));
    }
}
