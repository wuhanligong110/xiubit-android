package org.xsl781.db;

import org.xsl781.db.assit.Checker;
import org.xsl781.db.impl.SQLiteHelper.OnUpdateListener;

import android.content.Context;

/**
 * 数据操作配置
 *
 * @author MaTianyu
 *         2013-6-2下午4:36:16
 */
public class DataBaseConfig {
    public static final String DEFAULT_DB_NAME = "xsl781.db";
    public static final int    DB_VERSION      = 1;

    //public boolean isDebugged;
    public Context context;
    public String dbName    = DEFAULT_DB_NAME;
    public int    dbVersion = DB_VERSION;
    public OnUpdateListener onUpdateListener;

    public DataBaseConfig(Context context) {
        this(context, DEFAULT_DB_NAME);
    }

    public DataBaseConfig(Context context, String dbName) {
        this(context, dbName, DB_VERSION, null);
    }

    public DataBaseConfig(Context context, String dbName, int dbVersion, OnUpdateListener onUpdateListener) {
        this.context = context.getApplicationContext();
        //this.isDebugged = isDebugged;
        if (!Checker.isEmpty(dbName)) this.dbName = dbName;
        if (dbVersion > 1) this.dbVersion = dbVersion;
        this.onUpdateListener = onUpdateListener;
    }

    @Override
    public String toString() {
        return "DataBaseConfig [mContext=" + context + ", mDbName=" + dbName + ", mDbVersion="
                + dbVersion + ", mOnUpdateListener=" + onUpdateListener + "]";
    }

}
