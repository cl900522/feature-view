package acme.me.spring.transaction;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * sku扩展属性表 domain类
 *
 * @author cdchenmingxuan
 * @description
 * @since 2020/5/12 15:33
 */
public class RawMultiThreadTrans {
    @Autowired
    private ObjectMapperDao objectMapperDao;

    @Getter
    @Setter
    private class TranactionState {
        boolean isFailed = false;
        boolean isFinished = false;
        Thread thread;
    }

    private class TransactionGroup {
        Integer initSize = 0;
        Map<Thread, TranactionState> threadTranactionStateMap = new HashMap();
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
            threadTranactionStateMap.put(Thread.currentThread(), new TranactionState());
        }

        public boolean isAllFinished() {
            return countDownLatch.getCount() == 0;
        }

        public synchronized void await() {
            try {
                if (this.isAllFinished()) {
                    return;
                }
                this.wait();
            } catch (Exception e) {
            }
        }

        public void finisCurrent(boolean success) {
            TranactionState tranactionState = threadTranactionStateMap.get(Thread.currentThread());
            if (!tranactionState.isFinished) {
                countDownLatch.countDown();
                tranactionState.setFinished(true);
            }
            tranactionState.setFailed(!success);
            this.notifyAll();
        }

        public boolean hasFailer() {
            if (!isAllFinished()) {
                return true;
            }
            for (TranactionState value : threadTranactionStateMap.values()) {
                if (value.isFailed) {
                    return true;
                }
            }
            return false;
        }
    }

    @Test
    public void test1() {
        Object o = new Object();
        objectMapperDao.addObjet(o);

        Integer size = 5;
        TransactionGroup transactionGroup = new TransactionGroup(size);

        for (int j = 0; j < size; j++) {
            JoinTransRunnalbe runnable = new JoinTransRunnalbe(transactionGroup);
            Thread thread = new Thread(runnable);
            thread.start();
        }

        transactionGroup.isAllFinished();
    }

    private class JoinTransRunnalbe implements Runnable {
        TransactionGroup transactionGroup;

        JoinTransRunnalbe(TransactionGroup transactionGroup) {
            this.transactionGroup = transactionGroup;
            this.transactionGroup.joinTransActionState();
        }

        public void run0() throws Exception {
            objectMapperDao.addObjet(new Object());
        }

        @Override
        public void run() {
            try {
                // do start transaction
                run0();
                transactionGroup.finisCurrent(true);
            } catch (Exception e) {
                transactionGroup.finisCurrent(false);
            }

            while (!transactionGroup.isAllFinished()) {
                transactionGroup.await();
            }
            if (transactionGroup.hasFailer()) {
                // do rollback trasaction
            }
        }
    }
}
