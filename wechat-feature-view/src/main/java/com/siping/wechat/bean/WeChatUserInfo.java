package com.siping.wechat.bean;

import java.util.List;
import java.io.Serializable;

/**
 * 微信用户信息
 * @author Mx
 */
public class WeChatUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 编号，数据库唯一编号
     */
    private Long id;
    /**
     * openid用户的唯一标示
     */
    private String openid;
    /**
     * 是否关注
     */
    private int subscribe;
    /**
     * 关注时间
     */
    private String subscribeTime;
    /**
     * 呢称
     */
    private String nickname;
    /**
     * 性别：1男2女0未知
     */
    private int sex;
    /**
     * 国家
     */
    private String country;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 语言
     */
    private String language;
    /**
     * 头像地址
     */
    private String headImgUrl;
    /**
     * 权限列表
     */
    @Deprecated
    private List<String> privileList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getPrivileList() {
        return privileList;
    }

    public void setPrivileList(List<String> privileList) {
        this.privileList = privileList;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(String subscribeTime) {
        this.subscribeTime = subscribeTime;
    }
}
