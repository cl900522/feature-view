package com.clinix.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.clinix.bean.HistoryRecord;
import com.clinix.bean.Reply;
import com.clinix.bean.Subject;
import com.clinix.bean.Version;

public interface AskMobileDao {
    public Connection getConnection();

    public String saveHistoryRecord(HistoryRecord records) throws Exception;

    public String saveSubject(Subject question) throws Exception;

    public List<Subject> getSubjectList(Map<String, Object> paramMap) throws Exception;

    public List<Reply> getReplyListBySubject(Map<String, Object> paramMap) throws Exception;

    public String saveReply(Reply reply) throws Exception;

    public Version getLatestVersion() throws Exception;
}
