package com.siping.wechat.util.gongzhong;

import org.json.JSONObject;

import com.siping.wechat.WeChatConstant;
import com.siping.wechat.bean.AccessToken;
import com.siping.wechat.bean.WeChatAccount;
import com.siping.wechat.util.WeChatUtil;

/**
 * access_token是公众号的全局唯一票据，公众号调用各接口时都需使用access_token。
 * 开发者需要进行妥善保存。access_token的存储至少要保留512个字符空间。
 * access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
 * @author nil
 *
 */
public class BasicInterface {
    /**
     * 通过公众服务号或订阅号帐号获取access_token
     * @param appid
     * @param appSecret
     * @return
     */
    public static AccessToken getAccessToken(WeChatAccount wechatAccount) throws Exception {
        String url = WeChatConstant.GTE_ACCESSTOKEN_URL;
        String requestUrl = url.replace("APPID", wechatAccount.getAppid()).replace("APPSECRET", wechatAccount.getAppsecret());
        JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(requestUrl, "GET", null);
        if(jsonObject.has(WeChatConstant.JSON_ERRCODE_KEY)){
            throw new Exception(jsonObject.getString(WeChatConstant.JSON_ERRMSG_KEY));
        }

        AccessToken token = null;
        try {
            token = new AccessToken();
            token.setToken(jsonObject.getString("access_token"));
            token.setExpiresIn(jsonObject.getInt("expires_in"));
        } catch (Exception e) {
            throw e;
        }

        wechatAccount.setAccessToken(token);
        return token;
    }
}
