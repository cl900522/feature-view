package com.clinix.askmobile.core.bean;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 操作结果类，code为0时，表示操作成功，message存储返回的结果json值；否则出错，message存储错误原因等信息
 * @author SipingWork
 */
public class ProcessResult implements JsonSeriable, Serializable {
    private int code = 0;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void fromJson(String json) throws Exception {
        JSONObject jsonObject = new JSONObject(json);
        try {
            code = jsonObject.getInt("code");
            message = jsonObject.getString("message");
        } catch (Exception e) {
            throw new Exception("Error format of ProcessResult's json string");
        }
    }

    @Override
    public String toJson() throws Exception {
        return "";
    }
}
