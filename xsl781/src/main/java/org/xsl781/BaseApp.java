package org.xsl781;

import org.xsl781.db.DataBase;
import org.xsl781.db.DataBaseConfig;
import org.xsl781.http.HttpConfig;

import android.app.Application;

public abstract class BaseApp extends Application {

	protected String spFileName;

	protected DataBase mDataBase;
	protected DataBaseConfig dbConfig;

	protected HttpConfig httpConfig;
	protected Http mHttp;

	public static int displayWidth = 0;
	public static int displayHeight = 0;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	//SharedPreferences 操作 =================================================

	public String getSpFileName() {
		return spFileName;
	}

	//DB 操作============================================================

	/**
	 * 获得当前dbConfig 对应的 DataBase 对象
	 * 直接操作数据库
	 */
	public DataBase getLiteDB(){
		return null;
	}

	/**
	 * 关闭当前dbConfig  对应的 DataBase 对象
	 */
	public synchronized void dbClose() {
		DB.close(mDataBase);
	}

	//Http 操作============================================================

	public synchronized HttpConfig getHttpConfig() {
		return httpConfig;
	}

	/**
	 * 自定义配置
	 * 
	 * @param httpConfig
	 */
	public synchronized void setHttpConfig(HttpConfig httpConfig) {
		this.httpConfig = httpConfig;
	}

	public Http getHttp() {
		if (mHttp == null) {
			if (httpConfig == null) {
				httpConfig = new HttpConfig();
			}
			mHttp = new Http(httpConfig);
		}
		return mHttp;
	}

}
