package com.clinix.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;

import com.clinix.bean.HistoryRecord;
import com.clinix.bean.Reply;
import com.clinix.bean.Subject;
import com.clinix.bean.Version;
import com.clinix.dao.AskMobileDao;

public class AskMobileDaoImpl implements AskMobileDao {
    private BasicDataSource dataSource;
    private static AskMobileDao instance;

    public static AskMobileDao instance() {
        if (instance == null) {
            instance = new AskMobileDaoImpl();
        }
        return instance;
    }

    private AskMobileDaoImpl() {
        try {
            /**
             * 数据库连接池基本参数
             */
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            dataSource.setUsername("mxuan");
            dataSource.setPassword("83792371");
            dataSource.setUrl("jdbc:sqlserver://183.61.183.135:1433;DatabaseName=ClinixoftAndroid");
            /**
             * 数据库连接属性
             */
            dataSource.setInitialSize(5);// 初始化连接池时,创建连接个数
            dataSource.setMinIdle(10);// 最小空闲连接数
            dataSource.setMaxIdle(100);// 最大空闲连接数
            dataSource.setMaxActive(100);// 连接池最大并发容量

            /**
             * 可选测试参数,数据连接检测使用,网上常说的BUG,就是因为没设置下面属性引起的CONNECTION失效.
             */
            dataSource.setValidationQuery("select count(*) from cav");
            dataSource.setTestOnBorrow(true);
            dataSource.setTestOnReturn(true);
            dataSource.setTestWhileIdle(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将使用DataSource管理链接并获取连接
     * @return
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public Version getLatestVersion() throws Exception {
        Version version = new Version();
        Connection connection = getConnection();
        try {
            String querySql = "select cavid,cavnumber,cavname,cavcontent,cavurl,cavallow,cavstate,cavtipdate,cavbuilddate from cav order by cavid";
            Statement state = connection.createStatement();
            ResultSet resultSet = state.executeQuery(querySql);
            if (resultSet.next()) {
                version.id = resultSet.getLong(1);
                version.version = resultSet.getString(2);
                version.versionName = resultSet.getString(3);
                version.updateInfo = resultSet.getString(4);
                version.downloadUrl = resultSet.getString(5);

                version.compatibleVersion = resultSet.getString(6);
                version.state = (resultSet.getInt(7) == 1);
                version.remindFrequency = resultSet.getInt(8);
                version.releaseDate = resultSet.getTimestamp(9);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return version;
    }

    @Override
    public String saveHistoryRecord(HistoryRecord history) throws Exception {
        Connection connection = this.getConnection();
        connection.setAutoCommit(false);
        String insertRowId;
        try {
            String sql = "insert into cch (cchnumber,cchsex,cchage,cchzhu,cchqi,cchfu,cchji,cchke,cchip,cchgps,cchdate,cchagent) values (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement state = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            state.setString(1, history.number);
            state.setString(2, history.sex);
            state.setInt(3, history.age);
            state.setString(4, history.zhu);
            state.setString(5, history.qi);
            state.setString(6, history.fu);
            state.setString(7, history.ji);
            state.setString(8, history.ke);
            state.setString(9, history.ip);
            state.setString(10, history.gps);
            state.setTimestamp(11, new Timestamp(history.date.getTime()));
            state.setString(12, history.agent);
            state.executeUpdate();

            connection.commit();
            ResultSet resutlSet = state.getGeneratedKeys();
            if (!resutlSet.next()) {
                throw new Exception("Fail to get inserted result id");
            }
            final int CUSTOMER_ID_COLUMN_INDEX = 1;
            insertRowId = resutlSet.getInt(CUSTOMER_ID_COLUMN_INDEX) + "";

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new Exception("Fail to save history records");
        } finally {
            connection.close();
        }
        return insertRowId;
    }

    @Override
    public String saveSubject(Subject subject) throws Exception {
        Connection connection = this.getConnection();
        connection.setAutoCommit(false);
        String insertRowId;
        try {
            String sql = "insert into ccs (curid,curname,ccsdepart,cchid,ccstag,ccsscore,ccscontent,ccsdate,ccstemplate,ccsover,ccsnewtip,ccslastdate,ccsruid,ccsruname) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement state = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            state.setLong(1, subject.askerId);
            state.setString(2, "");
            if (subject.dept == null) {
                state.setString(3, "");
            } else {
                state.setString(3, subject.dept);
            }
            state.setLong(4, subject.historyId);
            state.setString(5, "");
            state.setFloat(6, subject.score);
            state.setString(7, subject.content);
            state.setTimestamp(8, new Timestamp(subject.submitDate.getTime()));
            state.setInt(9, subject.isTemplate ? 1 : 0);
            state.setInt(10, subject.isOver ? 1 : 0);
            state.setInt(11, subject.hasNewReply ? 1 : 0);
            state.setTimestamp(12, new Timestamp(subject.submitDate.getTime()));
            state.setLong(13, subject.askerId);
            state.setString(14, "");
            state.executeUpdate();

            connection.commit();
            ResultSet resutlSet = state.getGeneratedKeys();
            if (!resutlSet.next()) {
                throw new Exception("Fail to get question result id");
            }
            final int CUSTOMER_ID_COLUMN_INDEX = 1;
            insertRowId = resutlSet.getInt(CUSTOMER_ID_COLUMN_INDEX) + "";

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new Exception("Fail to save history records");
        } finally {
            connection.close();
        }
        return insertRowId;
    }

    @Override
    public List<Subject> getSubjectList(Map<String, Object> paramMap) throws Exception {
        Connection connection = this.getConnection();
        List<Subject> subjects = new ArrayList<Subject>();
        try {
            String querySql = "select ccsid,curid,curname,ccsdepart,cchid,ccstag,ccsscore,ccscontent,ccsdate,ccslastdate,ccsruid,ccsruname,ccstemplate,ccsover,ccsnewtip from ccs where 1=1";

            String userId = (String) paramMap.get("userId");
            Boolean isOver = (Boolean) paramMap.get("isOver");
            if (isOver) {
                querySql += " and ccsover=1 and curid=" + userId;
            } else {
                querySql += " and ccsover=0 and curid=" + userId;
            }

            Statement state = connection.createStatement();
            ResultSet resultSet = state.executeQuery(querySql);
            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.id = resultSet.getLong(1);
                subject.askerId = resultSet.getLong(2);
                subject.dept = resultSet.getString(4);
                subject.historyId = resultSet.getLong(5);
                subject.tag = resultSet.getString(6);
                subject.score = resultSet.getFloat(7);
                subject.content = resultSet.getString(8);
                subject.submitDate = resultSet.getTimestamp(9);
                Object object = resultSet.getObject(10);
                if (object == null) {
                    subject.lastReplyDate = subject.submitDate;
                } else {
                    subject.lastReplyDate = resultSet.getTimestamp(10);
                }
                if (object == null) {
                    subject.lastReplyUserId = -1L;
                } else {
                    subject.lastReplyUserId = resultSet.getLong(11);
                }

                subjects.add(subject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Fail to get subject records");
        } finally {
            connection.close();
        }
        return subjects;
    }

    @Override
    public List<Reply> getReplyListBySubject(Map<String, Object> paramMap) throws Exception {
        Connection connection = this.getConnection();
        List<Reply> replys = new ArrayList<Reply>();
        try {
            String subjectId = (String) paramMap.get("subjectId");
            String lastDate = (String) paramMap.get("lastDate");

            String querySql = "select ccrid,ccsid,curid,curname,ccrcontent,ccrdate,ccrscore,ccrstate from ccr where 1=1 and ccsid=" + subjectId + " and ccrdate >= '" + lastDate + "'";
            Statement state = connection.createStatement();
            ResultSet resultSet = state.executeQuery(querySql);
            while (resultSet.next()) {
                Reply reply = new Reply();
                reply.id = resultSet.getLong(1);
                reply.questionId = resultSet.getLong(2);
                reply.replyerId = resultSet.getLong(3);
                reply.content = resultSet.getString(5);
                reply.replyDate = resultSet.getTimestamp(6);
                reply.score = resultSet.getFloat(7);
                reply.isRead = (resultSet.getInt(8) == 1);

                replys.add(reply);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Fail to get replys");
        } finally {
            connection.close();
        }
        return replys;
    }

    @Override
    public String saveReply(Reply reply) throws Exception {
        Connection connection = this.getConnection();
        connection.setAutoCommit(false);
        String insertRowId;
        try {
            String sql = "insert into ccr (ccsid,curid,curname,ccrcontent,ccrdate,ccrscore,ccrstate) values (?,?,?,?,?,?,?)";
            PreparedStatement state = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            state.setLong(1, reply.questionId);
            state.setLong(2, reply.replyerId);
            state.setString(3, "");
            state.setString(4, reply.content);
            state.setTimestamp(5, new Timestamp(reply.replyDate.getTime()));
            state.setFloat(6, reply.score);
            state.setInt(7, reply.isRead ? 1 : 0);
            state.executeUpdate();

            connection.commit();
            ResultSet resutlSet = state.getGeneratedKeys();
            if (!resutlSet.next()) {
                throw new Exception("Fail to get inserted reply id");
            }
            final int CUSTOMER_ID_COLUMN_INDEX = 1;
            insertRowId = resutlSet.getInt(CUSTOMER_ID_COLUMN_INDEX) + "";

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new Exception("Fail to save history records");
        } finally {
            connection.close();
        }
        return insertRowId;
    }
}
