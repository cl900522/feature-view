package acme.me.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class RateLimitTest {
    @Test
    public void test1() {
        RateLimiter rateLimiter = RateLimiter.create(200.0);
        rateLimiter.acquire();
        rateLimiter.acquire(200);
        rateLimiter.tryAcquire();
    }

    @Test
    public void test2() {
        RateLimiter rateLimiter = RateLimiter.create(200.0);
        rateLimiter.tryAcquire();
        rateLimiter.tryAcquire(100, 1, TimeUnit.SECONDS);
    }
}
