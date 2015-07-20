package com.siping.wechat.util.gongzhong;


import org.json.JSONObject;

import com.siping.wechat.HttpRequestMethod;
import com.siping.wechat.WeChatConstant;
import com.siping.wechat.bean.WeChatAccount;
import com.siping.wechat.bean.menu.Menu;
import com.siping.wechat.util.WeChatUtil;

public class MenuInterface {

    public static void createMenu(WeChatAccount wechatAccount, Menu menu) throws Exception{
        String url = WeChatConstant.CREATE_MENU;
        url = url.replace("ACCESS_TOKEN", wechatAccount.getAccessToken().getToken());
        try {
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST, menu.generateJsonContent());
            if(jsonObject.has(WeChatConstant.JSON_ERRCODE_KEY)){
                Integer errorCode = jsonObject.getInt(WeChatConstant.JSON_ERRCODE_KEY);
                if(errorCode > 0){
                    throw new Exception(jsonObject.getString(WeChatConstant.JSON_ERRMSG_KEY));
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static Menu getMenu(WeChatAccount wechatAccount) throws Exception{
        String url = WeChatConstant.GET_MENU;
        url = url.replace("ACCESS_TOKEN", wechatAccount.getAccessToken().getToken());
        try {
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.GET, null);
            if(jsonObject.has(WeChatConstant.JSON_ERRCODE_KEY)){
                throw new Exception(jsonObject.getString(WeChatConstant.JSON_ERRMSG_KEY));
            }else{
                return Menu.buildMenu(jsonObject);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static void deleteMenu(WeChatAccount wechatAccount) throws Exception {
        String url = WeChatConstant.DELETE_MENU;
        url = url.replace("ACCESS_TOKEN", wechatAccount.getAccessToken().getToken());
        try {
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.GET, null);
            if(jsonObject.has(WeChatConstant.JSON_ERRCODE_KEY)){
                Integer errorCode = jsonObject.getInt(WeChatConstant.JSON_ERRCODE_KEY);
                if(errorCode > 0){
                    throw new Exception(jsonObject.getString(WeChatConstant.JSON_ERRMSG_KEY));
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
