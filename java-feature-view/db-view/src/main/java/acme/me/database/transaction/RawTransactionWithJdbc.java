package acme.me.database.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-beans-datasource.xml")
@Slf4j
/**
 * 基于jdbc实现的简单事务控制
 */
public class RawTransactionWithJdbc {

    @Autowired
    private DataSource dbSource;

    @Test
    public void test1() throws SQLException {
        Connection connection = null;
        Statement preStatement = null;
        try {
            connection = dbSource.getConnection();
            if (connection.getAutoCommit()) {
                connection.setAutoCommit(false);
                log.debug("set autocommit false");
            }
            preStatement = connection.createStatement();
            preStatement.executeUpdate("delete from SY_ADMIN");
            preStatement.executeUpdate("delete from USER");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("execute sql error and try to rollback");
            connection.rollback();
        } finally {
            connection.close();
        }
    }
}
