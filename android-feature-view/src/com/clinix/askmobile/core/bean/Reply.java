package com.clinix.askmobile.core.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

/**
 * 回复的信息
 * @author SipingWork
 */
public class Reply implements JsonSeriable, Serializable {
    private Long id;
    private Subject subject;
    private UserInfo replyer;
    private String content;
    private Date replyDate;
    private Float score;
    private boolean isRead = false;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public UserInfo getReplyer() {
        return replyer;
    }

    public void setReplyer(UserInfo replyer) {
        this.replyer = replyer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long replyId) {
        this.id = replyId;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyTime) {
        this.replyDate = replyTime;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    /**
     * json的key值
     */
    private static final String KEY_ID = "id";
    private static final String KEY_QUESTIONID = "questionId";
    private static final String KEY_REPLYERID = "replyId";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_REPLYDATE = "replyDate";
    private static final String KEY_SCORE = "score";
    private static final String KEY_ISREAD = "isRead";

    @Override
    public void fromJson(String json) throws Exception {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String keyValue = jsonObject.getString(KEY_ID);
            id = Long.parseLong(keyValue);
            keyValue = jsonObject.getString(KEY_CONTENT);
            content = keyValue;
            keyValue = jsonObject.getString(KEY_REPLYDATE);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
            replyDate = format.parse(keyValue);
        } catch (Exception e) {
            throw new Exception("Error to parse jsonString to Reply:" + e.getMessage());
        }
    }

    @Override
    public String toJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
            if (id == null) {
                jsonObject.put(KEY_ID, new Date().getTime());
            } else {
                jsonObject.put(KEY_ID, id);
            }
            jsonObject.put(KEY_QUESTIONID, subject.getId());
            jsonObject.put(KEY_REPLYERID, replyer.getId());
            jsonObject.put(KEY_CONTENT, content);
            jsonObject.put(KEY_REPLYDATE, format.format(replyDate));
            jsonObject.put(KEY_SCORE, score);
            jsonObject.put(KEY_ISREAD, isRead);
        } catch (Exception e) {
            throw new Exception("Error to generate jsonString to Reply" + e.getMessage());
        }
        return jsonObject.toString();
    }

}
