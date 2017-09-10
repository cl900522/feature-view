package com.siping.wechat.bean.menu;

import org.json.JSONObject;

public class ViewLimitedButton extends MenuButton {
    private String mediaId;
    public ViewLimitedButton(){
        super();
        this.setType("view_limited");
    }
    
    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public JSONObject generateJsonString() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", this.getType());
        jsonObject.put("name", this.getName());
        jsonObject.put("media_id", this.getMediaId());
        return jsonObject;
    }

}
