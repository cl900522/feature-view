package com.siping.wechat.util.gongzhong;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.siping.wechat.bean.FileType;
import com.siping.wechat.bean.MediaFile;
import com.siping.wechat.bean.WeChatAccount;
import com.siping.wechat.bean.menu.ClickButton;
import com.siping.wechat.bean.menu.LocationSelectButton;
import com.siping.wechat.bean.menu.MediaIdButton;
import com.siping.wechat.bean.menu.Menu;
import com.siping.wechat.bean.menu.MenuContainer;
import com.siping.wechat.bean.menu.PicPhotoOrAlbumButton;
import com.siping.wechat.bean.menu.PicSysphotoButton;
import com.siping.wechat.bean.menu.PicWeixinButton;
import com.siping.wechat.bean.menu.ScancodePushButton;
import com.siping.wechat.bean.menu.ScancodeWaitmsgButton;
import com.siping.wechat.bean.menu.ViewButton;
import com.siping.wechat.bean.menu.ViewLimitedButton;

public class MenuInterfaceTest {
    private static WeChatAccount weChatAccount = new WeChatAccount();

    @Before
    public void createWeChatAccountTest() throws Exception {
        weChatAccount.setAppid("wx90f846fb078ece3c");
        weChatAccount.setAppsecret("eb550804df5ccf87a0d683ae7623066f ");
        weChatAccount.setToken("sipingsoft");
        BasicInterface.getAccessToken(weChatAccount);
    }

    @Test
    public void createMenuTest() throws Exception{
        Menu menu = new Menu();
        ClickButton musicButton = new ClickButton();
        musicButton.setName("今日歌曲");
        musicButton.setKey("V1001_TODAY_MUSIC");
        menu.addMenu(musicButton);

        MenuContainer container = new MenuContainer();
        container.setName("菜单");
        ViewButton viewButton = new ViewButton();
        viewButton.setName("搜索");
        viewButton.setUrl("http://www.soso.com/");
        container.addSubMenu(viewButton);

        ClickButton clickButton = new ClickButton();
        clickButton.setName("Death");
        clickButton.setKey("DEAD");
        container.addSubMenu(clickButton);

        ScancodePushButton scancodePushButton = new ScancodePushButton();
        scancodePushButton.setName("扫码堆事件");
        scancodePushButton.setKey("rselfmenu_0_1");
        container.addSubMenu(scancodePushButton);

        ScancodeWaitmsgButton scancodeWaitmsgButton = new ScancodeWaitmsgButton();
        scancodeWaitmsgButton.setName("扫码推事件且弹出");
        scancodeWaitmsgButton.setKey("rselfmenu_0_0");
        container.addSubMenu(scancodeWaitmsgButton);

        PicSysphotoButton picSysphotoButton = new PicSysphotoButton();
        picSysphotoButton.setName("弹出系统拍照发图");
        picSysphotoButton.setKey("rselfmenu_1_0");
        container.addSubMenu(picSysphotoButton);

        menu.addMenu(container);

        MenuContainer container2 = new MenuContainer();
        container2.setName("菜单");
        PicPhotoOrAlbumButton picPhotoOrAlbumButton = new PicPhotoOrAlbumButton();
        picPhotoOrAlbumButton.setName("拍照或者相册发图");
        picPhotoOrAlbumButton.setKey("rselfmenu_1_1");
        container2.addSubMenu(picPhotoOrAlbumButton);

        PicWeixinButton picWeixinButton = new PicWeixinButton() ;
        picWeixinButton.setName("微信相册发图");
        picWeixinButton.setKey("rselfmenu_1_2");
        container2.addSubMenu(picWeixinButton);
        
        LocationSelectButton locationSelectButton = new LocationSelectButton();
        locationSelectButton.setName("弹出地理位置选择器");
        locationSelectButton.setKey("rselfmenu_1_1");
        container2.addSubMenu(locationSelectButton);

        String filePath = ClassLoader.getSystemClassLoader().getResource("glyphicons-halflings.png").getPath();
        MediaFile mediaFile = new MediaFile();
        mediaFile.setFilePath(filePath);
        mediaFile.setFileType(FileType.IMAGE);;
        MediaInterface.addMateria(weChatAccount, mediaFile);
        System.out.println(mediaFile.getMediaId());
        MediaIdButton mediaIdButton = new MediaIdButton();
        mediaIdButton.setName("下发消息");
        mediaIdButton.setMediaId(mediaFile.getMediaId());
        container2.addSubMenu(mediaIdButton);
        
//        ViewLimitedButton viewLimitedButton = new ViewLimitedButton();
//        viewLimitedButton.setName("跳转图文消息");
//        viewLimitedButton.setMediaId(mediaId);
//        container2.addSubMenu(viewLimitedButton);
        menu.addMenu(container2);
        
        System.out.println(menu.generateJsonContent());
        MenuInterface.createMenu(weChatAccount, menu);
    }

    @Test
    public void getMenuTest() throws Exception {
        Menu menu = MenuInterface.getMenu(weChatAccount);
        System.out.println(menu.generateJsonContent());
    }

    @Ignore
    @Test
    public void deleteMenuTest() throws Exception {
        MenuInterface.deleteMenu(weChatAccount);
    }
}
