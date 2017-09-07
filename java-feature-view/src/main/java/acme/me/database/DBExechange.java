package acme.me.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DBExechange {

    public static void main(String[] args) throws SQLException, IOException {
        ApplicationContext apc = null;
        try {
            apc = new ClassPathXmlApplicationContext("spring-beans.xml");
        } catch (BeansException e) {
            e.printStackTrace();
            System.out.println("构建业务模型出错");
        }
        if (apc == null) {
            return;
        }
        DataSource dbsource = (DataSource) apc.getBean("dataSourceImp");
        Connection conn = null;
        BufferedReader bufferReader = null;
        String sqlCommand = null;
        try {
            conn = dbsource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取数据库链接错误！");
        }
        if (conn != null) {
            try {
                File sqlFile = (File) apc.getBean("sqlFile");
                if (!sqlFile.exists()) {
                    System.out.println("Can not file file ill.sql");
                    return;
                }
                InputStream fileStream = new FileInputStream(sqlFile);
                InputStreamReader streamReader = new InputStreamReader(fileStream);
                bufferReader = new BufferedReader(streamReader);
                while ((sqlCommand = bufferReader.readLine()) != null) {
                    sqlCommand = sqlCommand.substring(0, sqlCommand.lastIndexOf(";"));
                    System.out.println(sqlCommand);
                    conn.createStatement().executeUpdate(sqlCommand);
                }
                conn.commit();
            } catch (SQLException e) {
                conn.commit();
                e.printStackTrace();
                System.out.println("Error happened when executing sql");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error happened when reading file!");
            } finally {
                if (conn != null) {
                    conn.close();
                }
                if (bufferReader != null) {
                    bufferReader.close();
                }
            }
        }
        System.out.println("finished!");
    }
}
