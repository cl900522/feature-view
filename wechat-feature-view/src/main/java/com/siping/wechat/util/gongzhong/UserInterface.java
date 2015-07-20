package com.siping.wechat.util.gongzhong;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.util.DateUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import com.siping.wechat.HttpRequestMethod;
import com.siping.wechat.WeChatConstant;
import com.siping.wechat.bean.WeChatAccount;
import com.siping.wechat.bean.WeChatGroup;
import com.siping.wechat.bean.WeChatUserInfo;
import com.siping.wechat.bean.WeChatUserLink;
import com.siping.wechat.util.WeChatUtil;

/**
 * 用户和组的管理接口
 * @author nil
 *
 */
public class UserInterface {
    /**
     * 批量获取wechat openid 信息
     * @param wechatAccount
     * @param nextOpenId
     * @return
     * @throws Exception
     */
    public static WeChatUserLink getWeChatUserLink(WeChatAccount wechatAccount, String nextOpenId) throws Exception{
        nextOpenId = (nextOpenId == null ? "" : nextOpenId);
        String url = WeChatConstant.GET_USER_OPENID;
        url = url.replace("ACCESS_TOKEN", wechatAccount.getAccessToken().getToken()).replace("NEXT_OPENID", nextOpenId);

        WeChatUserLink userList = null;
        try {
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, "GET", null);
            userList = new WeChatUserLink();
            userList.setTotal(jsonObject.getInt("total"));
            userList.setCount(jsonObject.getInt("count"));
            userList.setNextOpenId(jsonObject.getString("next_openid"));
            JSONObject dataObject = (JSONObject) jsonObject.get("data");
            JSONArray array = dataObject.getJSONArray("openid");

            List<String> openidList = new ArrayList<String>();
            for (int i = 0; i < array.length(); i++) {
                openidList.add(array.get(i).toString());
            }
            userList.setOpenidList(openidList);
        } catch (Exception e) {
            throw e;
        }
        return userList;
    }

    /**
     * 获取用户信息的方法
     * @param wechatAccount
     * @param openid
     * @return
     * @throws Exception
     */
    public static WeChatUserInfo getWeChatUserInfo(WeChatAccount wechatAccount, String openid) throws Exception{
        String url = WeChatConstant.GET_USERINFO_URL;
        url = url.replace("ACCESS_TOKEN", wechatAccount.getAccessToken().getToken()).replace("OPENID", openid);
        try {
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, "GET", null);
            WeChatUserInfo info = null;
            info = new WeChatUserInfo();
            info.setOpenid(jsonObject.getString("openid"));
            info.setNickname(jsonObject.getString("nickname"));
            info.setSex(jsonObject.getInt("sex"));
            info.setHeadImgUrl(jsonObject.getString("headimgurl"));

            Date subscribeTime = new Date(jsonObject.getLong("subscribe_time"));
            info.setSubscribeTime(DateUtil.formatDate(subscribeTime,"yyyy-MM-dd hh:mm:ss"));

            if(jsonObject.has("subscribe")){
                info.setSubscribe(jsonObject.getInt("subscribe"));
            }
            if(jsonObject.has("country")){
                info.setCountry(jsonObject.getString("country"));
            }
            if(jsonObject.has("province")){
                info.setProvince(jsonObject.getString("province"));
            }
            if(jsonObject.has("city")){
                info.setCity(jsonObject.getString("city"));
            }
            return info;
        } catch (Exception e) {
            throw e;
        }
    }
    /**
     * 创建微信用户组
     * @param wechatAccount
     * @param group
     * @return
     * @throws Exception
     */
    public static WeChatGroup createGroup(WeChatAccount wechatAccount, WeChatGroup group) throws Exception{
        String url = WeChatConstant.CREATE_GROUP;
        url = url.replace("ACCESS_TOKEN", wechatAccount.getAccessToken().getToken());

        try {
            JSONObject groupname = new JSONObject();
            groupname.put("name", group.getName());

            JSONObject params = new JSONObject();
            params.put("group", groupname);
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST, params.toString());
            if(jsonObject.has(WeChatConstant.JSON_ERRCODE_KEY)){
                throw new Exception(jsonObject.getString(WeChatConstant.JSON_ERRMSG_KEY));
            }
            JSONObject groupObject = jsonObject.getJSONObject("group");
            group.setId(groupObject.getString("id"));
            return group;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 获取微信分组列表
     * @param wechatAccount
     * @param group
     * @return
     * @throws Exception
     */
    public static List<WeChatGroup> getGroups(WeChatAccount wechatAccount) throws Exception{
        String url = WeChatConstant.GET_ALL_GROUPS;
        url = url.replace("ACCESS_TOKEN", wechatAccount.getAccessToken().getToken());

        JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.GET, null);
        if(jsonObject.has(WeChatConstant.JSON_ERRCODE_KEY)){
            throw new Exception(jsonObject.getString(WeChatConstant.JSON_ERRMSG_KEY));
        }

        List<WeChatGroup> groups = new ArrayList<WeChatGroup>();
        JSONArray groupArray = jsonObject.getJSONArray("groupS");
        for (int i = 0; i < groupArray.length(); i++) {
            JSONObject object = (JSONObject) groupArray.get(i);
            WeChatGroup group = new WeChatGroup();
            group.setId(object.getString("id"));
            group.setName(object.getString("name"));
            groups.add(group);
        }

        return groups;
    }

    /**
     * 查询用户所在分组的方法
     * @param wechatAccount
     * @param openid
     * @return
     * @throws Exception
     */
    public static WeChatGroup getUserGroup(WeChatAccount wechatAccount, String openid) throws Exception{
        String url = WeChatConstant.GET_USER_GROUP;
        url = url.replace("ACCESS_TOKEN", wechatAccount.getAccessToken().getToken());
        try{
            JSONObject params = new JSONObject();
            params.put("openid", openid);
            JSONObject result = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST, params.toString());

            if(result.has(WeChatConstant.JSON_ERRCODE_KEY)){
                throw new Exception(result.getString(WeChatConstant.JSON_ERRMSG_KEY));
            }

            WeChatGroup group = new WeChatGroup();
            group.setId(result.getString("groupid"));
            return group;
        }catch(Exception e){
            throw e;
        }

    }

    /**
     * 修改分组名的方法
     * @param wechatAccount
     * @param group
     * @return
     * @throws Exception
     */
    public static void updateGroup(WeChatAccount wechatAccount, WeChatGroup group) throws Exception{
        String url = WeChatConstant.UPDATE_GROUP;
        url = url.replace("ACCESS_TOKEN", wechatAccount.getAccessToken().getToken());
        try{
            JSONObject groupData = new JSONObject();
            groupData.put("id", group.getId());
            groupData.put("name", group.getName());

            JSONObject param = new JSONObject();
            param.put("group", groupData);
            JSONObject result =  WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST, param.toString());

            if(result.has(WeChatConstant.JSON_ERRCODE_KEY)){
                if(result.getInt(WeChatConstant.JSON_ERRCODE_KEY) != 0){
                    throw new Exception(result.getString(WeChatConstant.JSON_ERRMSG_KEY));
                }
            } else {
                throw new Exception("Can not know result.");
            }
        }catch(Exception e){
            throw e;
        }
    }

    /**
     * 移动用户分组的方法
     * @param wechatAccount
     * @param group
     * @return
     * @throws Exception
     */
    public static void updateUserGroup(WeChatAccount wechatAccount, WeChatUserInfo wechatUser, WeChatGroup group) throws Exception{
        String url = WeChatConstant.UPDATE_USER_GROUP;
        url = url.replace("ACCESS_TOKEN", wechatAccount.getAccessToken().getToken());
        try{
            JSONObject params = new JSONObject();
            params.put("openid", wechatUser.getOpenid());
            params.put("to_groupid", group.getId());
            JSONObject result = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST, params.toString());

            if(result.getInt(WeChatConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(result.getString(WeChatConstant.JSON_ERRMSG_KEY));
            }

        }catch(Exception e){
            throw e;
            }
    }
    /**
     * 批量移动用户分组的方法
     * @param wechatAccount
     * @param group
     * @return
     * @throws Exception
     */
    public static void batchUpdateUserGroup(WeChatAccount wechatAccount,WeChatGroup group,List<WeChatUserInfo> wechatUser) throws Exception{
        String url = WeChatConstant.BATCH_UPDATE_USER_GROUP;
        url = url.replace("ACCESS_TOKEN", wechatAccount.getAccessToken().getToken());
        try{
            JSONObject params = new JSONObject();
            JSONArray userArray = new JSONArray();
            for(WeChatUserInfo user : wechatUser){
                userArray.put(user.getOpenid());
            }
            params.put("openid_list", userArray);
            params.put("to_groupid", group.getId());
            JSONObject result = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST, params.toString());

            if(result.getInt(WeChatConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(result.getString(WeChatConstant.JSON_ERRMSG_KEY));
            }
        }catch(Exception e){
            throw e;
            }
    }
    /**
     * 删除分组的方法
     * @param wechatAccount
     * @param group
     * @return
     * @throws Exception
     */
    public static void deleteGroup(WeChatAccount wechatAccount,WeChatGroup group) throws Exception{
        String url = WeChatConstant.DELETE_GROUP;
        url = url.replace("ACCESS_TOKEN", wechatAccount.getAccessToken().getToken());
        try{
            JSONObject groupData = new JSONObject();
            groupData.put("id", Integer.parseInt(group.getId()));

            JSONObject param = new JSONObject();
            param.put("group", groupData);
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST, param.toString());

            if(jsonObject.getInt(WeChatConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatConstant.JSON_ERRMSG_KEY));
            }

        }catch(Exception e){
            throw e;
        }
    }
    /**
     * 设置备注名的方法
     * @param wechatAccount
     * @param group
     * @return
     * @throws Exception
     */
    public static void updateRemark(WeChatAccount wechatAccount, WeChatUserInfo wechatUser) throws Exception{
        String url = WeChatConstant.UPDATE_REMARK;
        url = url.replace("ACCESS_TOKEN", wechatAccount.getAccessToken().getToken());
        try{
            JSONObject params = new JSONObject();
            params.put("openid", wechatUser.getOpenid());
            params.put("remark", wechatUser.getNickname());
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST, params.toString());

            if(jsonObject.getInt(WeChatConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatConstant.JSON_ERRMSG_KEY));
            }

        }catch(Exception e){
            throw e;
        }
    }
}
