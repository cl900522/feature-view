package com.clinix.askmobile.core.bean;

import java.io.Serializable;

/**
 * 用户的账户信息，还有待完善，参考AskMobileApplication类的参数
 * @author SipingWork
 *
 */
public class UserInfo implements Serializable {
    private Long id;
    private String deviceId;
    private String account;
    private String password;
    private String tel;
    private String ip;
    private String gps;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String machineId) {
        this.deviceId = machineId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

}
