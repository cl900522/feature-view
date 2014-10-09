package com.clinix.askmobile.ui.activity;

import java.io.Serializable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 所有Activity的父类，提供部分功能封装
 * @author SipingWork
 */
public abstract class MainActivity extends Activity {
    /**
     * 跳转目标activity页面
     * @param targetAcitvityClass跳转的Activty类对象
     */
    public void jump2Activity(Class<?> targetAcitvityClass) {
        Log.d(this.getClass().toString(), "跳转Activity类：" + targetAcitvityClass.getName());
        Intent intent = new Intent();
        intent.setClass(this, targetAcitvityClass);
        startActivity(intent);
    }

    /**
     * 带参数跳转目标activity页面
     * @param targetAcitvityClass跳转的Activty类对象
     */
    public void jump2Activity(Class<?> targetAcitvityClass, Map<String, Serializable> params) {
        Log.d(this.getClass().toString(), "跳转Activity类：" + targetAcitvityClass.getName());
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        for (String key : params.keySet()) {
            bundle.putSerializable(key, params.get(key));
        }
        intent.putExtras(bundle);
        intent.setClass(this, targetAcitvityClass);
        startActivity(intent);
    }

    /**
     * 传参标记值
     */
    public static final String PARAMKEY_HISTORYRECORD = "history";
    public static final String PARAMKEY_SUBJECT = "subject";
    public static final String PARAMKEY_ISDIAGNOSIS = "isDiagnosis";

    /**
     * 获取跳转之前的Activity传入的参数
     */
    protected void getParamsFromPreActivity() {
    }

    private static boolean ISTOINDEX = false;
    private static final int WAITTIME = 1000;

    /**
     * Back键点击后如果连击事件在1s中内，则返回Index否则调用finish()返回上一页
     */
    @Override
    public void onBackPressed() {
        Timer tExit = new Timer();
        if (ISTOINDEX == false) {
            ISTOINDEX = true;
            finish();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    ISTOINDEX = false;
                }
            }, WAITTIME);
        } else {
            jump2Activity(IndexActivity.class);
        }
    }

    /**
     * 消息显示时长
     */
    private static final int MESSAGE_DURATION = 2000;

    /**
     * 以toast的方式显示消息
     * @param content
     */
    protected void showMessage(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(MESSAGE_DURATION);
        toast.show();
    }

    /**
     * 以toast的方式显示消息，参数为String资源编号
     * @param content
     */
    public void showMessage(int resourceId) {
        Toast toast = Toast.makeText(getApplicationContext(), resourceId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(MESSAGE_DURATION);
        toast.show();
    }

}
