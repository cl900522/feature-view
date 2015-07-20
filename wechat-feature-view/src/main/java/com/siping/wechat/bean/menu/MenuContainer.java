package com.siping.wechat.bean.menu;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class MenuContainer extends MenuItem {
    private List<MenuItem> subMenus = new ArrayList<MenuItem>();
    private String url;

    public List<MenuItem> getSubMenus() {
        return subMenus;
    }
    public void setSubMenus(List<MenuItem> subButtonList) {
        this.subMenus = subButtonList;
    }
    public void addSubMenu(MenuItem subMenu) {
        this.subMenus.add(subMenu);
    }

    public JSONObject generateJsonString() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", this.getName());

        JSONArray jsonArray = new JSONArray();
        for(MenuItem menu: this.getSubMenus()){
            jsonArray.put(menu.generateJsonString());
        }
        jsonObject.put("sub_button", jsonArray);

        return jsonObject;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static MenuItem createMenuItem(JSONObject jsonObject) throws Exception {
        MenuContainer menuContainer = new MenuContainer();
        menuContainer.setName(jsonObject.getString("name"));
        JSONArray subMenus = jsonObject.getJSONArray("sub_button");
        for(int i=0; i<subMenus.length(); i++){
            MenuItem subMenu = MenuItem.buildMenu((JSONObject) subMenus.get(i));
            menuContainer.addSubMenu(subMenu);
        }
        return menuContainer;
    }
}
