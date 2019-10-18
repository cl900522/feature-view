package acme.me.cache.guava;

import com.google.common.cache.*;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuavaCacheTest {

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Test
    public void test1() throws InterruptedException, ExecutionException {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        cacheBuilder.initialCapacity(100).refreshAfterWrite(Duration.ofSeconds(1L));
        cacheBuilder.recordStats().maximumSize(1000);
        cacheBuilder.removalListener(new RemovalListener<Object, Object>() {
            @Override
            public void onRemoval(RemovalNotification<Object, Object> removalNotification) {
                System.out.println("Delete key: " + removalNotification.getKey());
            }
        });

        LoadingCache<String, String> cache = cacheBuilder.build(new CacheLoader<String, String>() {
            @Override
            public String load(String s) throws Exception {
                return s + " value";
            }

            @Override
            public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
                ListenableFutureTask<String> futureTask = ListenableFutureTask.create(() -> {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("Reload Value for " + i + " second(s)");
                        Thread.currentThread().sleep(1000);
                    }
                    return key + " reload value";
                });
                executorService.execute(futureTask);
                return futureTask;
            }
        });

        System.out.println(cache.getIfPresent("word"));
        System.out.println(cache.get("word"));

        Thread.sleep(5000L);

        System.out.println(cache.getIfPresent("word"));
    }

    @Test
    public void test2() throws InterruptedException {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        cacheBuilder.initialCapacity(100);
        cacheBuilder.recordStats().maximumSize(1000);
        cacheBuilder.removalListener(new RemovalListener<Object, Object>() {
            @Override
            public void onRemoval(RemovalNotification<Object, Object> removalNotification) {
                System.out.println("Delete key: " + removalNotification.getKey());
            }
        });

        Cache<String, String> cache = cacheBuilder.build();
        cache.put("word", "Hello Guava Cache");
        System.out.println(cache.getIfPresent("word"));

        cache.invalidateAll();
        System.out.println(cache.getIfPresent("word"));
    }

    @Test
    public void test3() throws InterruptedException, ExecutionException {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        cacheBuilder.initialCapacity(100);
        cacheBuilder.recordStats().maximumSize(1000);
        cacheBuilder.removalListener(new RemovalListener<Object, Object>() {
            @Override
            public void onRemoval(RemovalNotification<Object, Object> removalNotification) {
                System.out.println("Delete key: " + removalNotification.getKey());
            }
        });

        Cache<String, String> cache = cacheBuilder.build();
        for (int i = 0; i < 2; i++) {
            String smile = cache.get("Smile", new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println("Do call...");
                    return "Great";
                }
            });

            System.out.println(smile);
        }
    }

    @Test
    public void test4() {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        Cache<Object, Object> build = cacheBuilder.build();
        build.stats().averageLoadPenalty();
        build.stats().evictionCount();
        build.stats().hitCount();
        build.stats().hitRate();
        build.stats().missRate();
        build.stats().requestCount();
        build.stats().loadExceptionRate();
        build.stats().loadSuccessCount();
    }


}
