package com.clinix.bean;

import net.sf.json.JSONObject;

/**
 * 操作结果类，code为0时，表示操作成功，message存储返回的结果json值；否则出错，message存储错误原因等信息
 * @author SipingWork
 */
public class ProcessResult {
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

    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("message", message);
        return jsonObject.toString();
    }

}
