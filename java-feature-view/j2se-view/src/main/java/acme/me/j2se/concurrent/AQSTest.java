package acme.me.j2se.concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

/**
 * @author: cdchenmingxuan
 * @date: 2019/8/20 10:40
 * @description: java-feature-view
 */
public class AQSTest {
    @Test
    public void test1() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Condition condition2 = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("begin wait");
                condition.await();
                System.out.println("end wait");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }).start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("begin wait2");
                condition2.await();
                System.out.println("end wait2");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }).start();
        Thread.sleep(1000);

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("begin signal");
                condition.signal();
                System.out.println("end signal");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        Thread.sleep(100);
    }

    @Test
    public void test2() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(true);
        for (int i = 0; i < 10; i++) {
            final Integer t = i;
            new Thread(() -> {
                lock.lock();
                System.out.println("lock " + t);
                if (t == 0) {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                    }
                }
                System.out.println("unlock " + t);
                lock.unlock();
            }).start();
        }

        Thread.sleep(2000L);
    }


    @Test
    public void test3() throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        List<String> users = new ArrayList<>();

        new Thread(() -> {
            ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
            writeLock.lock();
            System.out.println("write lock in");
            users.add("u1");
            users.add("u2");
            users.add("u3");
            users.add("u4");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println("write lock out");
            writeLock.unlock();
        }).start();

        new Thread(() -> {
            ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
            readLock.lock();
            System.out.println("read lock in");
            System.out.println(users);
            readLock.unlock();
            System.out.println("read lock out");
        }).start();

        Thread.sleep(2000);
    }

    @Test
    public void test4() {
        Point point = new Point();
        point.move(1.0, 2.0);
        System.out.println(point.distanceFromOrigin());
    }

    /**
     * StampLock
     * <p>
     * 乐观读锁tryOptimisticRead: 它是相对于悲观锁来说的，在操作数据前并没有通过
     * CAS 设置锁的状态，仅仅通过位运算测试。如果当前没有线程持有写锁，则简单
     * 地返回一个非0 的stamp 版本信息。获取该stamp 后在具体操作数据前还需要调用
     * validate方法验证该stamp是否己经不可用，也就是看当调用tryOptimisticRead 返回
     * stamp 后到当前时间期间是否有其他线程持有了写锁，如果是则validate会返回0,
     * 否则就可以使用该stamp 版本的锁对数据进行操作。
     * <p>
     * 由于tryOptimisticRead 并没有使用CAS 设置锁状态，所以不需要显式地释放该锁。该锁的一个特点是适用于读
     * 多写少的场景， 因为获取读锁只是使用位操作进行检验，不涉及CAS 操作，所以效率会高很多
     * <p>
     * 但是同时由于没有使用真正的锁，在保证数据一致性上需要复制一份要操作的变量到方法栈。
     * <p>
     * StampedLock 的读写锁都是不可重入锁，所以在获取锁后释放锁前不应该再调
     * 用会获取锁的操作，以避免造成调用线程被阻塞。
     */
    private class Point {
        private double x, y;
        private final StampedLock sl = new StampedLock();

        void move(Double deltaX, Double deltaY) {
            long stamp = sl.writeLock();
            try {
                x += deltaX;
                y += deltaY;
            } finally {
                sl.unlockWrite(stamp);
            }
        }

        double distanceFromOrigin() {
            long stamp = sl.tryOptimisticRead();
            double currentX = x, currentY = y;
            if (!sl.validate(stamp)) {
                stamp = sl.readLock();
                try {
                    currentX = x;
                    currentY = y;
                } finally {
                    sl.unlockRead(stamp);
                }
            }
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }

        void moveIfAtOrigin(double newX, double newY) {
            long stamp = sl.readLock();
            try {
                while (x == 0.0 && y == 0.0) {
                    long ws = sl.tryConvertToWriteLock(stamp);
                    if (ws != 0L) {
                        stamp = ws;
                        x = newX;
                        y = newY;
                        break;
                    } else {
                        sl.unlockRead(stamp);
                        stamp = sl.writeLock();
                    }
                }
            } finally {
                sl.unlock(stamp);
            }
        }
    }
}
