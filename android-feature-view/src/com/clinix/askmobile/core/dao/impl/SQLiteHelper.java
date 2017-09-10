package com.clinix.askmobile.core.dao.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.clinix.askmobile.AskMobileApplication;

/**
 * 单例模式的数据库帮助类，通过DBHelper.instance().getWritableDatabase()即可操作系统核心数据库
 * @author Mx
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "medical.db";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteHelper dbHelper;
    private static Context context;

    /**
     * 返回SQLite数据库帮助实例
     * @return
     */
    public static SQLiteHelper instance() {
        if (dbHelper == null || context != AskMobileApplication.instance()) {
            context = AskMobileApplication.instance();
            if (context != null) {
                dbHelper = new SQLiteHelper(context);
            } else {
                Log.e("非法调用系统数据库", "应用没有启动！");
            }
        }
        return dbHelper;
    }

    /**
     * 创建帮助类，CursorFactory设置为null,使用默认值
     * @param context
     */
    private SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * 数据库第一次被创建时onCreate会被调用
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(this.getClass().toString(), "Creating database!");
    }

    /**
     * 如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(this.getClass().toString(), "Updateing database!");
    }
}
