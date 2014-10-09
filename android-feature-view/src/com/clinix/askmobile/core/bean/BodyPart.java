package com.clinix.askmobile.core.bean;

import java.io.Serializable;

/**
 * 身体部位类，参考数据表 bodyposition中的数据
 * @author SipingWork
 */
public class BodyPart implements Serializable {
    public static final String[] tableColumns = new String[] { "id", "name", "isleaf", "coords" };
    public static final int ID_INDEX = 0;
    public static final int NAME_INDEX = 1;
    public static final int LEAF_INDEX = 2;
    public static final int COORDS_INDEX = 3;

    private int id;
    private String name;
    /**
     * 是否为节点部位，即不可再分割的部位，数据库用1表示叶节点，0表示非叶节点
     */
    private boolean isLeaf;
    /**
     * 部位对应的图片x1,y1,x2,y2....xn,yn序列，一般认为如过该值为非null，则应该将数据库中的superid设置为0（表示全身）
     */
    private String coords;

    public BodyPart() {
        /**
         * 表示全身的编号，数据库中没有该值
         */
        setId(0);
        setName("全身");
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

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getCoords() {
        return coords;
    }

    public void setCoords(String positions) {
        this.coords = positions;
    }

}
