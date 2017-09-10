package com.clinix.bean;

public interface JsonSeriable {
    /**
     * 将自身输出为Json字符串
     * @return
     */
    public String toJsonString();

    /**
     * 从Json字符串解析出信息，如果字符串有问题，则输出异常
     * @param json
     * @throws Exception
     */
    public void fromJsonString(String json) throws Exception;
}
