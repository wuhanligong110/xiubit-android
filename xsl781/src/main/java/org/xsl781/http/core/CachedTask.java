/*
 * 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.xsl781.http.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.xsl781.utils.CipherUtils;
import org.xsl781.utils.FileUtils;
import org.xsl781.utils.Log;
import org.xsl781.utils.StringUtils;

import android.annotation.TargetApi;
import android.os.Build;

/**
 * 本类主要用于获取网络数据，并将结果缓存至文件，文件名为key，缓存有效时间为value <br>
 * <b>注：</b> 参数Result需要序列化，否则不能或者不能完整的读取缓存。<br>
 * 
 * ,
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public abstract class CachedTask<Params, Progress, Result extends Serializable> extends
		SafeTask<Params, Progress, Result> {
	private String cachePath = "folderName"; // 缓存路径
	private String cacheName = "MD5_effectiveTime"; // 缓存文件名格式
	private long expiredTime = 0; // 缓存有效时间
	private String key; // 缓存以键值对形式存在
	private ConcurrentHashMap<String, Long> cacheMap; // (k:缓存md5(url),v:缓存时的时间)
	private boolean isUpdateData = false;
	private boolean isOnlyReadCach = false;
	protected boolean isDataFromServer = false;

	/**
	 * 构造方法
	 * 
	 * @param cachePath
	 *            缓存路径
	 * @param key
	 *            存储的key值，若重复将覆盖
	 * @param cacheTime
	 *            缓存有效期，单位：分
	 */
	public CachedTask(String cachePath, String key, long cacheTime) {
		if (StringUtils.isEmpty(cachePath) || StringUtils.isEmpty(key)) {
			throw new RuntimeException("cachePath or key is empty");
		} else {
			this.cachePath = cachePath;
			// 对外url，对内url的md5值（不仅可以防止由于url过长造成文件名错误，还能防止恶意修改缓存内容）
			this.key = CipherUtils.md5(key);
			// 对外单位：分，对内单位：毫秒
			this.expiredTime = TimeUnit.MILLISECONDS.convert(cacheTime, TimeUnit.MINUTES);
			Log.getLog(getClass()).d(cacheTime + "----" + expiredTime);
			this.cacheName = this.key + "_" + expiredTime;
			initCacheMap();
		}
	}

	/**
	 * 构造方法
	 * @param cachePath 缓存路径
	 * @param key  存储的key值，若重复将覆盖
	 * @param cacheTime  缓存有效期，单位：分
	 * @param isUpdateData 是否更新缓存 true时，不管缓存有效期，均更新缓存和缓存时间
	 * @param isOnlyReadCach 是否只读缓存，为true时，不网络请求，本地无缓存时，返回空
	 */
	public CachedTask(String cachePath, String key, long cacheTime, boolean isUpdateData,
			boolean isOnlyReadCach) {
		this.isUpdateData = isUpdateData;
		this.isOnlyReadCach = isOnlyReadCach;
		if (StringUtils.isEmpty(cachePath) || StringUtils.isEmpty(key)) {
			throw new RuntimeException("cachePath or key is empty");
		} else {
			this.cachePath = cachePath;
			// 对外url，对内url的md5值（不仅可以防止由于url过长造成文件名错误，还能防止恶意修改缓存内容）
			this.key = CipherUtils.md5(key);
			// 对外单位：分，对内单位：毫秒
			this.expiredTime = TimeUnit.MILLISECONDS.convert(cacheTime, TimeUnit.MINUTES);
			Log.getLog(getClass()).d(cacheTime + "----" + expiredTime);
			this.cacheName = this.key + "_" + expiredTime;
			initCacheMap();
		}
	}

	private void initCacheMap() {
		cacheMap = new ConcurrentHashMap<String, Long>();
		File folder = new File(cachePath);
		if (!folder.exists() || folder.listFiles() == null) {
			return;
		}
		for (File file : folder.listFiles()) {
			String name = file.getName();
			if (!StringUtils.isEmpty(name)) {
				String[] nameFormat = name.split("_");
				// 若满足命名格式则认为是一个合格的cache
				if (nameFormat.length == 2
						&& (nameFormat[0].length() == 32 || nameFormat[0].length() == 64 || nameFormat[0]
								.length() == 128)) {
					cacheMap.put(nameFormat[0], file.lastModified());
					Log.getLog(getClass()).d("-85---" + file.lastModified());
				}
			}
		}
	}

	/**
	 * 做联网操作,本方法运行在线程中
	 */
	protected abstract Result doConnectNetwork(Params... params) throws Exception;

	/**
	 * 做耗时操作
	 */
	@Override
	protected final Result doInBackgroundSafely(Params... params) throws Exception {
		Result res = null;
		//如果是仅读缓存
		if (isOnlyReadCach) {
			res = getResultFromCache();
			return res;
		}
		Long temp = cacheMap.get(key);
		long saveTime = (temp == null) ? 0 : temp; // 获取缓存时的时间
		long currentTime = System.currentTimeMillis(); // 获取当前时间
		if (isUpdateData || currentTime >= saveTime + expiredTime) { // 若缓存无效，联网下载,或者要求更新数据
			isDataFromServer = true;
			res = doConnectNetwork(params);
			if (res == null) {
				res = getResultFromCache();
			} else {
				saveCache(res);
			}
		} else { // 缓存有效，使用缓存
			res = getResultFromCache();
			if (res == null) { // 若缓存数据意外丢失，重新下载
				res = doConnectNetwork(params);
				saveCache(res);
			}
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	private Result getResultFromCache() {
		isDataFromServer = false;
		Result res = null;
		ObjectInputStream ois = null;
		File file = new File(cachePath + cacheName);
		if (!file.exists()) {
			return null;
		}
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			res = (Result) ois.readObject();
			System.out.println("======getResultFromCache" + res.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			FileUtils.closeIO(ois);
		}
		return res;
	}

	/**
	 * 保存数据，并返回是否成功
	 */
	private boolean saveResultToCache(Result res) {
		boolean saveSuccess = false;
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(FileUtils.getFileAndDeleteExist(
					cachePath, cacheName)));
			oos.writeObject(res);
			saveSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			FileUtils.closeIO(oos);
		}
		return saveSuccess;
	}

	/**
	 * 清空缓存文件（异步）
	 */
	public void cleanCacheFiles() {
		cacheMap.clear();
		File file = new File(cachePath);
		final File[] fileList = file.listFiles();
		if (fileList != null) {
			// 异步删除全部文件
			TaskExecutor.start(new Runnable() {
				@Override
				public void run() {
					for (File f : fileList) {
						if (f.isFile()) {
							f.delete();
						}
					}
				}// end run()
			});
		}// end if
	}

	/**
	 * 移除一个缓存
	 * 
	 * @param key
	 */
	public void remove(String key) {
		// 对内是url的MD5
		String realKey = CipherUtils.md5(key);
		for (Map.Entry<String, Long> entry : cacheMap.entrySet()) {
			if (entry.getKey().equals(realKey)) {
				cacheMap.remove(realKey);
				return;
			}
		}
	}

	/**
	 * 如果缓存是有效的，就保存
	 * 
	 * @param res
	 *            将要缓存的数据
	 */
	private void saveCache(Result res) {
		if (res != null) {
			saveResultToCache(res);
			cacheMap.put(cacheName, System.currentTimeMillis());
			Log.getLog(getClass()).d("-208---" + System.currentTimeMillis());
		}
	}
}
