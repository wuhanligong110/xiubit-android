package com.toobei.common.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.toobei.common.entity.BaseResponseEntity;

import org.xsl781.http.core.CachedTask;
import org.xsl781.ui.ActivityStack;
import org.xsl781.ui.ViewInject;

/**
 * 类说明：{@link CachedTask}
 * @date 2015-9-23
 */
public abstract class NetCachAsyncTask<T extends BaseResponseEntity> extends
		CachedTask<Void, Void, T> {
	protected ProgressDialog dialog;
	protected Context ctx;
	protected boolean openDialog = false;
	protected boolean isUpdateData = false;

	/**
	* 构造方法 默认缓存为所有用户共用，注意特定账号数据使用带curUserPhone参数的构造方法
	* @param key
	*            存储的key值，若重复将覆盖
	* @param cacheTime
	*            缓存有效期，单位：分
	* @param openDialog 是否显示加载状态条
	* @param isUpdateData 是否更新缓存，true时，不管缓存有效期，均更新缓存和缓存时间
	*/
	public NetCachAsyncTask(String key, long cacheTime, boolean openDialog, boolean isUpdateData) {
		super(PathUtils.getCacheDataDir(null), key, cacheTime, isUpdateData, false);
		this.openDialog = openDialog;
		this.isUpdateData = isUpdateData;
	}

	/**
	 * 构造方法
	 * @param curUserPhone 表示该缓存只针对当前用户
	 * @param key
	 *            存储的key值，若重复将覆盖
	 * @param cacheTime
	 *            缓存有效期，单位：分
	 * @param openDialog 是否显示加载状态条
	 * @param isUpdateData 是否更新缓存，true时，不管缓存有效期，均更新缓存和缓存时间
	 */
	public NetCachAsyncTask(String curUserPhone, String key, long cacheTime, boolean openDialog,
			boolean isUpdateData) {
		super(PathUtils.getCacheDataDir(curUserPhone), key, cacheTime, isUpdateData, false);
		this.openDialog = openDialog;
		this.isUpdateData = isUpdateData;
	}

	public NetCachAsyncTask setOpenDialog(boolean openDialog) {
		this.openDialog = openDialog;
		return this;
	}

	public void setDialog(ProgressDialog dialog) {
		this.dialog = dialog;
	}

	@Override
	protected void onPreExecuteSafely() {
		if (openDialog) {
            ctx = ActivityStack.getInstance().topActivity();
            if (ctx != null){
			dialog = ViewInject.showSpinnerDialog((Activity) ctx);
            }
		}
	}

	@Override
	protected T doConnectNetwork(Void... params) throws Exception {
		T result = getData();
		if (result != null && result.getCode().equals("0")) {
			return result;
		}
		return null;
	}

	@Override
	protected void onPostExecuteSafely(T result, Exception e) throws Exception {
		if (openDialog && dialog != null) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
		onPost(e, result, isDataFromServer);
	}

	protected abstract T getData() throws Exception;

	protected void onPost(Exception e, T result, boolean isDataFromServer) {

	}
}
