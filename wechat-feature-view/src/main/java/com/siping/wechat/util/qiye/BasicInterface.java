package com.siping.wechat.util.qiye;

import org.json.JSONObject;

import com.siping.wechat.WeChatQiyeConstant;
import com.siping.wechat.bean.AccessToken;
import com.siping.wechat.bean.QiyeAccount;
import com.siping.wechat.util.WeChatUtil;

/**
 * 在每次主动调用企业号接口时需要带上AccessToken参数。AccessToken参数由CorpID和Secret换取。
 * CorpID是企业号的标识，每个企业号拥有一个唯一的CorpID；Secret是管理组凭证密钥。
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
    public static AccessToken getToken(QiyeAccount qiyeAccount) throws Exception {
        String url = WeChatQiyeConstant.GET_TOKEN;
        String requestUrl = url.replace("CORPID", qiyeAccount.getCorpid()).replace("CORPSECRET", qiyeAccount.getCorpsecret());
        JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(requestUrl, "GET", null);
        if(jsonObject.has(WeChatQiyeConstant.JSON_ERRCODE_KEY)){
            throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
        }

        AccessToken token = null;
        try {
            token = new AccessToken();
            token.setToken(jsonObject.getString("access_token"));
            token.setExpiresIn(jsonObject.getInt("expires_in"));
        } catch (Exception e) {
            throw e;
        }

        qiyeAccount.setAccessToken(token);
        return token;
    }
    public static String getIp(QiyeAccount qiyeAccount) throws Exception {
        String url = WeChatQiyeConstant.GET_CALLBACK_IP;
        String result = null;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken());
        try{
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, "GET", null);
            if(jsonObject != null){
                result = jsonObject.getString("ip_list")+"";
            }
            return result;
        }catch(Exception e){
            throw e;
            }
    }
}
