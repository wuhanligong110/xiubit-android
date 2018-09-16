package org.xsl781.utils;

public class Log {

	private final static int VERBOSE = 0;
	private final static int DEBUG = 1;
	private final static int INFO = 2;
	private final static int WARN = 3;
	private final static int ERROR = 4;
	private final static int DEFAULT_LEVEL = -1;

	private int level;

	private static String _clazz;
	private static Log log;
	private static String TAG = "xsl781";
	public static boolean isDBPrint = true;
	
	public static Log getDebugLog(Class<?> clazz, int l) {
		getLog(clazz);
		log.level = l;
		return log;
	}

	public static Log getLog(Class<?> clazz) {
		if(log == null)
			log = new Log(clazz);
		_clazz = "[" + clazz.getSimpleName() + "] ";
		return log;
	}

	public Log(Class<?> clazz) {
		_clazz = "[" + clazz.getSimpleName() + "] ";
		level = DEFAULT_LEVEL;
	}

	public void v(String message) {
		verbose(message, null);
	}

	public void d(String message) {
		debug(message, null);
	}

	public void i(String message) {
		info(message, null);
	}

	public void w(String message) {
		warn(message, null);
	}

	public void e(String message) {
		error(message, null);
	}

	public void verbose(String message, Throwable t) {
		if (VERBOSE < level)
			return;
		if (message != null)
			android.util.Log.v(TAG, _clazz + " Line: " + getLineNumber() + " : " + message);
		if (t != null)
			android.util.Log.v(TAG, _clazz + " Line: " + getLineNumber() + " : " + t.toString());
	}

	public void debug(String message, Throwable t) {
		if (DEBUG < level)
			return;
		if (message != null)
			android.util.Log.d(_clazz, _clazz + " Line: " + getLineNumber() + " : " + message);
		if (t != null)
			android.util.Log.d(_clazz, _clazz + " Line: " + getLineNumber() + " : " + t.toString());
	}

	public void info(String message, Throwable t) {
		if (INFO < level)
			return;
		if (message != null)
			android.util.Log.i(TAG, _clazz + " Line: " + getLineNumber() + " : " + message);
		if (t != null)
			android.util.Log.i(TAG, _clazz + " Line: " + getLineNumber() + " : " + t.toString());
	}

	public void warn(String message, Throwable t) {
		if (WARN < level)
			return;
		if (message != null)
			android.util.Log.w(TAG, _clazz + " Line: " + getLineNumber() + " : " + message);
		if (t != null)
			android.util.Log.w(TAG, _clazz + " Line: " + getLineNumber() + " : " + t.toString());
	}

	public void error(String message, Throwable t) {
		if (ERROR < level)
			return;
		if (message != null)
			android.util.Log.e(TAG, _clazz + " Line: " + getLineNumber() + " : " + message);
		if (t != null)
			android.util.Log.e(TAG, _clazz + " Line: " + getLineNumber() + " : " + t.toString());
	}

	private static int getLineNumber() {
		return Thread.currentThread().getStackTrace()[5].getLineNumber();
	}

	public static void setDBPrint(boolean isDBPrint) {
		Log.isDBPrint = isDBPrint;
	}

	public static void setTag(String tAG) {
		TAG = tAG;
	}
	
	
}