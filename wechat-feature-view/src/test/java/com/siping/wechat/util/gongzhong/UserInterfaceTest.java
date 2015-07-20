package com.siping.wechat.util.gongzhong;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.siping.wechat.bean.WeChatAccount;
import com.siping.wechat.bean.WeChatGroup;
import com.siping.wechat.bean.WeChatUserInfo;
import com.siping.wechat.bean.WeChatUserLink;

public class UserInterfaceTest {
    private static WeChatAccount weChatAccount = new WeChatAccount();

    @Before
    public void createWeChatAccountTest() throws Exception {
        weChatAccount.setAppid("wx90f846fb078ece3c");
        weChatAccount.setAppsecret("eb550804df5ccf87a0d683ae7623066f ");
        weChatAccount.setToken("sipingsoft");
        BasicInterface.getAccessToken(weChatAccount);
    }
    @Ignore
    @Test
    public void getWeChatUserLinkTest() throws Exception {
        WeChatUserLink link = UserInterface.getWeChatUserLink(weChatAccount, null);
        Assert.assertNotNull(link);
    }

    @Test
    public void createGroupTest() throws Exception {
        WeChatGroup group = new WeChatGroup();
        group.setName("开发部门");
        group = UserInterface.createGroup(weChatAccount, group);
        Assert.assertNotNull(group.getId());

        /*UserInterface.deleteGroup(weChatAccount, group);*/
    }

    @Test
    public void getUserGroupTest() throws Exception {
        String openid = "ofRZAswWXAu-JSRvR96B_Jr_6ytM";
        WeChatGroup user = new WeChatGroup();
        user = UserInterface.getUserGroup(weChatAccount, openid);
        Assert.assertNotNull(user);
    }

    @Test
    public void updateGroupTest() throws Exception {
        WeChatGroup group = new WeChatGroup();
        group.setId("103");
        group.setName("修改103");
        UserInterface.updateGroup(weChatAccount, group);
    }

    @Test
    public void updateUserGroupTest() throws Exception {
        WeChatGroup group = new WeChatGroup();
        WeChatUserInfo user = new WeChatUserInfo();
        group.setId("103");
        user.setOpenid("ofRZAswWXAu-JSRvR96B_Jr_6ytM");
        UserInterface.updateUserGroup(weChatAccount, user, group);
    }    
}
