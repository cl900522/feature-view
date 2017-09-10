package com.clinix.askmobile.core.bean;


/**
 * 症状的信息
 * @author Mx
 */
public class SymStruct extends ShortBasicInfo {
    public static final String[] tableColumns = new String[] { "key_no", "cvocable", "conid", "iszs", "isfb", "isbs", "iszz", "istz", "isjy" };
    public static final int KEY_NO_INDEX = 0;
    public static final int CVOCABLE_INDEX = 1;
    public static final int CONID_INDEX = 2;
    public static final int ISZS_INDEX = 3;
    public static final int ISFB_INDEX = 4;
    public static final int ISBS_INDEX = 5;
    public static final int ISZZ_INDEX = 6;
    public static final int ISTZ_INDEX = 7;
    public static final int ISJY_INDEX = 8;

    /**
     * 内码，因为Key_no可能会变
     */
    public int CONID;
    /**
     * 主诉
     */
    public int ISZS;
    /**
     * 发病过程
     */
    public int ISFB;
    /**
     * 病史
     */
    public int ISBS;
    /**
     * 症状
     */
    public int ISZZ;
    /**
     * 体征
     */
    public int ISTZ;
    /**
     * 检验
     */
    public int ISJY;
}
