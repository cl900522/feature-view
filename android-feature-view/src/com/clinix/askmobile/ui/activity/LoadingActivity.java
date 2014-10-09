package com.clinix.askmobile.ui.activity;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.clinix.askmobile.AskMobileApplication;
import com.clinix.askmobile.core.bean.HistoryRecord;
import com.clinix.askmobile.core.service.MedicalSer;
import com.clinix.askmobile.core.service.impl.MedicalCore;
import com.clinix.askmobile.core.service.impl.MedicalSerImpl;
import com.clinix.askmobile.res.R;
import com.clinix.askmobile.webservice.WebServiceProxy;
import com.clinix.askmobile.webservice.impl.WebServiceProxyImpl;

/**
 * 数据载入也，直接跳过
 * @author SipingWork
 */
public class LoadingActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        new LoadingAsyncTask().execute("Initializing system!");

        /**
         * 如果网络可用，则检查最新版本
         */
        if (AskMobileApplication.instance().isNetWorkAvaliable()) {
            checkLatestVersion();
            uploadHitoryRecord();
        }
    }

    @Override
    public void onStart() {
        new LoadingAsyncTask().execute("Initializing system!");
        super.onStart();
    }

    /**
     * 查询最新的version
     */
    private void checkLatestVersion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    WebServiceProxy webservice = new WebServiceProxyImpl();
                    AskMobileApplication.instance().setLatestVersion(webservice.getLatestVersion());
                    Log.d(this.getClass().toString(), AskMobileApplication.instance().getLatestVersion().toJson());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 启动AsyncTask线程用于初始化核心数据提供类 MedicalCore
     */
    private class LoadingAsyncTask extends AsyncTask<String, String, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            Log.d(this.getClass().toString(), params[0].toString());
            AskMobileApplication.instance().copyDataBaseFils();
            MedicalCore.instance().init();
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            super.onProgressUpdate(progress);
        }

        @Override
        protected void onPostExecute(Integer result) {
            jump2Activity(IndexActivity.class);
            super.onPostExecute(result);
        }
    }

    /**
     * 检查本地保存的记录，并上传
     * @author SipingWork
     */
    private void uploadHitoryRecord() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MedicalSer service = MedicalSerImpl.instance();
                    WebServiceProxy webservice = new WebServiceProxyImpl();
                    List<HistoryRecord> records = service.getHistoryRecordList("UPLOAD");
                    for (HistoryRecord record : records) {
                        String idStr = webservice.uploadHistoryRecord(record);
                        record.setServerId(idStr);
                        service.updateHistoryRecord(record);
                    }
                } catch (Exception e) {
                    Log.e(this.getClass().toString(), e.getMessage());
                }
            }
        }).start();
    }
}
