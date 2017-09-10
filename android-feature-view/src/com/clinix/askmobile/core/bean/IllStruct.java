package com.clinix.askmobile.core.bean;


/**
 * 描述疾病信息的数据模型
 * @author Work
 */
public class IllStruct extends ShortBasicInfo {
    /**
     * 载入诊断数据时search的列名称
     */
    public static final String[] tableColums = new String[] { "key_no", "cvocable", "symlink", "nosymlink", "ischild", "isman", "iswoman" };
    public static final int KEY_NO_INDEX = 0;
    public static final int CVOCABLE_INDEX = 1;
    public static final int SYMLINK_INDEX = 2;
    public static final int NOSYMLINK_INDEX = 3;
    public static final int ISCHILD_INDEX = 4;
    public static final int ISMAN_INDEX = 5;
    public static final int ISWOMAN_INDEX = 6;

    /**
     * 查询疾病的详细信息使用的search列名称
     */
    public static final String[] tableInfoColums = new String[] { "xml", "dept" };
    public static final int XML_INDEX = 0;
    public static final int DEPT_INDEX = 1;

    /**
     * 对应的症状列表，询证值，加权值（加密）
     */
    public String SYMLink;
    /**
     * 否症，对应的SYM.CONID
     */
    public String NOSYMLINK;
    /**
     * 小孩疾病
     */
    public int IsChild;
    /**
     * 男性病
     */
    public int IsMan;
    /**
     * 女性病
     */
    public int IsWoman;
    /**
     * 傻瓜病
     */
    @Deprecated
    public int IsFool;
    /**
     * XML描述信息
     */
    public String xml;
    /**
     * 科室信息
     */
    public String dept;
}
