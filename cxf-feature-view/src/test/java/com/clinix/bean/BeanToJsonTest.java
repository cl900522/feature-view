package com.clinix.bean;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试所有数据类的生成和解析Json是否有错误
 * @author SipingWork
 */
public class BeanToJsonTest {
    @Test
    public void testProcessResult() {
        ProcessResult result = new ProcessResult();
        result.setCode(1);
        result.setMessage("Test");
        String json = result.toString();
        Assert.assertNotNull(json);
    }

    @Test
    public void testVersion() {
        Version version = new Version();
        version.id = 12873L;
        version.version = "10.3.4Beta";
        version.versionName = "Orange";
        version.updateInfo = "删除上传数据功能";
        version.downloadUrl = "http://bizhi.zhuoku.com/2010/05/11/Ubuntu/1680x1050.png";
        version.compatibleVersion = "7.*,8.*,9.*";
        version.state = true;
        version.releaseDate = new Date();
        version.remindFrequency = 12;
        String json = version.toJsonString();
        System.out.println(json);
        Version equalVersion = new Version();
        try {
            equalVersion.fromJsonString(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(version.version, equalVersion.version);
        Assert.assertEquals(version.compatibleVersion, equalVersion.compatibleVersion);
    }

    @Test
    public void testHistoryRecord() {
        HistoryRecord history = new HistoryRecord();
        history.sex = "wm";
        history.age = 12;
        history.id = 456723L;
        history.number = "16237";
        history.userId = 16237L;
        history.zhu = "精神恍惚";
        history.qi = "不定时发病";
        history.fu = "到处乱跑";
        history.ji = "神经病";
        history.ke = "精神科";
        history.ip = "232.123.1.14";
        history.gps = "LAT:123.98132;LON:123.0398123";
        history.agent = "this is tester's devid id";
        history.date = new Date();
        String json = history.toJsonString();
        System.out.println(json);
        HistoryRecord equalHistory = new HistoryRecord();
        try {
            equalHistory.fromJsonString(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(history.gps, equalHistory.gps);
        Assert.assertEquals(history.userId, equalHistory.userId);
    }

    @Test
    public void testQustion() {
        Subject question = new Subject();
        question.id = 11233L;
        question.askerId = 9283948L;
        question.dept = "外科";
        question.historyId = 928948L;
        question.content = "请问大夫如何治疗呢？";
        question.tag = "外伤，骨伤";
        question.score = 12F;
        question.readNumber = 12873L;
        question.submitDate = new Date();
        question.lastReplyDate = new Date();
        question.lastReplyUserId = 92833948L;
        question.isTemplate = true;
        question.isOver = true;
        question.hasNewReply = true;

        String json = question.toJsonString();
        System.out.println(json);
        Subject equalQuestion = new Subject();
        try {
            equalQuestion.fromJsonString(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(question.readNumber, equalQuestion.readNumber);
        Assert.assertEquals(question.isOver, equalQuestion.isOver);
    }

    @Test
    public void testReply() {
        Reply reply = new Reply();
        reply.id = 1123223L;
        reply.questionId = 928348L;
        reply.replyerId = 9283948L;
        reply.content = "请问大夫如何治疗呢？";
        reply.score = 12F;
        reply.replyDate = new Date();
        reply.isRead = true;

        String json = reply.toJsonString();
        System.out.println(json);
        Reply equalReply = new Reply();
        try {
            equalReply.fromJsonString(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(reply.questionId, equalReply.questionId);
        Assert.assertEquals(reply.isRead, equalReply.isRead);
        Assert.assertEquals(reply.content, equalReply.content);
    }
    @Test
    public void testUser() {
        UserAccount reply = new UserAccount();
        reply.id = 11223L;
        reply.deviceId = "IOS129388fasd7";
        reply.account = "zhangMing";
        reply.password = "googleearth";
        reply.tel = "1383021123";

        String json = reply.toJsonString();
        System.out.println(json);
        UserAccount equalReply = new UserAccount();
        try {
            equalReply.fromJsonString(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(reply.deviceId, equalReply.deviceId);
        Assert.assertEquals(reply.tel, equalReply.tel);
    }
}
