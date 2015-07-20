package com.siping.wechat.bean;

import java.io.Serializable;

public class WeChatAccount implements Serializable{
    /**
     * 是否自動載入token
     */
    private Boolean autoReload = false;
    private String wechatAccount;
    private String appid;
    private String appsecret;
    private String token;
    private AccessToken accessToken;

    public Boolean getAutoReload() {
        return autoReload;
    }
    public void setAutoReload(Boolean autoReload) {
        this.autoReload = autoReload;
    }
    public String getWechatAccount() {
        return wechatAccount;
    }
    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
    }
    public String getAppid() {
        return appid;
    }
    public void setAppid(String appid) {
        this.appid = appid;
    }
    public String getAppsecret() {
        return appsecret;
    }
    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public AccessToken getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
