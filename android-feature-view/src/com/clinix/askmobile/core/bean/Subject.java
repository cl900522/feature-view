package com.clinix.askmobile.core.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import com.clinix.askmobile.AskMobileApplication;

/**
 * 咨询的主题信息
 * @author SipingWork
 *
 */
public class Subject implements JsonSeriable, Serializable {
    /**
     * 问题编号
     */
    Long id;
    /**
     * 提问者编号，采用机器的唯一标识码存储
     */
    UserInfo asker;
    /**
     * 问题标题
     */
    HistoryRecord history;
    /**
     * 问题内容
     */
    String content;
    /**
     * 问题标签
     */
    String tag;
    /**
     * 积分
     */
    float scrore;
    /**
     * 阅读人数
     */
    long readNumber = 0;
    /**
     * 提交日期
     */
    Date submitDate = new Date();
    /**
     * 最后回复日期
     */
    Date lastReplyDate = new Date();
    /**
     * 最后回复人编号，采用机器的唯一标识码存储
     */
    UserInfo lastReplyUser;;
    /**
     * 最后回复人姓名
     */
    @Deprecated
    String lastReplyUserName;
    /**
     * 是否做为模板，向其他提问者推荐
     */
    boolean isTemplate;
    /**
     * 是否解决
     */
    boolean isOver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserInfo getAsker() {
        return asker;
    }

    public void setAsker(UserInfo asker) {
        this.asker = asker;
    }

    public HistoryRecord getHistory() {
        return history;
    }

    public void setHistory(HistoryRecord history) {
        this.history = history;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public float getScrore() {
        return scrore;
    }

    public void setScrore(float scrore) {
        scrore = scrore;
    }

    public long getReadNumber() {
        return readNumber;
    }

    public void setReadNumber(long readNumber) {
        this.readNumber = readNumber;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Date getLastReplyDate() {
        return lastReplyDate;
    }

    public void setLastReplyDate(Date lastReplyDate) {
        this.lastReplyDate = lastReplyDate;
    }

    public UserInfo getLastReplyUser() {
        return lastReplyUser;
    }

    public void setLastReplyUser(UserInfo lastReplyUser) {
        this.lastReplyUser = lastReplyUser;
    }

    public String getLastReplyUserName() {
        return lastReplyUserName;
    }

    public void setLastReplyUserName(String lastReplyUserName) {
        this.lastReplyUserName = lastReplyUserName;
    }

    public boolean isTemplate() {
        return isTemplate;
    }

    public void setTemplate(boolean isTemplate) {
        this.isTemplate = isTemplate;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean isOver) {
        this.isOver = isOver;
    }

    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return content + "  " + dateFormat.format(submitDate);
    }

    /**
     * json keys
     */
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

    public String toJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        UserInfo user = AskMobileApplication.instance().getUserInfo();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Long serNum = new Date().getTime();
            jsonObject.put(KEY_ID, serNum);
            jsonObject.put(KEY_ASKERID, user.getId());
            if (history == null) {
                jsonObject.put(KEY_DEPT, "NULL");
                jsonObject.put(KEY_HISTORYID, "-1");
            } else {
                jsonObject.put(KEY_DEPT, history.getBody().getDepart().toString());
                jsonObject.put(KEY_HISTORYID, history.getServerId());
            }
            jsonObject.put(KEY_CONTENT, content);
            jsonObject.put(KEY_SCORE, 0);
            jsonObject.put(KEY_READNUMBER, readNumber);
            jsonObject.put(KEY_SUBMITDATE, format.format(submitDate));
            jsonObject.put(KEY_ISOVER, isOver);
        } catch (Exception e) {
            throw new Exception("Error format " + e.getMessage());
        }
        return jsonObject.toString();
    }

    public void fromJson(String json) throws Exception {
        try {
            JSONObject subjectJson = new JSONObject(json);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String value = subjectJson.getString(KEY_CONTENT);
            setContent(value);
            value = subjectJson.getString(KEY_ID);
            setId(Long.parseLong(value));
            value = subjectJson.getString(KEY_HISTORYID);
            HistoryRecord history = new HistoryRecord();
            history.setServerId(value);
            //TODO more detail info to be loaded
            setHistory(history);
            value = subjectJson.getString(KEY_SUBMITDATE);
            setSubmitDate(dateFormat.parse(value));
        } catch (Exception e) {
            throw new Exception("Failt to parse json to Subject" + e.getMessage());
        }
    }
}
