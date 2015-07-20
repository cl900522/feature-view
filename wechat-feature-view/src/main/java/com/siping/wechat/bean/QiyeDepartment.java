package com.siping.wechat.bean;

public class QiyeDepartment {
    /**
     * 部门id
     */
    private String id;

    /**
     * 父亲部门id,根部门id为1
     */
    private String parentid;

    /**
     * 在父部门中的次序值，order值小的排序靠前
     */
    private String order;

    /**
     * 部门名称
     */
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
