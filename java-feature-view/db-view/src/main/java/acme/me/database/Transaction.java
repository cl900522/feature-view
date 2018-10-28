package acme.me.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Transaction {
    private static ApplicationContext applicationContex = new ClassPathXmlApplicationContext("spring-beans.xml");
    private static org.apache.log4j.Logger log4jLogger = org.apache.log4j.Logger.getLogger(Transaction.class);
    private static org.slf4j.Logger slf4jLogger = org.slf4j.LoggerFactory.getLogger(Transaction.class);

    public static void main(String[] args) throws SQLException {
        DataSource dbSource = (DataSource) applicationContex.getBean("dataSource");
        Connection connection = null;
        Statement preStatement = null;
        if (null != dbSource) {
            try {
                connection = dbSource.getConnection();
                if (connection.getAutoCommit()) {
                    connection.setAutoCommit(false);
                    log4jLogger.debug("set autocommit false");
                }
                preStatement = connection.createStatement();
                preStatement.executeUpdate("delete from SY_ADMIN");
                preStatement.executeUpdate("delete from USER");
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                slf4jLogger.error("execute sql error and try to rollback");
                connection.rollback();
            } finally {
                connection.close();
            }
        } else {
            slf4jLogger.error("Spring init failed to get datasource!");
        }
    }
}
