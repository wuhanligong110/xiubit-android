package com.toobei.common.utils;

import java.io.Serializable;

import org.xsl781.http.core.CachedTask;

/**
 * 只读缓存的线程
 * 类说明：{@link CachedTask}
 * @date 2015-9-23
 */
public abstract class ReadCachAsyncTask<T extends Serializable> extends CachedTask<Void, Void, T> {

	/**
	* 构造方法 默认缓存为所有用户共用，注意特定账号数据使用带curUserPhone参数的构造方法
	* @param key
	*            存储的key值，
	*/
	public ReadCachAsyncTask(String key) {
		super(PathUtils.getCacheDataDir(null), key, 43200, false, true);
	}

	/**
	 * 构造方法
	 * @param curUserPhone 表示该缓存只针对当前用户
	 * @param key 存储的key值，
	 */
	public ReadCachAsyncTask(String curUserPhone, String key) {
		super(PathUtils.getCacheDataDir(curUserPhone), key, 43200, false, true);
	}

	@Override
	protected T doConnectNetwork(Void... params) throws Exception {
		return null;
	}

	@Override
	protected void onPostExecuteSafely(T result, Exception e) throws Exception {
		onPost(e, result);
	}

	protected abstract void onPost(Exception e, T result);
}
