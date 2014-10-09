package com.clinix.askmobile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.clinix.askmobile.core.bean.Body;
import com.clinix.askmobile.core.bean.UserInfo;
import com.clinix.askmobile.core.bean.VersionInfo;
import com.clinix.askmobile.res.R;

/**
 * 应用类，可用于保存运行时的数据
 * @author SipingWork
 */
public class AskMobileApplication extends Application {
    /**
     * 发布的应用版本信息，可以和获取的latestVersion对比决定是否更新
     */
    private static final String CLIENT_VERSION = "1.0";
    private static String DATABASE_PATH = "";
    private final String DATABASE_FILENAME = "medical.db";

    private static AskMobileApplication instance;

    /**
     * 身体信息数据
     */
    private Body body;
    /**
     * 用户的账户信息-参考initUserAccount()函数
     */
    private UserInfo userInfo;
    /**
     * LoadingActivty会根据网络情况而检查最新的应用最新版本
     */
    private VersionInfo latestVersion;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public static AskMobileApplication instance() {
        return instance;
    }

    public VersionInfo getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(VersionInfo latestVersion) {
        this.latestVersion = latestVersion;
    }

    @Override
    public final void onCreate() {
        super.onCreate();
        instance = this;
        DATABASE_PATH = "/data/data/" + getPackageName() + "/databases";
        body = new Body(Body.WOMAN_SEX_STR);
        initUserAccount();
    }

    /**
     * 初始化用户信息（即未来的账户信息，现在还不是很完善，使用固定数据，有待改善……）
     */
    private void initUserAccount() {
        userInfo = new UserInfo();
        userInfo.setId(20140924L);
        userInfo.setGps("Lan:129831273;Lon:123198203");
        userInfo.setIp("193.128.3.54");

        try {
            TelephonyManager telManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            userInfo.setDeviceId(telManager.getDeviceId());
            userInfo.setTel(telManager.getLine1Number());
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "Failed to get device info");
        }
    }

    /**
     * 拷贝数据库文件
     */
    public void copyDataBaseFils() {
        /**
         * 拷贝数据库到android系统中
         */
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
            File dir = new File(DATABASE_PATH);
            if (!dir.exists())
                dir.mkdir();

            /**
             * 如果数据库文件存在则删除否则直接拷贝
             */
            if (!(new File(databaseFilename)).exists() || (new File(databaseFilename)).delete()) {
                Log.d(this.getClass().toString(), "初始化拷贝数据库文件");
                is = getResources().openRawResource(R.raw.medical);
                fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[8192];
                int count = 0;

                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }

                fos.close();
                is.close();
            }
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "拷贝数据库文件出错了");
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                Log.e(this.getClass().toString(), "关闭拷贝数据库文件流失败");
            }
        }
    }

    /**
     * 检查网络是否可用
     * @return
     */
    public boolean isNetWorkAvaliable() {
        /**
         * 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
         */
        try {
            ConnectivityManager connectivity = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                /**
                 * 获取网络连接管理的对象
                 */
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    /**
                     * 判断当前网络是否已经连接
                     */
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        return false;
    }
}
