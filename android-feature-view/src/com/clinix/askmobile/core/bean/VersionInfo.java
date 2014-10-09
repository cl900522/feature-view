package com.clinix.askmobile.core.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

/**
 * 发布的版本信息数据
 * @author SipingWork
 *
 */
public class VersionInfo implements JsonSeriable, Serializable {
    private Long id;
    private String version;
    private String versionName;
    private String updateInfo;
    private String downloadUrl;
    private String allowedVersion;
    private Boolean state;
    private Integer remindFrequency;
    private Date releaseDate;

    public Long getId() {
        return id;
    }

    public void setId(Long tag) {
        this.id = tag;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getAllowedVersion() {
        return allowedVersion;
    }

    public void setAllowedVersion(String allowedVersion) {
        this.allowedVersion = allowedVersion;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Integer getRemindFrequency() {
        return remindFrequency;
    }

    public void setRemindFrequency(Integer remindFrequency) {
        this.remindFrequency = remindFrequency;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

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

    @Override
    public void fromJson(String jsonString) throws Exception {
        JSONObject jsonObject = new JSONObject(jsonString);
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
            allowedVersion = keyValue;
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

    @Override
    public String toJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ID, id);
            jsonObject.put(KEY_VERSION, version);
            jsonObject.put(KEY_VERSIONNAME, versionName);
            jsonObject.put(KEY_UPDATEINFO, updateInfo);
            jsonObject.put(KEY_DOWNLOADURL, downloadUrl);
            jsonObject.put(KEY_COMPATIBLEVERSION, allowedVersion);
            jsonObject.put(KEY_STATE, state);
            jsonObject.put(KEY_REMINDFREQUENCY, remindFrequency);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            jsonObject.put(KEY_RELEASEDATE, format.format(releaseDate));
        } catch (Exception e) {
            throw new Exception("Error to generate json for Version");
        }
        return jsonObject.toString();
    }

}
