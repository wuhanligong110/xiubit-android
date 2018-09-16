package com.toobei.common;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Debug;
import android.os.Looper;
import android.util.Log;

import com.toobei.common.utils.PathUtils;
import com.toobei.common.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import org.xsl781.utils.FileUtils;
import org.xsl781.utils.TimeUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 */
public class CrashHandler implements UncaughtExceptionHandler {
	private Thread.UncaughtExceptionHandler mDefaultHandler;// 系统默认的UncaughtException处理类
	private static CrashHandler INSTANCE;// CrashHandler实例
	private Context mContext;// 程序的Context对象
	public static final String TAG = "CatchExcep";

	//	private List<Activity> activityList = new LinkedList<Activity>();

	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {

	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		if (INSTANCE == null)
			INSTANCE = new CrashHandler();
		return INSTANCE;
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;

		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();// 获取系统默认的UncaughtException处理器
		Thread.setDefaultUncaughtExceptionHandler(this);// 设置该CrashHandler为程序的默认处理器
		MobclickAgent.setCatchUncaughtExceptions(true);
		/*	new Thread() {
				@Override
				public void run() {
					//启动时，检查是否有上次异常结束后保存的文件，上传
					//	if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					File[] files = FileUtils.getFiles(PathUtils.getExceptionPath());
					if (files != null) {
						for (File file : files) {
							//		sendAppCrashReport(mContext, file);
							FileUtils.deleteFile(file.getAbsolutePath());
						}
					}
					List<_Exception> list = ExceptionService.getInstance().getUploadException();
					if (list != null && list.size() > 0) {
						for (_Exception _exception : list) {
							try {
								MobclickAgent.reportError(mContext, _exception.getError());
								System.out.println("======== 上传错误日志 序号：" + _exception.getId());
								_exception.setUploaded(true);
								TophlcApp.getInstance().getDBLiteDefault().save(_exception);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					//	}
				}
			}.start();*/

	}

	/**
	 * 当UncaughtException发生时会转入该重写的方法来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			//如果用户没有处理则让系统默认的异常处理器来处理    
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Log.e(TAG, "异常: ", e);
			}
			//	ex.printStackTrace();
			Log.e(TAG, "==============未捕获异常==============", ex);
			if (System.currentTimeMillis()
					- TopApp.getInstance().getDefaultSp().getLastedCrashErrorTime() > 4000) {
				TopApp.getInstance().getDefaultSp()
						.setLastedRestartTime(System.currentTimeMillis());
				TopApp.getInstance().restart();
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Log.e(TAG, "" + e.getStackTrace());
				}
				TopApp.getInstance().killApp();
			}
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 *            异常信息
	 * @return true 如果处理了该异常信息;否则返回false.
	 */
	public boolean handleException(Throwable ex) {
		if (ex == null || mContext == null)
			return false;
		try {
			saveToSDCard(ex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//	TophlcApp.getInstance().getDefaultSp().setLastedCrashErrorPath(path);
		/*	_Exception e = new _Exception(crashReport, path);
			TophlcApp.getInstance().getDBLiteDefault().save(e);*/

		//使用Toast来显示异常信息    
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				ToastUtil.showCustomToast("机器闹情绪了，拼命安慰中。。。");
				//	Utils.showToast(mContext, "请抱歉,程序异常！", Toast.LENGTH_SHORT, false);
				Looper.loop();
			}
		}.start();
		return true;
	}

	/**
	 * 获取App安装包信息
	 * 
	 * @return
	 */
	private PackageInfo getPackageInfo(Context context) {
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	private void saveToSDCard(Throwable ex) throws Exception {
		String fileName = "crash-" + TimeUtils.getDateStr() + ".txt";
		File file = FileUtils.getSaveFile(PathUtils.getExceptionPath(), fileName);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
		// 导出发生异常的时间
		pw.println(TimeUtils.getCurrentDate(TimeUtils.FORMAT_DATE_TIME1));
		// 导出手机信息
		dumpPhoneInfo(pw);
		//导出内存信息
		pw.println(collectMemInfo());
		// 导出异常的调用栈信息
		ex.printStackTrace(pw);
		pw.println();
		pw.flush();
		pw.close();
		//如果是OOM，dump内存
		//	OOMHelper.dumpHprofIfNeeded(mContext, ex);
	}

	private String collectMemInfo() {
		StringWriter meminfo = new StringWriter();
		PrintWriter writer = new PrintWriter(meminfo);
		writer.append("***************************************************************\n");
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo memoryInfo = new MemoryInfo();
		activityManager.getMemoryInfo(memoryInfo);

		writer.append("memoryInfo.availMem=").append(String.valueOf(memoryInfo.availMem))
				.append("\n");
		writer.append("memoryInfo.lowMemory=").append(String.valueOf(memoryInfo.lowMemory))
				.append("\n");
		writer.append("memoryInfo.threshold=").append(String.valueOf(memoryInfo.threshold))
				.append("\n");

		getSelfMem(activityManager, writer);
		writer.close();
		return meminfo.toString();
	}

	private boolean getSelfMem(ActivityManager am, PrintWriter writer) {

		List<RunningAppProcessInfo> procInfo = am.getRunningAppProcesses();
		for (RunningAppProcessInfo runningAppProcessInfo : procInfo) {
			if (runningAppProcessInfo.processName.indexOf(mContext.getPackageName()) != -1) {
				int pids[] = { runningAppProcessInfo.pid };
				Debug.MemoryInfo self_mi[] = am.getProcessMemoryInfo(pids);

				writer.append("dalvikPrivateDirty=")
						.append(String.valueOf(self_mi[0].dalvikPrivateDirty)).append("\n");
				writer.append("dalvikPss=").append(String.valueOf(self_mi[0].dalvikPss))
						.append("\n");
				writer.append("nativePrivateDirty=")
						.append(String.valueOf(self_mi[0].nativePrivateDirty)).append("\n");
				writer.append("nativePss=").append(String.valueOf(self_mi[0].nativePss))
						.append("\n");
				writer.append("nativeSharedDirty=")
						.append(String.valueOf(self_mi[0].nativeSharedDirty)).append("\n");
				writer.append("otherPrivateDirty=")
						.append(String.valueOf(self_mi[0].otherPrivateDirty)).append("\n");
				writer.append("otherPss=").append(String.valueOf(self_mi[0].otherPss)).append("\n");
				writer.append("otherSharedDirty=")
						.append(String.valueOf(self_mi[0].otherSharedDirty)).append("\n");
				writer.append("TotalPrivateDirty=")
						.append(String.valueOf(self_mi[0].getTotalPrivateDirty())).append("\n");
				writer.append("TotalPss=").append(String.valueOf(self_mi[0].getTotalPss()))
						.append("\n");
				writer.append("TotalSharedDirty=")
						.append(String.valueOf(self_mi[0].getTotalSharedDirty())).append("\n");
				writer.append("***************************************************************\n");
				return true;
			}
		}

		return false;
	}

	private String dumpPhoneInfo(PrintWriter pw) throws NameNotFoundException {
		// 应用的版本名称和版本号
		PackageInfo pi = getPackageInfo(mContext);
		pw.print("App Version: ");
		pw.print(pi.versionName);
		pw.print('_');
		pw.println(pi.versionCode);
		pw.println();

		// android版本号
		pw.print("Android OS Version: ");
		pw.print(Build.VERSION.RELEASE);
		pw.print("_");
		pw.println(Build.VERSION.SDK_INT);
		pw.println();

		// 手机制造商
		pw.print("Vendor: ");
		pw.println(Build.MANUFACTURER);
		pw.println();

		// 手机型号
		pw.print("Model: ");
		pw.println(Build.MODEL);
		pw.println();

		// cpu架构
		pw.print("CPU ABI: ");
		pw.println(Build.CPU_ABI);
		pw.println();
		return pw.toString();
	}

}