package com.clinix.askmobile.core.bean;

import java.io.Serializable;

/**
 * 科室信息
 * @author Mx
 */
public class Depart implements Serializable{
    public static final String[] dataCoulumns = new String[] { "dept" };
    public static final int ID_INDEX = 1;
    public static final int DEPT_INDEX = 0;

    /**
     * 科室编号
     */
    private int id;
    /**
     * 科室名称
     */
    private String name;

    public Depart() {
    }

    public Depart(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
