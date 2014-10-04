package com.clinix.dao.impl;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.clinix.bean.HistoryRecord;
import com.clinix.bean.Reply;
import com.clinix.bean.Subject;
import com.clinix.bean.Version;
import com.clinix.dao.AskMobileDao;

/**
 * 测试Dao层服务是否ok
 * @author SipingWork
 */
public class AskMobileDaoImplTest {
    AskMobileDao dao;

    @Before
    public void setUp() throws Exception {
        dao = AskMobileDaoImpl.instance();
    }

    @Test
    public void testProcessResult() {
        Connection connection = dao.getConnection();
        Assert.assertNotNull(connection);
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetLatestVersion() throws Exception {
        Version version = dao.getLatestVersion();
        Assert.assertNotNull(version);
    }

    @Test
    public void testSaveHistoryRecord() throws Exception {
        HistoryRecord history = new HistoryRecord();
        history.sex = "wm";
        history.age = 12;
        history.id = 4567890123L;
        history.number = "16273123981237";
        history.userId = 16273123981237L;
        history.zhu = "精神恍惚";
        history.qi = "不定时发病";
        history.fu = "到处乱跑";
        history.ji = "神经病";
        history.ke = "精神科";
        history.ip = "232.123.1.14";
        history.gps = "LAT:123.98132;LON:123.0398123";
        history.agent = "this is tester's devid id";
        history.date = new Date();

        dao.saveHistoryRecord(history);
    }

    @Test
    public void testSaveQuestion() throws Exception {
        Subject question = new Subject();
        question.id = 1123223L;
        question.askerId = 92823948L;
        question.dept = "外科";
        question.historyId = 9283748L;
        question.content = "请问大夫如何治疗呢？";
        question.tag = "外伤，骨伤";
        question.score = 12.5F;
        question.readNumber = 12873L;
        question.submitDate = new Date();
        question.lastReplyDate = new Date();
        question.lastReplyUserId = 928373948L;
        question.isTemplate = true;
        question.isOver = true;
        question.hasNewReply = true;

        dao.saveSubject(question);
    }

    @Test
    public void testGetSubjectList() throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", "92823948");
        paramMap.put("isOver", true);

        List<Subject> subjects = dao.getSubjectList(paramMap);
        Assert.assertNotNull(subjects);
    }

    @Test
    public void testSaveReply() throws Exception {
        Reply reply = new Reply();
        reply.id = 1123223L;
        reply.questionId = 9232948L;
        reply.replyerId = 924948L;
        reply.content = "请问大夫如何治疗呢？";
        reply.score = 12F;
        reply.replyDate = new Date();
        reply.isRead = true;

        dao.saveReply(reply);
    }

    @Test
    public void getReplyListBySubject() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("subjectId", "9232948");
        paramMap.put("lastDate", format.format(new Date()));

        List<Reply> replys = dao.getReplyListBySubject(paramMap);
        Assert.assertNotNull(replys);
    }
}
