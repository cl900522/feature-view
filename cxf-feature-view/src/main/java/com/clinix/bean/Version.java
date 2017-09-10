package com.clinix.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

public class Version implements JsonSeriable {

    /**
     * json的key值
     */
    private static final String KEY_ID = "id";
    private static final String KEY_VERSION = "version";
    private static final String KEY_VERSIONNAME = "versionName";
    private static final String KEY_UPDATEINFO = "updateInfo";
    private static final String KEY_DOWNLOADURL = "downloadUrl";
    private static final String KEY_COMPATIBLEVERSION = "compatibleVersion";
    private static final String KEY_STATE = "state";
    private static final String KEY_REMINDFREQUENCY = "remindFrequency";
    private static final String KEY_RELEASEDATE = "releaseDate";

    public Long id;
    public String version;
    public String versionName;
    public String updateInfo;
    public String downloadUrl;
    public String compatibleVersion;
    public Boolean state;
    public Integer remindFrequency;
    public Date releaseDate;

    @Override
    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_ID, id);
        jsonObject.put(KEY_VERSION, version);
        jsonObject.put(KEY_VERSIONNAME, versionName);
        jsonObject.put(KEY_UPDATEINFO, updateInfo);
        jsonObject.put(KEY_DOWNLOADURL, downloadUrl);
        jsonObject.put(KEY_COMPATIBLEVERSION, compatibleVersion);
        jsonObject.put(KEY_STATE, state);
        jsonObject.put(KEY_REMINDFREQUENCY, remindFrequency);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        jsonObject.put(KEY_RELEASEDATE, format.format(releaseDate));
        return jsonObject.toString();
    }

    @Override
    public void fromJsonString(String json) throws Exception {
        JSONObject jsonObject = JSONObject.fromObject(json);
        try {
            String keyValue = jsonObject.getString(KEY_ID);
            id = Long.parseLong(keyValue);
            keyValue = jsonObject.getString(KEY_VERSION);
            version = keyValue;
            keyValue = jsonObject.getString(KEY_VERSIONNAME);
            versionName = keyValue;
            keyValue = jsonObject.getString(KEY_UPDATEINFO);
            updateInfo = keyValue;
            keyValue = jsonObject.getString(KEY_DOWNLOADURL);
            downloadUrl = keyValue;
            keyValue = jsonObject.getString(KEY_COMPATIBLEVERSION);
            compatibleVersion = keyValue;
            keyValue = jsonObject.getString(KEY_STATE);
            state = Boolean.parseBoolean(keyValue);
            keyValue = jsonObject.getString(KEY_REMINDFREQUENCY);
            remindFrequency = Integer.parseInt(keyValue);
            keyValue = jsonObject.getString(KEY_RELEASEDATE);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            releaseDate = format.parse(keyValue);
        } catch (Exception e) {
            throw new Exception("Error to parse jsonString to Version:" + e.getMessage());
        }
    }

}
