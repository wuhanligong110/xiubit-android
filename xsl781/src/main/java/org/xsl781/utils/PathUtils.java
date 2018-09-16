package org.xsl781.utils;

import java.io.File;

import android.os.Environment;

public class PathUtils {
	/**
	 * 可预设基础路径
	 */
	public static String BASE_PATH = null;

	public static String getSDcardDir() {
		return Environment.getExternalStorageDirectory().getPath() + "/";
	}

	public static String checkAndMkdirs(String dir) {
		File file = new File(dir);
		if (file.exists() == false) {
			file.mkdirs();
		}
		return dir;
	}

	public static String getAppPath() {
		String dir = getSDcardDir() + BASE_PATH;
		if (BASE_PATH == null) {
			dir = getSDcardDir() + "org/xsl781/";
		}
		return checkAndMkdirs(dir);
	}

	public static String getCacheDir() {
		String dir = getAppPath() + "cache/";
		return checkAndMkdirs(dir);
	}

	public static String getChatFileDir() {
		String dir = getAppPath() + "files/";
		return checkAndMkdirs(dir);
	}

	public static String getChatFile(String id) {
		String dir = getChatFileDir();
		String path = dir + id;
		return path;
	}

	public static String getRecordTmpPath() {
		return getChatFileDir() + "tmp";
	}

	public static String getUUIDFilePath() {
		return getChatFile(Utils.uuid());
	}

	public static String getTmpPath() {
		return getAppPath() + "tmp";
	}
}
