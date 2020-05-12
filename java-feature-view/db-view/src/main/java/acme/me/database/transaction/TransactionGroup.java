package acme.me.database.transaction;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class TransactionGroup {
    Integer initSize;
    Map<Thread, TransactionState> threadTranactionStateMap = new HashMap();
    CountDownLatch countDownLatch;

    TransactionGroup(int size) {
        initSize = size;
        countDownLatch = new CountDownLatch(size);
    }

    public void joinTransActionState() {
        if (threadTranactionStateMap.containsKey(Thread.currentThread())) {
            return;
        }
        if (threadTranactionStateMap.size() > initSize) {
            throw new RuntimeException("线程数量超过了初始化的大小");
        }
        threadTranactionStateMap.put(Thread.currentThread(), new TransactionState());
    }

    public boolean isAllFinished() {
        return countDownLatch.getCount() == 0;
    }

    public synchronized void await() {
        try {
            while (!this.isAllFinished()) {
                this.wait();
            }
        } catch (Exception e) {
        }
    }

    public synchronized void finisCurrent(boolean success) {
        TransactionState state = threadTranactionStateMap.get(Thread.currentThread());
        if (!state.isFinished) {
            countDownLatch.countDown();
            state.setFinished(true);
        }
        state.setFailed(!success);
        this.notifyAll();
    }

    public boolean hasFailed() {
        if (!isAllFinished()) {
            return true;
        }
        for (TransactionState value : threadTranactionStateMap.values()) {
            if (value.isFailed) {
                return true;
            }
        }
        return false;
    }

    @Getter
    @Setter
    public class TransactionState {
        boolean isFailed = false;
        boolean isFinished = false;
        Thread thread;
    }
}
