package com.toobei.common;

import java.text.SimpleDateFormat;

import org.xsl781.utils.TimeUtils;

import android.content.Context;
import android.os.Debug;
import android.view.InflateException;

import com.toobei.common.utils.PathUtils;

/**
 * .
 * Author: raezlu
 * Date: 13-9-9
 * Time: 下午8:11
 */
public class OOMHelper {

	private final static String TAG = "OOMHelper";

	// private final static String OOM_DIR = "oom";
	private final static String OOM_SUFFIX = ".hprof";

	private static ThreadLocal<SimpleDateFormat> sLocalDateFormat = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss.SSS");
		}
	};

	private OOMHelper() {
		// static usage.
	}

	public static boolean dumpHprofIfNeeded(Context context, Throwable e) {
		if (!TopApp.getInstance().IS_DEBUG) {
			// dump only in debug mode.
			return false;
		}
		if (context == null) {
			// no valid context.
			return false;
		}
		if (e == null || !isOOM(e)) {
			// dump only when oom.
			return false;
		}

		String fileName = "crash_OOM_" + TimeUtils.getDateStr() + OOM_SUFFIX;

		try {
			Debug.dumpHprofData(PathUtils.getExceptionPath() + fileName);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return true;
	}

	public static boolean isOOM(Throwable e) {
		int loopCount = 0;
		while (e != null && loopCount++ < 5) {
			if (isOOMInner(e)) {
				return true;
			}
			e = e.getCause();
		}
		return false;
	}

	private static boolean isOOMInner(Throwable e) {
		if (e == null) {
			return false;
		}
		return (e instanceof OutOfMemoryError) || (e instanceof InflateException);
	}
}
