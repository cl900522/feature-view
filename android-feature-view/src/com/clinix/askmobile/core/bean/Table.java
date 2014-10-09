package com.clinix.askmobile.core.bean;

/**
 * 表的枚举信息，包括名称和index索引，替代以前通过数组查找的方式
 * @author Mx
 */
public enum Table {

    /**
     * ill表和sy表名
     */
    ILL(8, "ill"), SYM(14, "sym");
    public final static int COUNTS = 35;
    private String name;
    private int index;

    private Table(int tableIndex, String tableName) {
        this.name = tableName;
        this.index = tableIndex;
    }

    /**
     * 表名称
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 表对应的使用编号
     * @return
     */
    public int getIndex() {
        return index;
    }
}
