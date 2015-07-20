package com.siping.wechat.util.gongzhong;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.siping.wechat.WeChatConstant;
import com.siping.wechat.bean.Oauth2Token;
import com.siping.wechat.bean.WeChatUserInfo;
import com.siping.wechat.bean.WeChatAccount;
import com.siping.wechat.util.WeChatUtil;

public class OAuth2Interface {
    /**
     * 根据微信帐号和code信息获取oauth的token
     * @param appid
     * @param appSecret
     * @param code
     * @return
     */
    public static Oauth2Token getOauth2Token(WeChatAccount wechatAccount, String code) throws Exception{
        String requestURL = WeChatConstant.GET_OAUTH_URL;
        requestURL = requestURL.replace("APPID", wechatAccount.getAppid()).replace("SECRET", wechatAccount.getAppsecret()).replace("CODE", code);

        JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(requestURL, "GET", null);

        Oauth2Token oauthToken = null;
        oauthToken = new Oauth2Token();
        oauthToken.setToken(jsonObject.getString("access_token"));
        oauthToken.setExpiresIn(jsonObject.getInt("expires_in"));
        oauthToken.setRefreshToken(jsonObject.getString("refresh_token"));
        oauthToken.setOpenId(jsonObject.getString("openid"));
        oauthToken.setScope(jsonObject.getString("scope"));
        return oauthToken;
    }

    /**
     * 根据OAuth2token获取UserInfo
     * @param accessToken
     * @param openId
     * @return
     */
    public static WeChatUserInfo getOAuth2UserInfo(String accessToken, String openId) throws Exception{
        WeChatUserInfo userInfo = null;
        String requestUrl = WeChatConstant.GET_OAUTH_USER;
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        try {
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(requestUrl, "GET", null);
            if (null != jsonObject) {
                userInfo = new WeChatUserInfo();
                userInfo.setOpenid(jsonObject.getString("openid"));
                userInfo.setNickname(jsonObject.getString("nickname"));
                userInfo.setSex(jsonObject.getInt("sex"));
                userInfo.setCountry(jsonObject.getString("country"));
                userInfo.setProvince(jsonObject.getString("province"));
                userInfo.setCity(jsonObject.getString("city"));
                userInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                userInfo.setSubscribeTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
        } catch (JSONException e) {
            throw e;
        }
        return userInfo;
    }
}
