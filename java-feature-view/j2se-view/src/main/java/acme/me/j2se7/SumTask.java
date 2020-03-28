package acme.me.j2se7;

import java.util.concurrent.RecursiveTask;

public class SumTask extends RecursiveTask<Integer> {

    private int threshold;
    private static final int segmentation = 10;

    private int[] src;

    private int fromIndex;
    private int toIndex;

    public SumTask(int formIndex, int toIndex, int[] src) {
        this.fromIndex = formIndex;
        this.toIndex = toIndex;
        this.src = src;
        this.threshold = src.length / segmentation;
    }

    @Override
    protected Integer compute() {
        if ((toIndex - fromIndex) < threshold) {
            int count = 0;
            System.out.println(" from index = " + fromIndex
                    + " toIndex=" + toIndex);
            for (int i = fromIndex; i <= toIndex; i++) {
                count += src[i];
            }
            return count;
        } else {
            int mid = (fromIndex + toIndex) / 2;
            System.out.println("拆分任务 mid->" + mid);
            SumTask left = new SumTask(fromIndex, mid, src);
            SumTask right = new SumTask(mid + 1, toIndex, src);
            left.fork();
            right.fork();
            invokeAll(left, right);
            return left.join() + right.join();
        }
    }
}
