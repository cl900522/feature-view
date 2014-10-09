package com.clinix.askmobile.core.bean;

/**
 * 症状信息
 * @author Work
 */
public class ShortSymInfo extends ShortBasicInfo {
    public static final String[] tableColumns = new String[] { "key_no", "cvo" };
    public static final int KEYNO_INDEX = 0;
    public static final int CVOCABLE_INDEX = 1;
    /**
     * 症状比重
     */
    public double SymWeight;

    public ShortSymInfo() {
        super();
        SymWeight = 0;
    }
}
