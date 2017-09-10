package com.clinix.webservice.impl;

import org.junit.Before;
import org.junit.Test;

import com.clinix.webservice.AskMobileWebService;

public class AskMobileWebServiceImplTest {
    private AskMobileWebService service;

    @Before
    public void setUp() throws Exception {
        service = new AskMobielWebServiceImpl();
    }

    @Test
    public void testGetVersion() {
        System.out.println("get version" + service.getLatestVersion());
    }

    @Test
    public void testUploadHistoryRecord() {
        String jsonRecord = "{\"id\":456723,\"number\":\"16237\",\"userId\":16237,\"sex\":\"wm\",\"age\":12,\"zhu\":\"精神恍惚\",\"qi\":\"不定时发病\",\"fu\":\"到处乱跑\",\"ji\":\"神经病\",\"ke\":\"精神科\",\"ip\":\"232.123.1.14\",\"gps\":\"LAT:123.98132;LON:123.0398123\",\"date\":\"2014-09-24\",\"agent\":\"this is tester's devid id\"}";
        System.out.println("upload record" + service.uploadHistoryRecord(jsonRecord));
    }

    @Test
    public void testSubmitSubject() {
        String jsonQuestion = "{\"content\":\"sdfs\",\"id\":1411612744044,\"readNumber\":0,\"historyId\":\"47\",\"submitDate\":\"2014-09-24\",\"score\":0,\"askerId\":20140924,\"isOver\":false,\"dept\":\"骨科\"}";
        System.out.println("submit question" + service.submitSubject(jsonQuestion));
    }

    @Test
    public void testQuerySubject() {
        String resultJson = "{\"userId\":20140924,\"isOver\":\"true\"}";
        System.out.println("query question" + service.querySubject(resultJson));

        resultJson = "{\"userId\":20140924,\"isOver\":\"false\"}";
        System.out.println("query question" + service.querySubject(resultJson));
    }

    @Test
    public void testSubmitReply() {
        String serverId = "{\"id\":1123223,\"questionId\":92833948,\"replyId\":9283948,\"content\":\"请问大夫如何治疗呢？\",\"replyDate\":\"2014-09-24\",\"score\":12,\"isRead\":true}";
        System.out.println("submit Reply" + service.submitReply(serverId));
    }

    @Test
    public void testQueryReply() {
        String questionId = "{\"subjectId\":9232948,\"lastDate\":\"2014-09-14 10:45:20.763\"}";
        System.out.println("query Reply" + service.queryReply(questionId));
    }
}
