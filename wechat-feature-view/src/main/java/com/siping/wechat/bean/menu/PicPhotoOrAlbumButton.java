package com.siping.wechat.bean.menu;

import org.json.JSONObject;

public class PicPhotoOrAlbumButton extends MenuButton {
    private String key;
    public PicPhotoOrAlbumButton(){
        super();
        this.setType("pic_photo_or_album");
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
