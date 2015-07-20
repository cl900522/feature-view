package com.siping.wechat.bean;

import java.io.Serializable;

/**
 * 微信的接口获取的access_token信息
 * @author Mx
 */
public class AccessToken implements Serializable{
    /**
     *  获取到的凭证
     */
    private String token;

    /**
     * 凭证有效时间，单位：秒
     */
    private Integer expiresIn;

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AccessToken [token=" + this.getToken() == null ? "null" : this.getToken() + ", expiresIn=" + expiresIn + "]";
    }
}
