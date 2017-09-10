package com.siping.wechat.util.qiye;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.siping.wechat.HttpRequestMethod;
import com.siping.wechat.WeChatQiyeConstant;
import com.siping.wechat.bean.QiyeAccount;
import com.siping.wechat.bean.QiyeDepartment;
import com.siping.wechat.bean.QiyeTag;
import com.siping.wechat.bean.QiyeUserInfo;
import com.siping.wechat.util.WeChatUtil;

public class QiyeTagInterface {
    /**
     * 创建标签的方法
     * @param qiyeAccount
     * @param tagid
     * @return
     * @throws Exception
     */
    public static JSONObject createTag(QiyeAccount qiyeAccount, QiyeTag qiyeTag) throws Exception{
        String url = WeChatQiyeConstant.CREATE_TAG;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken());
        try{
            JSONObject params = new JSONObject();
            params.put("tagname", qiyeTag.getTagname());
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST, params.toString());

            if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
            }

            JSONObject result = new JSONObject();
            result.put("tagid", qiyeTag.getTagid());
            return result;
        }catch(Exception e){
            throw e;
        }

    }
    /**
     * 更改标签名字的方法
     * @param qiyeAccount
     * @param tagid
     * @return
     * @throws Exception
     */
    public static void updateTag(QiyeAccount qiyeAccount, QiyeTag qiyeTag) throws Exception{
        String url = WeChatQiyeConstant.UPDATE_TAG;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken());
        try{
            JSONObject params = new JSONObject();
            params.put("tagid", qiyeTag.getTagid());
            params.put("tagname", qiyeTag.getTagname());
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST, params.toString());

            if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
            }

        }catch(Exception e){
            throw e;
            }
    }
    /**
     * 删除标签的方法
     * @param qiyeAccount
     * @param tagid
     * @return
     * @throws Exception
     */
    public static void deleteTag(QiyeAccount qiyeAccount, QiyeTag qiyeTag) throws Exception{
        String url = WeChatQiyeConstant.DELETE_TAG;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken()).replace("tagid", qiyeTag.getTagid());
        try{
            JSONObject params = new JSONObject();
            params.put("tagid", qiyeTag.getTagid());
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.GET, params.toString());

            if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
            }

        }catch(Exception e){
            throw e;
            }
    }
    /**
     * 获取标签成员的方法
     * @param qiyeAccount
     * @param tagid
     * @return
     * @throws Exception
     */
    public static JSONObject getTagUser(QiyeAccount qiyeAccount, QiyeTag qiyeTag, List<QiyeUserInfo> qiyeUserInfo, List<QiyeDepartment> qiyeDepartment) throws Exception{
        String url = WeChatQiyeConstant.GET_TAG_USER;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken()).replace("tagid", qiyeTag.getTagid());
        try{
            JSONObject params = new JSONObject();
            params.put("tagid", qiyeTag.getTagid());
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.GET, params.toString());

            if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
            }

            JSONObject result = new JSONObject();
            JSONArray userData = new JSONArray();
            JSONArray departmentData = new JSONArray();
            for(QiyeUserInfo user : qiyeUserInfo){
                userData.put(user.getUserid());
                userData.put(user.getName());
            }
            for(QiyeDepartment dep : qiyeDepartment){
                departmentData.put(dep.getId());
            }
            result.put("userlist", userData);
            result.put("partylist", departmentData);
            return result;

        }catch(Exception e){
            throw e;
            }
    }
    /**
     * 增加标签成员的方法
     * @param qiyeAccount
     * @param tagid
     * @return
     * @throws Exception
     */
    public static void addTagUser(QiyeAccount qiyeAccount, QiyeTag qiyeTag, List<QiyeUserInfo> qiyeUserInfo, List<QiyeDepartment> qiyeDepartment) throws Exception{
        String url = WeChatQiyeConstant.ADD_TAG_USER;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken());
        JSONObject params = new JSONObject();
        JSONArray userData = new JSONArray();
        JSONArray departmentData = new JSONArray();
        for(QiyeUserInfo user : qiyeUserInfo){
            userData.put(user.getUserid());
        }
        for(QiyeDepartment dep : qiyeDepartment){
            departmentData.put(dep.getId());
        }
        params.put("userlist", userData);
        params.put("partylist", departmentData);
        params.put("tagid", qiyeTag.getTagid());
        JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST, params.toString());

        if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
            throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
        }

        if(jsonObject.has("invalidlist")){
            throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
        }

        if(jsonObject.has("invalidparty")){
            throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
        }

        if(jsonObject.has("invalidlist") && jsonObject.has("invalidparty")){
            throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
        }

    }
    /**
     * 删除标签成员的方法
     * @param qiyeAccount
     * @param tagid
     * @return
     * @throws Exception
     */
    public static void deleteTagUser(QiyeAccount qiyeAccount, QiyeTag qiyeTag, List<QiyeUserInfo> qiyeUserInfo, List<QiyeDepartment> qiyeDepartment) throws Exception{
        String url = WeChatQiyeConstant.DEL_TAG_USER;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken());
        JSONObject params = new JSONObject();
        JSONArray userData = new JSONArray();
        JSONArray departmentData = new JSONArray();
        for(QiyeUserInfo user : qiyeUserInfo){
            userData.put(user.getUserid());
        }
        for(QiyeDepartment dep : qiyeDepartment){
            departmentData.put(dep.getId());
        }
        params.put("userlist", userData);
        params.put("partylist", departmentData);
        params.put("tagid", qiyeTag.getTagid());
        JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST, params.toString());

        if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
            throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
        }

        if(jsonObject.has("invalidlist")){
            throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
        }

        if(jsonObject.has("invalidparty")){
            throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
        }

        if(jsonObject.has("invalidlist") && jsonObject.has("invalidparty")){
            throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
        }

    }
    /**
     * 获取标签列表的方法
     * @param qiyeAccount
     * @param tagid
     * @return
     * @throws Exception
     */
    public static void getTagList(QiyeAccount qiyeAccount, List<QiyeTag> qiyeTag) throws Exception{
        String url = WeChatQiyeConstant.GET_TAG_LIST;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken());
        try{
            JSONObject results = new JSONObject();
            JSONObject params = new JSONObject();
            JSONArray listid = new JSONArray();
            JSONArray listname = new JSONArray();
            for(QiyeTag tag : qiyeTag){
                listid.put(tag.getTagid());
                listname.put(tag.getTagname());
            }
            params.put("tagid",listid);
            params.put("tagname", listname);
            results.put("taglist", params);
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.GET, results.toString());

            if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
            }

        }catch(Exception e){
            throw e;
            }
    }
}
