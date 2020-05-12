package acme.me.database.transaction;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public abstract class JoinTransRunnable implements Runnable {
    TransactionGroup transactionGroup;
    PlatformTransactionManager transactionManager;
    TransactionStatus transactionStatus;
    JdbcTemplate jdbcTemplate;

    JoinTransRunnable(TransactionGroup transactionGroup, PlatformTransactionManager transactionManager, JdbcTemplate jdbcTemplate) {
        this.transactionGroup = transactionGroup;
        this.transactionManager = transactionManager;
        this.jdbcTemplate = jdbcTemplate;
    }


    public abstract void execute(JdbcTemplate jdbcTemplate) throws Exception;

    @Override
    public void run() {
        this.transactionGroup.joinTransActionState();

        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            transactionStatus = transactionManager.getTransaction(def);

            execute(jdbcTemplate);

            transactionGroup.finisCurrent(true);
        } catch (Exception e) {
            transactionGroup.finisCurrent(false);
        }

        transactionGroup.await();

        if (transactionGroup.hasFailed()) {
            transactionManager.rollback(transactionStatus);
        } else {
            transactionManager.commit(transactionStatus);
        }
    }
}
