package com.siping.wechat.bean;

import java.util.List;

public class WeChatUserLink {
    /**
     * 总数
     */
    private int total;
    /**
     * 本次获取到的个数
     */
    private int count;
    /**
     * openidList
     */
    private List<String> openidList;
    /**
     * 下一个Openid
     */
    private String nextOpenId;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getOpenidList() {
        return openidList;
    }

    public void setOpenidList(List<String> openidList) {
        this.openidList = openidList;
    }

    public String getNextOpenId() {
        return nextOpenId;
    }

    public void setNextOpenId(String nextOpenId) {
        this.nextOpenId = nextOpenId;
    }

}
