package com.clinix.bean;

import net.sf.json.JSONObject;

public class UserAccount implements JsonSeriable {
    private static final String KEY_ID = "id";
    private static final String KEY_DEVICEID = "deviceId";
    private static final String KEY_ACCOUNT = "account";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_TEL = "tel";

    public Long id;
    public String deviceId;
    public String account;
    public String password;
    public String tel;

    @Override
    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_ID, id);
        jsonObject.put(KEY_DEVICEID, deviceId);
        jsonObject.put(KEY_ACCOUNT, account);
        jsonObject.put(KEY_PASSWORD, password);
        jsonObject.put(KEY_TEL, tel);
        return jsonObject.toString();
    }

    @Override
    public void fromJsonString(String json) throws Exception {
        JSONObject jsonObject = JSONObject.fromObject(json);
        try {
            String keyValue = jsonObject.getString(KEY_ID);
            id = Long.parseLong(keyValue);
            keyValue = jsonObject.getString(KEY_DEVICEID);
            deviceId = keyValue;
            keyValue = jsonObject.getString(KEY_ACCOUNT);
            account = keyValue;
            keyValue = jsonObject.getString(KEY_PASSWORD);
            password = keyValue;
            keyValue = jsonObject.getString(KEY_TEL);
            tel = keyValue;
        } catch (Exception e) {
            throw new Exception("Error to parse jsonString to UserAccount:" + e.getMessage());
        }
    }

}
