package acme.me.spring.transaction;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * sku扩展属性表 domain类
 *
 * @author cdchenmingxuan
 * @description
 * @since 2020/5/12 14:43
 */
@Slf4j
public class MultiThreadTrans {
    @Autowired
    private ObjectMapperDao objectMapperDao;

    @Autowired
    private PlatformTransactionManager transactionManager;

    List<TransactionStatus> transactionStatuses = Collections.synchronizedList(new ArrayList<TransactionStatus>());

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void threadMethod() {
        Object object  = new Object();
        try {
            // 使用这种方式将事务状态都放在同一个事务里面
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
            TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
            transactionStatuses.add(status);
            objectMapperDao.addObjet(object);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        System.out.println("main insert is over");
        try {
            for (int a = 0; a < 3; a++) {
                ThreadOperation threadOperation = new ThreadOperation();
                Thread innerThread = new Thread(threadOperation);
                innerThread.start();
            }
        } catch (Exception e) {
            log.error("###线程异常");
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    public class ThreadOperation implements Runnable {
        @Override
        public void run() {
            try {
                // 使用这种方式将事务状态都放在同一个事务里面
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
                TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
                transactionStatuses.add(status);
                Object object  = new Object();
                /**
                 * 1.这里如果用其他类的saveUser2方法，在这个线程内事务生效，其他线程不受影响
                 * 2.如果是用本类的方法，这个线程内的事务不生效，其他线程也不受影响
                 */
                objectMapperDao.addObjet(object);
                System.out.println("thread insert is over");
            } catch (Exception e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                //throw new RuntimeException();
                // 事务回滚不管用
                /*TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw new RuntimeException();*/
                /*for (TransactionStatus transactionStatus:transactionStatuses) {
                    transactionStatus.setRollbackOnly();
                }*/
            }
        }

    }
}
