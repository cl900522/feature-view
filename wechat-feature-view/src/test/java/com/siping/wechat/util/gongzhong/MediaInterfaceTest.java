package com.siping.wechat.util.gongzhong;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.siping.wechat.bean.FileType;
import com.siping.wechat.bean.MediaFile;
import com.siping.wechat.bean.PicTextMessage;
import com.siping.wechat.bean.WeChatAccount;

public class MediaInterfaceTest {
    private static WeChatAccount weChatAccount = new WeChatAccount();

    @Before
    public void createWeChatAccountTest() throws Exception {
        weChatAccount.setAppid("wx90f846fb078ece3c");
        weChatAccount.setAppsecret("eb550804df5ccf87a0d683ae7623066f ");
        weChatAccount.setToken("sipingsoft");
        BasicInterface.getAccessToken(weChatAccount);
    }

    @Test
    public void addMateriaTest() throws Exception {
        String filePath = ClassLoader.getSystemClassLoader().getResource("glyphicons-halflings.png").getPath();
        MediaFile file = new MediaFile();
        file.setFilePath(filePath);
        file.setFileType(FileType.IMAGE);
        MediaInterface.addMateria(weChatAccount, file);
        Assert.assertNotNull(file.getMediaId());
    }

    @Test
    public void sendPicTextMessageTest() throws Exception{
        String filePath = ClassLoader.getSystemClassLoader().getResource("glyphicons-halflings.png").getPath();
        MediaFile file = new MediaFile();
        file.setFilePath(filePath);
        file.setFileType(FileType.IMAGE);
        MediaInterface.uploadFile(weChatAccount, file);
        Assert.assertNotNull(file.getMediaId());

        PicTextMessage picTextMessage = new PicTextMessage();
        picTextMessage.setHeadImage(file);
        picTextMessage.setTitle("测试消息");
        picTextMessage.setAuthor("测试用户");
        picTextMessage.setContent("Nice to send!");
        picTextMessage.setSourceUrl("http://www.baidu.com");
        MediaInterface.uploadMessage(weChatAccount, picTextMessage);
        MediaInterface.sendAllMessege(weChatAccount, picTextMessage);
    }
}
