package com.siping.wechat.bean;

import java.io.Serializable;

/**
 * 微信用户信息
 * @author Mx
 */
public class QiyeUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * userid用户的唯一标示
     */
    private String userid;
    /**
     * 成员名称
     */
    private String name;
    /**
     * 职位信息
     */
    private String position;
    /**
     * 成员联系方式
     */
    private String mobile;
    /**
     * 性别：1男2女0未知
     */
    private int gender;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 微信id
     */
    private String weixinid;
    /**
     * 头像地址
     */
    private String avatarMediaid;
    /**
     * 扩展属性
     */
    private String extattr;
    /**
     * 关注状态: 1=已关注，2=已冻结，4=未关注
     */
    private int status;
    /**
     * 所属部门id
     */
    private String departmentid;
    /**
     * 1:微信邀请 2.邮件邀请
     */
    private int type;

    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public int getGender() {
        return gender;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getWeixinid() {
        return weixinid;
    }
    public void setWeixinid(String weixinid) {
        this.weixinid = weixinid;
    }
    public String getAvatarMediaid() {
        return avatarMediaid;
    }
    public void setAvatarMediaid(String avatarMediaid) {
        this.avatarMediaid = avatarMediaid;
    }
    public String getExtattr() {
        return extattr;
    }
    public void setExtattr(String extattr) {
        this.extattr = extattr;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getDepartmentid() {
        return departmentid;
    }
    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

}
