package acme.me.orm;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.log4j.Logger;

import acme.me.orm.bean.Account;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class IbatisView {
    private static Logger logger = Logger.getLogger(IbatisView.class);
    public static void main(String[] args) throws SQLException{
        String resource ="sqlMapConfig.xml";
        Reader reader;
        SqlMapClient sqlMap = null;
        try {
            reader = Resources.getResourceAsReader(resource);
            sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
            sqlMap.startTransaction();
            Account account = new Account();
            account.setName("Erica");
            account.setSex("m");
            sqlMap.insert(account.getClass().getName()+".insertAccount",account);
            sqlMap.commitTransaction();
            logger.error("Insert successfully");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (SQLException e) {
            logger.error(e.getMessage());
            sqlMap.endTransaction();
        }
    }
}
