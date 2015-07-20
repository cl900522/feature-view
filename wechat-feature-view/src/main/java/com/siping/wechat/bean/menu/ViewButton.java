package com.siping.wechat.bean.menu;

import org.json.JSONObject;

public class ViewButton extends MenuButton{
    private String url;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public ViewButton(){
        super();
        this.setType("view");
    }

    @Override
    public JSONObject generateJsonString() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", this.getType());
        jsonObject.put("name", this.getName());
        jsonObject.put("url", this.getUrl());
        return jsonObject;
    }
}
