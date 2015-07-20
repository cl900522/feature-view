package com.siping.wechat;


/**
 * 微信接口的常量参数类
 */
public class WeChatConstant {

    /**
     * 官方提供的服务url，使用时需要将部分参数做替换，如ACCESS_TOKEN需要替换为具体的access_token值
     */
    public static final String GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + "ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    public static final String GTE_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + "client_credential&appid=APPID&secret=APPSECRET";
    public static final String GET_OAUTH_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + "APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    public static final String GET_OAUTH_USER = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
    public static final String GET_USER_OPENID = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + "ACCESS_TOKEN&next_openid=NEXT_OPENID&lang=zh_CN";
    public static final String BATCH_GET_USER = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
    public static final String OAUTH_LINK = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx92e61f6e9bb81863&redirect_uri=http%3A%2F%2Fcrcc.sipingsoft.com%2Fwechat%2Foauth&response_type=code&scope=snsapi_base&state=123#wechat_redirect";;
	public static final String SEND_ALL_NEWS= "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";

	@Deprecated
	public static final String UPLOAD_FILE = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=FILE_TYPE";
	public static final String ADD_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=FILE_TYPE";
	public static final String UPLOAD_MESSAGE = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	public static final String CREATE_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
	public static final String GET_ALL_GROUPS = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
	public static final String GET_USER_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";
	public static final String UPDATE_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
	public static final String UPDATE_USER_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
	public static final String BATCH_UPDATE_USER_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=ACCESS_TOKEN";
	public static final String DELETE_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=ACCESS_TOKEN";
	public static final String UPDATE_REMARK = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN";
    public static final String CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    public static final String GET_MENU = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    public static final String DELETE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	public static final String JSON_ERRMSG_KEY = "errmsg";
    public static final String JSON_ERRCODE_KEY = "errcode";
}
