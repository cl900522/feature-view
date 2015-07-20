package com.siping.wechat.bean;

/**
 * 通过oauth的认证结果code获取oauth信息
 * @author: LHQ
 * @date: 2014年6月24日 下午2:03:12
 * @email: daysinsun@gmail.com
 * @function:
 * @version :
 */
public class Oauth2Token extends AccessToken{
    /**
     * 未知
     */
    private String refreshToken;

    /**
     * oauth获取的openid信息
     */
    private String openId;

    /**
     * 未知
     */
    private String scope;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

}
