package com.clinix.askmobile.core.bean;


/**
 * 记录推出的症状对应病名或者证理的信息
 * @author Work
 */
public class ShortIllInfo extends ShortBasicInfo {
    public static final String[] tableColumns = new String[] { "key_no", "tit" };
    public static final int KEYNO_INDEX = 0;
    public static final int TIT_INDDEX = 1;

    public String TblName = "";
    /**
     * 该病或者证理的值
     */
    public double Rate;
    public int ConSymCount;
    /**
     * 该病或者证理的主要症状rate值
     */
    public int MRate;
    public int Mainflag;
    public double xzvalue;
    public String NOSYMLINK;
}
