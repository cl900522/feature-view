package com.clinix.askmobile.core.bean;

import java.io.Serializable;

/**
 * 最基础的数据，即编号和名称，通常其他的疾病和症状都需要继承该类，这个类主要用于查询返回值使用，减少数据交互
 * @author Work
 */
public class ShortBasicInfo implements Serializable {
    /**
     * 编号，对应的是数组下标，所以从1开始，不是从0开始
     */
    public int Key_no;
    /**
     * 名称
     */
    public String Cvocable;

    public ShortBasicInfo() {
        Cvocable = "";
        Key_no = -1;
    }

    @Override
    public String toString() {
        return Cvocable;
    }

    public String getFormatedKeyno() {
        if (Key_no == -1)
            return "00000";
        String Kno = String.valueOf(Key_no);
        while (Kno.length() < 5)
            Kno = "0" + Kno;
        return Kno;
    }
}
