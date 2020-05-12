package acme.me.database.transaction;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * sku扩展属性表 domain类
 *
 * @author cdchenmingxuan
 * @description
 * @since 2020/5/12 15:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-beans-transaction.xml")
@Slf4j
public class RawMultiThreadTrans {
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test1() throws InterruptedException {
        Integer size = 5;
        TransactionGroup transactionGroup = new TransactionGroup(size);

        for (int j = 0; j < size; j++) {
            final String sql = String.format("insert into transaction_view(name, age) values ('%s', %d)", "user" + j, 30);
            JoinTransRunnable runnable = new JoinTransRunnable(transactionGroup, transactionManager, jdbcTemplate) {
                @Override
                public void execute(JdbcTemplate jdbcTemplate) throws Exception {
                    jdbcTemplate.execute(sql);
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }

        Thread.sleep(5000);
    }


}
