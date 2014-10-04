package com.clinix.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

public class Subject implements JsonSeriable {
    private static final String KEY_ID = "id";
    private static final String KEY_ASKERID = "askerId";
    private static final String KEY_DEPT = "dept";
    private static final String KEY_HISTORYID = "historyId";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_TAG = "tag";
    private static final String KEY_SCORE = "score";
    private static final String KEY_READNUMBER = "readNumber";
    private static final String KEY_SUBMITDATE = "submitDate";
    private static final String KEY_LASTREPLYDATE = "lastReplyDate";
    private static final String KEY_LASTREPLYUSERID = "lastReplyUserId";
    private static final String KEY_ISTEMPLATE = "isTemplate";
    private static final String KEY_ISOVER = "isOver";
    private static final String KEY_HASNEWREPLY = "hasNewReply";

    /**
     * 问题编号
     */
    public Long id;
    /**
     * 提问者编号，采用机器的唯一标识码存储
     */
    public Long askerId;
    /**
     * 科室
     */
    public String dept;
    /**
     * 记录编号
     */
    public Long historyId;
    /**
     * 问题内容
     */
    public String content;
    /**
     * 问题标签
     */
    public String tag;
    /**
     * 积分
     */
    public Float score;
    /**
     * 阅读人数
     */
    public Long readNumber;
    /**
     * 提交日期
     */
    public Date submitDate;
    /**
     * 最后回复日期
     */
    public Date lastReplyDate;
    /**
     * 最后回复人编号，采用机器的唯一标识码存储
     */
    public Long lastReplyUserId;
    /**
     * 是否做为模板，向其他提问者推荐
     */
    public boolean isTemplate = false;
    /**
     * 是否解决
     */
    public boolean isOver = false;

    public boolean hasNewReply = false;

    @Override
    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        jsonObject.put(KEY_ID, id);
        jsonObject.put(KEY_ASKERID, askerId);
        jsonObject.put(KEY_DEPT, dept);
        jsonObject.put(KEY_HISTORYID, historyId);
        jsonObject.put(KEY_CONTENT, content);
        jsonObject.put(KEY_TAG, tag);
        jsonObject.put(KEY_SCORE, score);
        jsonObject.put(KEY_READNUMBER, readNumber);
        jsonObject.put(KEY_SUBMITDATE, format.format(submitDate));
        jsonObject.put(KEY_LASTREPLYDATE, format.format(lastReplyDate));
        jsonObject.put(KEY_LASTREPLYUSERID, lastReplyUserId);
        jsonObject.put(KEY_ISTEMPLATE, isTemplate);
        jsonObject.put(KEY_ISOVER, isOver);
        jsonObject.put(KEY_HASNEWREPLY, hasNewReply);
        return jsonObject.toString();
    }

    @Override
    public void fromJsonString(String json) throws Exception {
        JSONObject jsonObject = JSONObject.fromObject(json);
        try {
            String keyValue = jsonObject.getString(KEY_ID);
            id = Long.parseLong(keyValue);
            keyValue = jsonObject.getString(KEY_ASKERID);
            askerId = Long.parseLong(keyValue);
            keyValue = jsonObject.getString(KEY_DEPT);
            dept = keyValue;
            keyValue = jsonObject.getString(KEY_HISTORYID);
            historyId = Long.parseLong(keyValue);
            keyValue = jsonObject.getString(KEY_CONTENT);
            content = keyValue;
            keyValue = jsonObject.getString(KEY_SCORE);
            score = Float.parseFloat(keyValue);
            keyValue = jsonObject.getString(KEY_READNUMBER);
            readNumber = Long.parseLong(keyValue);
            keyValue = jsonObject.getString(KEY_SUBMITDATE);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            submitDate = format.parse(keyValue);
            keyValue = jsonObject.getString(KEY_ISOVER);
            isOver = Boolean.parseBoolean(keyValue);
        } catch (Exception e) {
            throw new Exception("Error to parse jsonString to Subject:" + e.getMessage());
        }
    }

}
