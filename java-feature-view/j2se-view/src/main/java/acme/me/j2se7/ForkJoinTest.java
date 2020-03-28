package acme.me.j2se7;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinTest {
    public static void main(String[] args) {
        int[] array = new Random().ints(100).toArray();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SumTask sumTask = new SumTask(0, array.length - 1, array);

        long start = System.currentTimeMillis();

        forkJoinPool.invoke(sumTask);
        System.out.println("The count is " + sumTask.join()
                + " spend time:" + (System.currentTimeMillis() - start) + "ms");
    }
}
