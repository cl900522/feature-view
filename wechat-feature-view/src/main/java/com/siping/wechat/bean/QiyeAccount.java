package com.siping.wechat.bean;

public class QiyeAccount {
    /**
     * 是否自動載入token
     */
    private Boolean autoReload = false;
    private String wechatAccount;
    private String corpid;
    private String corpsecret;
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
    public String getCorpid() {
        return corpid;
    }
    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }
    public String getCorpsecret() {
        return corpsecret;
    }
    public void setCorpsecret(String corpsecret) {
        this.corpsecret = corpsecret;
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
