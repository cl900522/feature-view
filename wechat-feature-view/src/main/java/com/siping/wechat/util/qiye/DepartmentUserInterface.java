package com.siping.wechat.util.qiye;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.siping.wechat.HttpRequestMethod;
import com.siping.wechat.WeChatConstant;
import com.siping.wechat.WeChatQiyeConstant;
import com.siping.wechat.bean.QiyeAccount;
import com.siping.wechat.bean.QiyeDepartment;
import com.siping.wechat.bean.QiyeUserInfo;
import com.siping.wechat.bean.WeChatGroup;
import com.siping.wechat.util.WeChatUtil;


public class DepartmentUserInterface {
    /**
     * 二次验证的方法
     * @param qiyeAccount
     * @param userid
     * @return
     * @throws Exception
     */
    public static void getAuthSucc(QiyeAccount qiyeAccount, QiyeUserInfo userInfo) throws Exception{
        String url = WeChatQiyeConstant.AUTH_SUCC;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken());
        try{
            JSONObject params = new JSONObject();
            params.put("userid", userInfo.getUserid());
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.GET, params.toString());

            if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
            }

        }catch(Exception e){
            throw e;
            }
    }
    /**
     * 创建部门的方法
     * @param qiyeAccount
     * @param id
     * @return
     * @throws Exception
     */
    public static QiyeDepartment createDepartment(QiyeAccount qiyeAccount, QiyeDepartment qiyeDepartment) throws Exception{
        String url = WeChatQiyeConstant.CREAT_DEPARTMENT;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken());
        try{
            JSONObject params = new JSONObject();
            params.put("name", qiyeDepartment.getName());
            params.put("id", qiyeDepartment.getId());
            params.put("parentid", qiyeDepartment.getParentid());
            params.put("order", qiyeDepartment.getOrder());
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST, params.toString());

            if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
            }

            QiyeDepartment result = new QiyeDepartment();
            result.setId(params.getString("id"));
            return result;

        }catch(Exception e){
            throw e;
            }
    }
    /**
     * 更新部门的方法
     * @param qiyeAccount
     * @param id
     * @return
     * @throws Exception
     */
    public static void updateDepartment(QiyeAccount qiyeAccount, QiyeDepartment qiyeDepartment) throws Exception{
        String url = WeChatQiyeConstant.UPDATE_DEPARTMENT;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken());
        try{
            JSONObject params = new JSONObject();
            params.put("name", qiyeDepartment.getName());
            params.put("id", qiyeDepartment.getId());
            params.put("parentid", qiyeDepartment.getParentid());
            params.put("order", qiyeDepartment.getOrder());
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST, params.toString());

            if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
            }

        }catch(Exception e){
            throw e;
            }
    }
    /**
     * 删除部门的方法
     * @param qiyeAccount
     * @param id
     * @return
     * @throws Exception
     */
    public static void deleteDepartment(QiyeAccount qiyeAccount, QiyeDepartment qiyeDepartment) throws Exception{
        String url = WeChatQiyeConstant.DELETE_DEPARTMENT;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken()).replace("ID", qiyeDepartment.getId());
        try{
            JSONObject params = new JSONObject();
            params.put("id", qiyeDepartment.getId());
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST, params.toString());

            if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
            }

        }catch(Exception e){
            throw e;
            }
    }
    /**
     * 获取部门列表的方法
     * @param qiyeAccount
     * @param id
     * @return
     * @throws Exception
     */
    public static QiyeDepartment getDepartmentList(QiyeAccount qiyeAccount, QiyeDepartment qiyeDepartment) throws Exception{
        String url = WeChatQiyeConstant.GET_DEPARTMENT_LIST;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken());
        try{
            JSONObject params = new JSONObject();
            params.put("id", qiyeDepartment.getId());
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.GET, params.toString());

            if(jsonObject.has(WeChatConstant.JSON_ERRCODE_KEY)){
                throw new Exception(jsonObject.getString(WeChatConstant.JSON_ERRMSG_KEY));
            }

            List<QiyeDepartment> department = new ArrayList<QiyeDepartment>();
            JSONArray listArray = jsonObject.getJSONArray("department");
            for (int i = 0; i < listArray.length(); i++) {
                JSONObject object = (JSONObject) listArray.get(i);
                QiyeDepartment result = new QiyeDepartment();
                result.setId(object.getString("id"));
                result.setName(object.getString("name"));
                result.setOrder(object.getString("order"));
                result.setParentid(object.getString("parentid"));
                department.add(result);
            }

            return (QiyeDepartment) department;
        }catch(Exception e){
            throw e;
        }
    }
    /**
     * 提取用户信息，创建成员+更新成员时用
     */
    public static JSONObject getPostData(QiyeUserInfo qiyeUserInfo) throws Exception{
        JSONObject params = new JSONObject();
        params.put("userid", qiyeUserInfo.getUserid());
        params.put("department", qiyeUserInfo.getDepartmentid());
        params.put("position", qiyeUserInfo.getPosition());
        params.put("mobile", qiyeUserInfo.getMobile());
        params.put("gender", qiyeUserInfo.getGender());
        params.put("email", qiyeUserInfo.getEmail());
        params.put("avatar_mediaid", qiyeUserInfo.getAvatarMediaid());
        params.put("extattr", qiyeUserInfo.getExtattr());
        return params;
    }
    /**
     * 创建成员的方法
     * @param qiyeAccount
     * @param
     * @return
     * @throws Exception
     */
    public static void createUser(QiyeAccount qiyeAccount, QiyeUserInfo qiyeUserInfo) throws Exception{
        String url = WeChatQiyeConstant.CREATE_USER;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken());
        try{
            JSONObject params = getPostData(qiyeUserInfo);
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST,params.toString());

            if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
            }

        }catch(Exception e){
            throw e;
            }
    }
    /**
     * 更新成员的方法
     * @param qiyeAccount
     * @param
     * @return
     * @throws Exception
     */
    public static void updateUser(QiyeAccount qiyeAccount, QiyeUserInfo qiyeUserInfo) throws Exception{
        String url = WeChatQiyeConstant.UPDATE_USER;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken());
        try{
            JSONObject params = getPostData(qiyeUserInfo);
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST,params.toString());

            if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
            }

        }catch(Exception e){
            throw e;
            }
    }
    /**
     * 删除成员的方法
     * @param qiyeAccount
     * @param
     * @return
     * @throws Exception
     */
    public static void deleteUser(QiyeAccount qiyeAccount, QiyeUserInfo qiyeUserInfo) throws Exception{
        String url = WeChatQiyeConstant.DELETE_USER;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken()).replace("userid", qiyeUserInfo.getUserid());
        try{
            JSONObject params = new JSONObject();
            params.put("userid", qiyeUserInfo.getUserid());
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.GET, params.toString());

            if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
            }
        }catch(Exception e){
            throw e;
            }
    }
    /**
     * 批量删除成员的方法
     * @param qiyeAccount
     * @param
     * @return
     * @throws Exception
     */
    public static void batchDeleteUser(QiyeAccount qiyeAccount, List<QiyeUserInfo> qiyeUsers) throws Exception{
        String url = WeChatQiyeConstant.BATCH_DELETE_USER;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken());
        try{
            if(qiyeUsers == null){
                throw new Exception("删除用户列表为空");
            }
            JSONObject params = new JSONObject();
            JSONArray usersJson = new JSONArray();
            for(QiyeUserInfo user : qiyeUsers){
                usersJson.put(user.getUserid());
            }
            params.put("useridlist", usersJson);

            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST, params.toString());

            if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
            }

        }catch(Exception e){
            throw e;
            }
    }
    /**
     * 获取成员的方法
     * @param qiyeAccount
     * @param
     * @return
     * @throws Exception
     */
    public static QiyeUserInfo getUser(QiyeAccount qiyeAccount,QiyeUserInfo qiyeUserInfo) throws Exception{
        String url = WeChatQiyeConstant.GET_USER;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken()).replace("userid", qiyeUserInfo.getUserid());
        try{
            JSONObject params = new JSONObject();
            params.put("userid", qiyeUserInfo.getUserid());
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.GET, params.toString());

            if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
            }

            QiyeUserInfo info = new QiyeUserInfo();
            info.setUserid(jsonObject.getString("userid"));
            info.setName(jsonObject.getString("name"));
            info.setDepartmentid(jsonObject.getString("department"));
            info.setPosition(jsonObject.getString("position"));
            info.setMobile(jsonObject.getString("mobile"));
            info.setGender(jsonObject.getInt("gender"));
            info.setEmail(jsonObject.getString("email"));
            info.setStatus(jsonObject.getInt("status"));
            info.setWeixinid(jsonObject.getString("weixinid"));
            info.setAvatarMediaid(jsonObject.getString("avatar"));
            info.setExtattr(jsonObject.getString("extattr"));
            return info;

        }catch(Exception e){
            throw e;
            }
    }
    /**
     * 获取部门成员的方法
     * @param qiyeAccount
     * @param
     * @return
     * @throws Exception
     */
    public static JSONObject getSimplelistUser(QiyeAccount qiyeAccount,List<QiyeUserInfo> qiyeUserInfo,QiyeDepartment qiyeDepartment) throws Exception{
        String url = WeChatQiyeConstant.GET_SIMPLELIST_USER;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken()).replace("department_id", qiyeDepartment.getId());
        try{
            JSONObject params = new JSONObject();
            params.put("department_id", qiyeDepartment.getId());
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.GET, params.toString());

            if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
            }

            QiyeUserInfo qiyeUser = new QiyeUserInfo();
            JSONArray userArray = new JSONArray();
            JSONObject result = new JSONObject();
            if(qiyeDepartment.getId() == qiyeUser.getDepartmentid()){
                for(QiyeUserInfo user : qiyeUserInfo){
                    userArray.put(user.getUserid());
                    userArray.put(user.getName());
                }
            }
            result.put("userlist", userArray);
            return result;
        }catch(Exception e){
            throw e;
            }

    }
    /**
     * 获取部门成员详细信息的方法
     * @param qiyeAccount
     * @param
     * @return
     * @throws Exception
     */
    public static JSONObject getListUser(QiyeAccount qiyeAccount,QiyeUserInfo qiyeUserInfo,QiyeDepartment qiyeDepartment) throws Exception{
        String url = WeChatQiyeConstant.GET_LIST_USER;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken()).replace("department_id", qiyeDepartment.getId());
        try{
            JSONObject params = new JSONObject();
            params.put("department_id", qiyeDepartment.getId());
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.GET, params.toString());

            if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
            }
            QiyeUserInfo info = new QiyeUserInfo();
            JSONObject result = new JSONObject();
            if(qiyeDepartment.getId() == info.getDepartmentid()){
                info.setUserid(jsonObject.getString("userid"));
                info.setName(jsonObject.getString("name"));
                info.setDepartmentid(jsonObject.getString("department"));
                info.setPosition(jsonObject.getString("position"));
                info.setMobile(jsonObject.getString("mobile"));
                info.setGender(jsonObject.getInt("gender"));
                info.setEmail(jsonObject.getString("email"));
                info.setStatus(jsonObject.getInt("status"));
                info.setWeixinid(jsonObject.getString("weixinid"));
                info.setAvatarMediaid(jsonObject.getString("avatar"));
                info.setExtattr(jsonObject.getString("extattr"));
            }
            result.put("userlist", info);
            return result;

        }catch(Exception e){
            throw e;
            }
    }
    /**
     * 邀请成员关注的方法
     * @param qiyeAccount
     * @param
     * @return
     * @throws Exception
     */
    public static QiyeUserInfo inviteUser(QiyeAccount qiyeAccount,QiyeUserInfo qiyeUserInfo) throws Exception{
        String url = WeChatQiyeConstant.INVITE_USER;
        url = url.replace("ACCESS_TOKEN", qiyeAccount.getAccessToken().getToken());
        try{
            JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, HttpRequestMethod.POST, qiyeUserInfo.getUserid().toString());

            if(jsonObject.getInt(WeChatQiyeConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatQiyeConstant.JSON_ERRMSG_KEY));
            }
            QiyeUserInfo result = new QiyeUserInfo();
            result.setType(jsonObject.getInt("type"));
            return result;
        }catch(Exception e){
            throw e;
            }
    }
}
