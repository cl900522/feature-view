package com.siping.wechat.bean.menu;

import org.json.JSONObject;

public class PicSysphotoButton extends MenuButton {
    private String key;
    public PicSysphotoButton(){
        super();
        this.setType("pic_sysphoto");
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    @Override
    public JSONObject generateJsonString() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", this.getType());
        jsonObject.put("name", this.getName());
        jsonObject.put("key", this.getKey());
        return jsonObject;
    }

}
