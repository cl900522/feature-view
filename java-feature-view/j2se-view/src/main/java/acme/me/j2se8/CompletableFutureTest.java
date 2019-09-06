package acme.me.j2se8;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @author: cdchenmingxuan
 * @date: 2019/9/6 14:00
 * @description: java-feature-view
 */
public class CompletableFutureTest {
    /**
     * thenAccept..消费结果:只需要入参结果值，没有返回
     * thenApply.. 变换结果:入参结果值，生成新的值
     * thenRun... 跟随之后调用，不会入参结果值
     */
    @Test
    public void test1() {
        //异步获取价格
        CompletableFuture<Double> t1 = CompletableFuture.supplyAsync(this::getPrice);
        t1.thenRun(() -> System.out.println("价格获取到后调用"));//跟随在获取price后执行（无法获取上一步执行结果）
        t1.thenAccept((t) -> System.out.println("原始价格是: $" + t));//跟随在获取price后处理结果
        t1.thenAcceptAsync((t) -> System.out.println("获取价格后异步输出价格: $" + t)); // 在获取价格后异步调用
        t1.thenRunAsync(() -> System.out.println("获取价格后异步调用..."));

        CompletableFuture<Double> t3 = t1.thenApplyAsync((t) -> {
            Double transedPrice = t * 7.9;
            System.out.println("转换后的价格：￥" + transedPrice);
            return transedPrice;
        });

        //异步获取折扣率
        CompletableFuture<Double> t2 = CompletableFuture.supplyAsync(this::getDiscount);
        t2.thenAcceptAsync((t) -> System.out.println("折扣比例是:" + t));

        //计算价格
        Double result = t3.thenCombine(t2, this::calculatePrice).whenComplete((v, e) -> {
            if (e != null) {
                e.printStackTrace();
            }
        }).join();
        System.out.println("最终价格: ￥" + result);

    }


    @Test
    public void test2() {
        CompletableFuture<Double> t1 = CompletableFuture.supplyAsync(this::getError);
        Double join = t1.exceptionally((t) -> {
            System.out.println("发生异常，返回无效值." + t.getMessage());
            return 0.0;
        }).join();

        System.out.println("返回值：" + join);
    }

    private Double calculatePrice(Double price, Double discount) {
        return price * discount;
    }


    public Double getPrice() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        System.out.println("返回原价...");
        return 100.0;
    }

    public Double getDiscount() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        System.out.println("返回折扣...");
        return 0.8;
    }

    public Double getError() {
        throw new RuntimeException("Not supported");
    }
}
