package com.clinix.askmobile.core.bean;

/**
 * 支持Json转换的接口
 * @author SipingWork
 */
public interface JsonSeriable {
    /**
     * 将服务器发送来的json数据转换为Object
     * @param jsonString
     * @throws Exception
     */
    public void fromJson(String jsonString) throws Exception;

    /**
     * 将Object转换为Json格式，方便传入服务器
     * @return
     * @throws Exception
     */
    public String toJson() throws Exception;
}
