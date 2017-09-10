package com.siping.wechat.bean.menu;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Menu {
    private List<MenuItem> menus;

    public List<MenuItem> getMenus() {
        return menus;
    }
    public void setMenus(List<MenuItem> menus) {
        this.menus = menus;
    }
    public void addMenu(MenuItem menu) {
        if(this.menus == null) {
            this.menus = new ArrayList<MenuItem>();
        }
        this.menus.add(menu);
    }

    public String generateJsonContent () throws Exception {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(MenuItem menu: this.getMenus()){
            jsonArray.put(menu.generateJsonString());
        }
        jsonObject.put("button", jsonArray);
        return jsonObject.toString();
    }

    public static Menu buildMenu(JSONObject jsonObject) throws Exception {
        JSONObject jsonMenu = jsonObject.getJSONObject("menu");
        Menu rootMenu = new Menu();
        JSONArray jsonArray = jsonMenu.getJSONArray("button");
        for(int i=0;i<jsonArray.length();i++){
            JSONObject json = (JSONObject) jsonArray.get(i);
            MenuItem menu = MenuItem.buildMenu(json);
            rootMenu.addMenu(menu);
        }
        return rootMenu;
    }
}
