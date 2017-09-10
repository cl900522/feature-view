package com.siping.wechat.bean.menu;

import org.json.JSONObject;

public abstract class MenuButton extends MenuItem{
    private String type;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public static MenuItem createMenuItem(JSONObject jsonObject) throws Exception {
        MenuItem menuButton = null;
        String type = jsonObject.getString("type");
        if(type.equals("click")){
            ClickButton menu = new ClickButton();
            menu.setKey(jsonObject.getString("key"));
            menuButton = menu;
        } else if(type.equals("view")){
            ViewButton menu = new ViewButton();
            menu.setUrl(jsonObject.getString("url"));
            menuButton = menu;
        } else if(type.equals("scancode_push")){
            ScancodePushButton menu = new ScancodePushButton();
            menu.setKey(jsonObject.getString("key"));
            menuButton = menu;
        } else if(type.equals("scancode_waitmsg")){
            ScancodeWaitmsgButton menu = new ScancodeWaitmsgButton();
            menu.setKey(jsonObject.getString("key"));
            menuButton = menu;
        } else if(type.equals("pic_sysphoto")){
            PicSysphotoButton menu = new PicSysphotoButton();
            menu.setKey(jsonObject.getString("key"));
            menuButton = menu;
        } else if(type.equals("pic_photo_or_album")){
            PicPhotoOrAlbumButton menu = new PicPhotoOrAlbumButton();
            menu.setKey(jsonObject.getString("key"));
            menuButton = menu;
        } else if(type.equals("pic_weixin")){
            PicWeixinButton menu = new PicWeixinButton();
            menu.setKey(jsonObject.getString("key"));
            menuButton = menu;
        } else if(type.equals("location_select")){
            LocationSelectButton menu = new LocationSelectButton();
            menu.setKey(jsonObject.getString("key"));
            menuButton = menu;
        } else if(type.equals("media_id")){
            MediaIdButton menu = new MediaIdButton();
            menu.setMediaId(jsonObject.getString("media_id"));
            menuButton = menu;
        } else if(type.equals("view_limited")){
            ViewLimitedButton menu = new ViewLimitedButton();
            menu.setMediaId(jsonObject.getString("media_id"));
            menuButton = menu;
        }
        menuButton.setName(jsonObject.getString("name"));
        return menuButton;
    }
}
