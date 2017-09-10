package com.clinix.askmobile.core.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import android.util.Log;

import com.clinix.askmobile.AskMobileApplication;

/**
 * 记录信息
 * @author Mx
 */
public class HistoryRecord implements JsonSeriable, Serializable {
    public static final String[] tableColumns = new String[] { "rowid", "serverid", "date", "sex", "mainsym", "freqsym", "subsyms", "ill", "depart", "iswilling", "active" };
    public static final int RECORDID_INDEX = 0;
    public static final int SERVERID_INDEX = 1;
    public static final int DATE_INDEX = 2;
    public static final int SEX_INDEX = 3;
    public static final int MAINSYM_INDEX = 4;
    public static final int FREQSYM_INDEX = 5;
    public static final int SUBSYMS_INDEX = 6;
    public static final int ILL_INDEX = 7;
    public static final int DEPART_INDEX = 8;
    public static final int ISWILLING_INDEX = 9;
    public static final int ACTIVE_INDEX = 10;
    /**
     * 用户信息
     */
    private UserInfo userInfo = AskMobileApplication.instance().getUserInfo();
    /**
     * 本地记录编号
     */
    private Long id;
    /**
     * 服务器端记录编号，方便与医生交流时查找对象以及同步时确定是否同步
     */
    private String serverId;
    /**
     * 收藏的日期
     */
    private Date date = new Date();
    /**
     * 身体信息
     */
    private Body body;
    /**
     * 是否为意愿加入的
     */
    private Boolean isWilling = false;

    /**
     * 是否还保存
     */
    private Boolean active = true;

    public Long getId() {
        return id;
    }

    public void setId(Long recordId) {
        this.id = recordId;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body bodyinfo) {
        this.body = bodyinfo;
    }

    public Boolean getIsWilling() {
        return isWilling;
    }

    public void setIsWilling(Boolean isWilling) {
        this.isWilling = isWilling;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String toString() {
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd hh:mm:dd");
        String dateStr = formate.format(date);
        return body.getIll().toString() + " " + dateStr;
    }

    public String toJson() throws Exception {
        final String KEY_ID = "id";
        final String KEY_NUMBER = "number";
        final String KEY_SEX = "sex";
        final String KEY_AGE = "age";
        final String KEY_USERID = "userId";
        final String KEY_ZHU = "zhu";
        final String KEY_QI = "qi";
        final String KEY_FU = "fu";
        final String KEY_JI = "ji";
        final String KEY_KE = "ke";
        final String KEY_IP = "ip";
        final String KEY_GPS = "gps";
        final String KEY_DATE = "date";
        final String KEY_AGENT = "agent";

        JSONObject jsonObject = new JSONObject();
        try {
            Long serialNum = new Date().getTime();
            jsonObject.put(KEY_ID, serialNum);
            jsonObject.put(KEY_NUMBER, serialNum.toString());
            jsonObject.put(KEY_USERID, userInfo.getId());
            jsonObject.put(KEY_SEX, body.sexStr);
            jsonObject.put(KEY_AGE, body.age);
            jsonObject.put(KEY_ZHU, body.getMainSym().toString());
            jsonObject.put(KEY_QI, body.getFreqSym().toString());
            jsonObject.put(KEY_FU, body.getSubSyms().toString());
            jsonObject.put(KEY_JI, body.getIll().toString());
            jsonObject.put(KEY_KE, body.getDepart().toString());
            jsonObject.put(KEY_IP, userInfo.getIp());
            jsonObject.put(KEY_GPS, userInfo.getGps());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:dd");
            jsonObject.put(KEY_DATE, format.format(date));
            jsonObject.put(KEY_AGENT, userInfo.getDeviceId());
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
        }
        return jsonObject.toString();
    }

    @Override
    public void fromJson(String jsonString) throws Exception {
        throw new Exception("This function is not supported!");
    }
}
