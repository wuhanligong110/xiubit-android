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
package org.xsl781.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 系统信息工具包<br>
 * 
 * <b>创建时间</b> 2014-8-14
 * 
 * 
 * @version 1.1
 */
public final class SystemTool {

	/** 
	* 实现文本复制功能 
	* @param content 
	*/
	public static void copy(String content, Context context) {
		if (content == null)
			return;
		// 得到剪贴板管理器  
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setText(content.trim());
	}

	/** 
	* 实现粘贴功能 
	* @param context 
	* @return 
	*/
	public static String paste(Context context) {
		// 得到剪贴板管理器  
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		return cmb.getText().toString().trim();
	}

	/**
	 * 指定格式返回当前系统时间
	 */
	public static String getDataTime(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}

	/**
	 * 返回当前系统时间(格式以HH:mm形式)
	 */
	public static String getDataTime() {
		return getDataTime("HH:mm");
	}

	/**
	* 返回当前移动终端的唯一标识
	* 如果是GSM网络，返回IMEI；如果是CDMA网络，返回MEID
	*/
	public static String getPhoneIMEI(Context cxt) {
		TelephonyManager tm = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	public static String getPhoneNum(Context cxt) {
		TelephonyManager tm = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceid = tm.getDeviceId();
		String tel = tm.getLine1Number();
		String imei = tm.getSimSerialNumber();
		String imsi = tm.getSubscriberId();
		Logger.d("tel=" + tel + "  " + imei + " " + imei);
		return tel;
	}

	public static int getStatusBarHeight(Context ctx) {
		int result = 0;
		int resourceId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = ctx.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	public static String getDeviceid(Context cxt) {
//		如果是GSM网络，返回IMEI；如果是CDMA网络，返回MEID 长度 14-16位
		TelephonyManager tm = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceid = tm.getDeviceId();
        Logger.d("deviceid ===>","deviceid:"+  deviceid);
		//	String tel = tm.getLine1Number();
		//	String imei = tm.getSimSerialNumber();
		//	String imsi = tm.getSubscriberId();
		//	Logger.d("tel=" + tel + "  " + imei + " " + imei);
		/**
		 * 不具备通话功能的android设备，例如有些平板电脑，可能拿不到deviceid，也即IMEI，还有的情况
		 * 有些厂商系统可能会返回垃圾数据，所以先对deviceid进行判断，获取有问题的话
		 * 这时候可以通过获取Wifi Mac地址，注意，只有在打开wifi的情况下才能获取Wifi Mac
		 */
		if(TextUtils.isEmpty(deviceid) || deviceid.length() < 14 || deviceid.length() > 16){
			WifiManager manager = (WifiManager) cxt.getSystemService(Context.WIFI_SERVICE);
			if (manager != null) {
				deviceid = manager.getConnectionInfo().getMacAddress();
                Logger.d("deviceid ===>","MacAddress:"+  deviceid);
			}
		}
		return deviceid;
	}

	/**
	 * 获取手机系统SDK版本
	 * 
	 * @return 如API 17 则返回 17
	 */
	public static int getSDKVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}

	/**
	 * 获取系统版本
	 * 
	 * @return 形如2.3.3
	 */
	public static String getSystemVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 功能：获得手机型号
	 * @return
	 */
	public static String getDeviceModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 调用系统发送短信
	 */
	public static void sendSMS(Context cxt, String smsBody) {
		Uri smsToUri = Uri.parse("smsto:");
		Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
		intent.putExtra("sms_body", smsBody);
		cxt.startActivity(intent);
	}

	/**
	 * 判断网络是否连接
	 */
	public static boolean checkNet(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		return info != null && info.isConnected();// 网络是否连接
	}

	/**
	 * 判断是否为wifi联网
	 */
	public static boolean isWiFi(Context cxt) {
		ConnectivityManager cm = (ConnectivityManager) cxt
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// wifi的状态：ConnectivityManager.TYPE_WIFI
		// 3G的状态：ConnectivityManager.TYPE_MOBILE
		State state = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		return State.CONNECTED == state;
	}

	public static String getWifiMac(Context cxt) {
		WifiManager wm = (WifiManager) cxt.getSystemService(Context.WIFI_SERVICE);
		return wm.getConnectionInfo().getMacAddress();
	}

	public static Ringtone getDefaultRingtone(Context ctx, int type) {
		return RingtoneManager.getRingtone(ctx,
				RingtoneManager.getActualDefaultRingtoneUri(ctx, type));
	}

	public static Uri getDefaultRingtoneUri(Context ctx, int type) {
		return RingtoneManager.getActualDefaultRingtoneUri(ctx, type);
	}

	/**
	 * 隐藏系统键盘
	 * 
	 * <br>
	 * <b>警告</b> 必须是确定键盘显示时才能调用
	 */
	public static void hideKeyBoard(Activity aty) {
		((InputMethodManager) aty.getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(aty.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 判断当前应用程序是否后台运行
	 */
	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				// 后台运行
// 前台运行
				return appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
			}
		}
		return false;
	}

	/** 
	* 程序是否在前台运行 
	* @return 
	*/
	public static boolean isAppOnForeground(Context context) {
		// Returns a list of application processes that are running on the  
		// device  

		ActivityManager activityManager = (ActivityManager) context.getApplicationContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = context.getPackageName();
		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		if (appProcesses == null)
			return false;

		for (RunningAppProcessInfo appProcess : appProcesses) {
			// The name of the process that this object is associated with.  
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断手机是否处理睡眠
	 */
	public static boolean isSleeping(Context context) {
		KeyguardManager kgMgr = (KeyguardManager) context
				.getSystemService(Context.KEYGUARD_SERVICE);
		boolean isSleeping = kgMgr.inKeyguardRestrictedInputMode();
		return isSleeping;
	}

	/**
	 * 安装apk
	 * 
	 * @param context
	 * @param file
	 */
	public static void installApk(Context context, File file) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setType("application/vnd.android.package-archive");
		intent.setData(Uri.fromFile(file));
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public static void installApk(Context context, String path) {
		Intent intent1 = new Intent();
		intent1.setAction(Intent.ACTION_VIEW);
		File file = new File(path);
		Log.i("wzz", file.getAbsolutePath());
		intent1.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent1);
	}

	/**
	 * 获取当前应用程序的版本号
	 */
	public static String getAppVersion(Context context) {
		String version = "0";
		try {
			version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			throw new RuntimeException(SystemTool.class.getName() + "the application not found");
		}
		return version;
	}

	/**
	* 获取当前应用程序的版本号
	*/
	public static int getAppVersionCode(Context context) {
		int versionCode = 0;
		try {
			versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			throw new RuntimeException(SystemTool.class.getName() + "the application not found");
		}
		return versionCode;
	}

	/**
	 * 回到home，后台运行
	 */
	public static void goHome(Context context) {
		Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
		mHomeIntent.addCategory(Intent.CATEGORY_HOME);
		mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		context.startActivity(mHomeIntent);
	}

	/**
	 * 功能：跳转到电话界面
	 * @param activity
	 * @param phone
	 */
	public static void goPhone(Activity activity, String phone) {
		Intent intent = new Intent();
		//系统默认的action，用来打开默认的电话界面
		intent.setAction(Intent.ACTION_CALL);
		//需要拨打的号码
		intent.setData(Uri.parse("tel:" + phone));
		activity.startActivity(intent);
	}

	/**
	 * 获取应用签名
	 * 
	 * @param context
	 * @param pkgName
	 */
	public static String getSign(Context context, String pkgName) {
		try {
			PackageInfo pis = context.getPackageManager().getPackageInfo(pkgName,
					PackageManager.GET_SIGNATURES);
			return hexdigest(pis.signatures[0].toByteArray());
		} catch (NameNotFoundException e) {
			throw new RuntimeException(SystemTool.class.getName() + "the " + pkgName
					+ "'s application not found");
		}
	}

	/**
	 * 将签名字符串转换成需要的32位签名
	 */
	private static String hexdigest(byte[] paramArrayOfByte) {
		final char[] hexDigits = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101,
				102 };
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramArrayOfByte);
			byte[] arrayOfByte = localMessageDigest.digest();
			char[] arrayOfChar = new char[32];
			for (int i = 0, j = 0;; i++, j++) {
				if (i >= 16) {
					return new String(arrayOfChar);
				}
				int k = arrayOfByte[i];
				arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
				arrayOfChar[++j] = hexDigits[(k & 0xF)];
			}
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 获取设备的可用内存大小
	 * 
	 * @param cxt
	 *            应用上下文对象context
	 * @return 当前内存大小
	 */
	public static int getDeviceUsableMemory(Context cxt) {
		ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo mi = new MemoryInfo();
		am.getMemoryInfo(mi);
		// 返回当前系统的可用内存
		return (int) (mi.availMem / (1024 * 1024));
	}

	/**
	 * 清理后台进程与服务
	 * * <p>You must hold the permission
	 * {@link android.Manifest.permission#KILL_BACKGROUND_PROCESSES} to be able to
	 * call this method.
	 * 
	 * @param cxt
	 *            应用上下文对象context
	 * @return 被清理的数量
	 */
	public static int gc(Context cxt) {
		long i = getDeviceUsableMemory(cxt);
		int count = 0; // 清理掉的进程数
		ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
		// 获取正在运行的service列表
		List<RunningServiceInfo> serviceList = am.getRunningServices(100);
		if (serviceList != null)
			for (RunningServiceInfo service : serviceList) {
				if (service.pid == android.os.Process.myPid())
					continue;
				try {
					android.os.Process.killProcess(service.pid);
					count++;
				} catch (Exception e) {
					e.getStackTrace();
					continue;
				}
			}

		// 获取正在运行的进程列表
		List<RunningAppProcessInfo> processList = am.getRunningAppProcesses();
		if (processList != null)
			for (RunningAppProcessInfo process : processList) {
				// 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
				// 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
				if (process.importance > RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
					// pkgList 得到该进程下运行的包名
					String[] pkgList = process.pkgList;
					for (String pkgName : pkgList) {
						//     ======正在杀死包名：" + pkgName
						try {
							am.killBackgroundProcesses(pkgName);
							count++;
						} catch (Exception e) { // 防止意外发生
							e.getStackTrace();
							continue;
						}
					}
				}
			}
		//    KJLoger.debug("清理了" + (getDeviceUsableMemory(cxt) - i) + "M内存");
		return count;
	}

	public static void notify(Context context, String msg, String title, Class<?> toClz,
			int notifyId) {
		PendingIntent pend = PendingIntent.getActivity(context, 0, new Intent(context, toClz), 0);
		Notification.Builder builder = new Notification.Builder(context);
		int icon = context.getApplicationInfo().icon;
		builder.setContentIntent(pend).setSmallIcon(icon).setWhen(System.currentTimeMillis())
				.setTicker(msg).setContentTitle(title).setContentText(msg).setAutoCancel(true);

		NotificationManager man = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		man.notify(notifyId, builder.getNotification());
	}

	public static void notifyMsg(Context context, Class<?> clz, String title, String ticker,
			String msg, int notifyId) {
		int icon = context.getApplicationInfo().icon;
		PendingIntent pend = PendingIntent.getActivity(context, 0, new Intent(context, clz), 0);
		Notification.Builder builder = new Notification.Builder(context);
		if (ticker == null) {
			ticker = msg;
		}
		builder.setContentIntent(pend).setSmallIcon(icon).setWhen(System.currentTimeMillis())
				.setTicker(ticker).setContentTitle(title).setContentText(msg).setAutoCancel(true);
		NotificationManager man = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		man.notify(notifyId, builder.getNotification());
	}

	public static void notifyMsg(Context cxt, Class<?> toClz, int titleId, int msgId, int notifyId) {
		notifyMsg(cxt, toClz, cxt.getString(titleId), null, cxt.getString(msgId), notifyId);
	}

	public static void cancelNotification(Context ctx, int notifyId) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
		nMgr.cancel(notifyId);
	}

	/**
	 * 判断是否安装目标应用
	 * 
	 * @param packageName
	 *            目标应用安装后的包名
	 * @return 是否已安装目标应用
	 */
	public static boolean isInstallByread(String packageName) {
		return new File("/data/data/" + packageName).exists();
	}

	/**
	 * 先判断是否安装，已安装则启动目标应用程序，否则先安装
	 * @param packageName
	 *            目标应用安装后的包名
	 */
	public static void launchApp(Activity ctx, String packageName) {
		// 启动目标应用
		if (isInstallByread(packageName)) {
			// 获取目标应用安装包的Intent
			try {
				Intent intent = ctx.getPackageManager().getLaunchIntentForPackage(packageName);
				ctx.startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}