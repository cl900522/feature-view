package com.clinix.askmobile.core.dao.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.clinix.askmobile.AskMobileApplication;

/**
 * 用于纯本地记录数据库的存储，放置更新主数据数据库时，对本地历史记录产生影响
 * @author Mx
 */
public class RecordSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "historyrecord.db";
    private static final int DATABASE_VERSION = 1;
    private static RecordSQLiteHelper dbHelper;
    private static Context context;

    /**
     * 返回SQLite数据库帮助实例
     * @return
     */
    public static RecordSQLiteHelper instance() {
        if (dbHelper == null || context != AskMobileApplication.instance()) {
            context = AskMobileApplication.instance();
            if (context != null) {
                dbHelper = new RecordSQLiteHelper(context);
            }
        }
        return dbHelper;
    }

    /**
     * 创建帮助类，CursorFactory设置为null,使用默认值
     * @param context
     */
    private RecordSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * 数据库第一次被创建时onCreate会被调用
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(this.getClass().toString(), "Creating database!");
        db.execSQL("CREATE TABLE [historyrecord] ( [id] INTEGER, [mainsym] INTEGER, [freqsym] INTEGER, [subsyms] VARCHAR, [ill] INTEGER, " 
        + "[iswilling] INTEGER DEFAULT (0), [serverid] CHAR, [date] DATE NOT NULL, [active] INTEGER DEFAULT (1), " + "[sex] CHAR, [depart] CHAR)");
    }

    /**
     * 如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(this.getClass().toString(), "Updateing database!");
    }
}
