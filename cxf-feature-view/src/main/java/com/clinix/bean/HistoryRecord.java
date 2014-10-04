package com.clinix.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

/**
 * 记录信息
 * @author Mx
 */
public class HistoryRecord implements JsonSeriable {
    /**
     * json的key值
     */
    private static final String KEY_ID = "id";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_SEX = "sex";
    private static final String KEY_AGE = "age";
    private static final String KEY_USERID = "userId";
    private static final String KEY_ZHU = "zhu";
    private static final String KEY_QI = "qi";
    private static final String KEY_FU = "fu";
    private static final String KEY_JI = "ji";
    private static final String KEY_KE = "ke";
    private static final String KEY_IP = "ip";
    private static final String KEY_GPS = "gps";
    private static final String KEY_DATE = "date";
    private static final String KEY_AGENT = "agent";

    public Long id;
    public String number;
    public Long userId;
    public String sex;
    public int age;
    public String zhu;
    public String qi;
    public String fu;
    public String ji;
    public String ke;
    public String ip;
    public String gps;
    public Date date;
    public String agent;

    @Override
    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_ID, id);
        jsonObject.put(KEY_NUMBER, number);
        jsonObject.put(KEY_USERID, userId);
        jsonObject.put(KEY_SEX, sex);
        jsonObject.put(KEY_AGE, age);
        jsonObject.put(KEY_ZHU, zhu);
        jsonObject.put(KEY_QI, qi);
        jsonObject.put(KEY_FU, fu);
        jsonObject.put(KEY_JI, ji);
        jsonObject.put(KEY_KE, ke);
        jsonObject.put(KEY_IP, ip);
        jsonObject.put(KEY_GPS, gps);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        jsonObject.put(KEY_DATE, format.format(date));
        jsonObject.put(KEY_AGENT, agent);
        return jsonObject.toString();
    }

    @Override
    public void fromJsonString(String json) throws Exception {
        JSONObject jsonObject = JSONObject.fromObject(json);
        try {
            String keyValue = jsonObject.getString(KEY_NUMBER);
            number = keyValue;
            keyValue = jsonObject.getString(KEY_SEX);
            sex = keyValue;
            keyValue = jsonObject.getString(KEY_AGE);
            age = Integer.parseInt(keyValue);
            keyValue = jsonObject.getString(KEY_USERID);
            userId = Long.parseLong(keyValue);
            keyValue = jsonObject.getString(KEY_ZHU);
            zhu = keyValue;
            keyValue = jsonObject.getString(KEY_QI);
            qi = keyValue;
            keyValue = jsonObject.getString(KEY_FU);
            fu = keyValue;
            keyValue = jsonObject.getString(KEY_JI);
            ji = keyValue;
            keyValue = jsonObject.getString(KEY_KE);
            ke = keyValue;
            keyValue = jsonObject.getString(KEY_IP);
            ip = keyValue;
            keyValue = jsonObject.getString(KEY_GPS);
            gps = keyValue;
            keyValue = jsonObject.getString(KEY_DATE);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            date = format.parse(keyValue);
            keyValue = jsonObject.getString(KEY_AGENT);
            agent = keyValue;
        } catch (Exception e) {
            throw new Exception("Error to parse jsonString to History Record:" + e.getMessage());
        }
    }
}
