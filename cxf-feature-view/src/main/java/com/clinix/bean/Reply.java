package com.clinix.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

public class Reply implements JsonSeriable {
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

    public Long id;
    public Long questionId;
    public Long replyerId;
    public String content;
    public Date replyDate;
    public Float score;
    public Boolean isRead;

    @Override
    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
        jsonObject.put(KEY_ID, id);
        jsonObject.put(KEY_QUESTIONID, questionId);
        jsonObject.put(KEY_REPLYERID, replyerId);
        jsonObject.put(KEY_CONTENT, content);
        jsonObject.put(KEY_REPLYDATE, format.format(replyDate));
        jsonObject.put(KEY_SCORE, score);
        jsonObject.put(KEY_ISREAD, isRead);
        return jsonObject.toString();
    }

    @Override
    public void fromJsonString(String json) throws Exception {
        JSONObject jsonObject = JSONObject.fromObject(json);
        try {
            String keyValue = jsonObject.getString(KEY_ID);
            id = Long.parseLong(keyValue);
            keyValue = jsonObject.getString(KEY_QUESTIONID);
            questionId = Long.parseLong(keyValue);
            keyValue = jsonObject.getString(KEY_REPLYERID);
            replyerId = Long.parseLong(keyValue);
            keyValue = jsonObject.getString(KEY_CONTENT);
            content = keyValue;
            keyValue = jsonObject.getString(KEY_REPLYDATE);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
            replyDate = format.parse(keyValue);
            keyValue = jsonObject.getString(KEY_SCORE);
            score = Float.parseFloat(keyValue);
            keyValue = jsonObject.getString(KEY_ISREAD);
            isRead = Boolean.parseBoolean(keyValue);
        } catch (Exception e) {
            throw new Exception("Error to parse jsonString to Reply:" + e.getMessage());
        }
    }

}
