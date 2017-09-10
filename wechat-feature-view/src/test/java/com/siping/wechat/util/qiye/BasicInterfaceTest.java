package com.siping.wechat.util.qiye;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.siping.wechat.bean.QiyeAccount;

public class BasicInterfaceTest {
    private static QiyeAccount qiyeAccount = new QiyeAccount();

    @Before
    public void createWeChatAccountTest() throws Exception {
        qiyeAccount.setCorpid("wx6dabe0a83e3d8dcc");
        qiyeAccount.setCorpsecret("GoDFcUoQT7yttT15AA6CF8ckh-f9nE94EdzZ4eqHTs4FdyxpPmD2Bt8t2R_3i-j9");
        BasicInterface.getToken(qiyeAccount);
        Assert.assertNotNull(qiyeAccount.getAccessToken().getToken());
    }
    
    @Test
    public void AAtEST() throws Exception{
        String ip = BasicInterface.getIp(qiyeAccount);
        Assert.assertNotNull(ip);
    }
}
