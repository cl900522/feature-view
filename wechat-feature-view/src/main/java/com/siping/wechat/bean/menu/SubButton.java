package com.siping.wechat.bean.menu;

import java.util.List;

import org.json.JSONObject;

public class SubButton extends MenuItem {

    public List<SubButton> subButtonList;
    private String url;

    public List<SubButton> getSubButtonList() {
        return subButtonList;
    }

    public void setSubButtonList(List<SubButton> subButtonList) {
        this.subButtonList = subButtonList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public JSONObject generateJsonString() throws Exception {
        return null;
    }
}
