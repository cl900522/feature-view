package com.siping.wechat.bean.menu;

import org.json.JSONObject;

public abstract class MenuItem {
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public abstract JSONObject generateJsonString() throws Exception;
    public static MenuItem buildMenu(JSONObject jsonObject) throws Exception{
        MenuItem menu = null;
        if(jsonObject.has("type")){
            menu = MenuButton.createMenuItem(jsonObject);
        }else{
            menu = MenuContainer.createMenuItem(jsonObject);
        }
        return menu;
    }
}
