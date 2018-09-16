package com.toobei.common;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.LauncherActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.igexin.sdk.PushManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.toobei.common.entity.AppVersion;
import com.toobei.common.ndk.TophlcNative;
import com.toobei.common.service.MyConnectionListener;
import com.toobei.common.service.MyEMEventlistener;
import com.toobei.common.service.OnDBUpdateListener;
import com.toobei.common.service.TooBeiPushService;
import com.toobei.common.service.TopAccountService;
import com.toobei.common.service.TopChatService;
import com.toobei.common.service.TopHttpService;
import com.toobei.common.service.TopLoginService;
import com.toobei.common.service.TopUserService;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.utils.TopPrefDao;

import org.xsl781.BaseApp;
import org.xsl781.DB;
import org.xsl781.db.DataBase;
import org.xsl781.db.DataBaseConfig;
import org.xsl781.ui.ActivityStack;
import org.xsl781.utils.Logger;
import org.xsl781.utils.MD5Util;
import org.xsl781.utils.SystemTool;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public abstract class TopApp extends BaseApp {
    /**
     * 需要继承初始化
     */
    public String appName = "lcds";
    protected String appPackageName = "com.v5ent.xiubit";

    public String appPathRela = "/tophlc/" + appName + "/";//app保存sd卡里的路径

    protected static TopApp _theApp = null;
    protected TopPrefDao currentUserPrefDao;
    protected DataBase defaultDB;
    /**
     * 用来当前版本号，是否显示欢迎界面
     */
    public boolean isShowWelcome = false;
    public boolean isLaunched = false;

    //发布时全部设置为false
    public boolean IS_DEBUG = false;//是否是调试模式
    public boolean IS_HTTP_TEST = false;//是否只采用http请求
    public AppVersion appVersion;
    private TophlcNative tophlcNative;

    public static boolean isActive = true; //是否在前台标志位
    public static long systime = 0;

    public static float widthRate = 1;   //实际屏幕宽度同1080px的比例
    public static float heightRate = 1;  //实际屏幕高度同1920px的比例
    public static boolean loginAndStay; //登陆后停留在当前页面
    public static String skinThemeType = "0"; // 0 默认 1，新年

    public static TopApp getInstance() {
        return _theApp;
    }


    public synchronized TophlcNative getTophlcNative() {
        if (tophlcNative == null) {
            tophlcNative = new TophlcNative();
        }
        return tophlcNative;
    }

    /**
     * 功能：重要参数 初始化
     * appName 理财师为lhlcs 金服为lhjf
     * IS_DEBUG 是否是调试模式 发布时为false
     * IS_HTTP_TEST 是否只采用http请求 发布时为false
     * appPackageName app包名
     */
    public abstract void initImportParams();

    public abstract TopPrefDao getCurUserSp();

    public abstract TopPrefDao getDefaultSp();

    public abstract TopHttpService getHttpService();

    public abstract TopChatService getChatService();

    public abstract TopUserService getUserService();

    public abstract TopLoginService getLoginService();

    public abstract TopAccountService getAccountService();

    public abstract int getHeadBgColor();
    public abstract String getShareDefaultImageUrl(); //默认分享图标地址

    @Override
    public void onCreate() {
        super.onCreate();
        initImportParams();
        _theApp = this;
        if (! isAppMainProcess()) return;

        CrashHandler.getInstance().init(getApplicationContext());
        initImageLoader(this);
        initEMIM();//初始化环信


        // TODO: 2017/1/18 0018
        PushManager.getInstance().initialize(this.getApplicationContext(), TooBeiPushService.class);
        //PushManager.getInstance().initialize(this.getApplicationContext());//旧版本使用初始化个推
        //        todo: 2017/1/18 0018  在个推SDK初始化后，注册上述 IntentService 类：
        //// com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
        //        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), com.getui.demo.DemoIntentService.class);
        //
        //        关于原有广播方式和新的IntentService方式兼容性说明：
        //        1. 如果调用了registerPushIntentService方法注册自定义IntentService，则SDK仅通过IntentService回调推送服务事件；
        //        2. 如果未调用registerPushIntentService方法进行注册，则原有的广播接收器仍然可以继续使用。


        initLogger();
        //	initLocation();
        //设置异常处理类
        //	LocationService.getInstance().initLocation(this);
        initBaseParams();
    }

    public boolean isAppMainProcess() {
        int pid = android.os.Process.myPid();
        String processAppName = getProcessName(pid);
        // 如果app启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
        if (processAppName == null || (!processAppName.equals(appPackageName) && !processAppName.equals(appPackageName+".debug"))) {
            //"com.easemob.chatuidemo"为demo的包名，换到自己项目中要改成自己包名
            // 则此application::onCreate 是被service 调用的，直接返回
            Logger.d("process:"+ processAppName+"; 非app主进程");
            return false;
        }
        Logger.d("process:"+ processAppName+"; app主进程");
        return true;
    }


    private void initLogger() {
        Logger.init("LOG::" + appName);
        if (!isOpenLog()) {
            Logger.close();
        }
    }
    
    abstract protected boolean isOpenLog();


    public DataBase getCurUserDB() {
        if (mDataBase == null && !TextUtils.isEmpty(TopApp.getInstance().getLoginService().curPhone)) {
            String phoneMd5 = MD5Util.MD5(TopApp.getInstance().getLoginService().curPhone);
            dbConfig = new DataBaseConfig(getApplicationContext(), phoneMd5, 1, new OnDBUpdateListener());
            if (IS_DEBUG) {
                System.out.println("curUserDB name is " + phoneMd5);
            }
            mDataBase = DB.newInstance(dbConfig);
        }
        return mDataBase;
    }

    public synchronized DataBase getDefaultDB() {
        if (defaultDB == null) {
            defaultDB = DB.newInstance(this, "lhlcs");
        }
        return defaultDB;
    }

    @SuppressWarnings("deprecation")
    private void initBaseParams() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (SystemTool.getSDKVersion() < 14) {//3.2版本以下
            TopApp.displayWidth = display.getWidth(); // 屏幕宽度（像素）
            TopApp.displayHeight = display.getHeight(); // 屏幕高度（像素）
        } else {
            Point size = new Point();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealSize(size);
            }else {
                display.getSize(size);
            }
            TopApp.displayWidth = size.x;
            TopApp.displayHeight = size.y;
        }
        widthRate = displayWidth / 1080f;
        heightRate = displayHeight / 1920f;

        TopApp.getInstance().getLoginService().curPhone = TopApp.getInstance().getDefaultSp().getCurUserPhone();
        if (TopApp.getInstance().getLoginService().curPhone != null) {
            TopApp.getInstance().getLoginService().token = TopApp.getInstance().getCurUserSp().getToken();
        }
    }

    /**
     * 功能：初始化环信SDK
     */
    private void initEMIM() {
        EMChat.getInstance().init(this);
        EMChat.getInstance().setDebugMode(IS_DEBUG);//在做打包混淆时，要关闭debug模式，如果未被关闭，则会出现程序无法运行问题
        //有选择性的接收某些类型event事件
        EMChatManager.getInstance().registerEventListener(new MyEMEventlistener(), new EMNotifierEvent.Event[]{EMNotifierEvent.Event.EventNewMessage, EMNotifierEvent.Event.EventOfflineMessage});
        //注册一个监听连接状态的listener
        EMChatManager.getInstance().addConnectionListener(new MyConnectionListener());
        EMChat.getInstance().setAutoLogin(true);
        //  emId = 7e3c4dde7cecbcce8ec10429467b48f81451975994705,emPwd = BQ/3R35faJ3Owjo3SaV8QktRY0g=

    }

    public void restart() {
        Intent intent = new Intent(this, LauncherActivity.class);
        PendingIntent restartIntent = PendingIntent.getActivity(this, 0, intent, 0);
        //退出程序
        AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, restartIntent); // 100ms钟后重启应用
        killApp();
    }

	/*	public void logOutAndRestart() {
            EMChatManager.getInstance().logout();
			TopApp.getInstance().getCurUserSp().setToken(null);
			TopApp.getInstance().getLoginService().curPhone = null;
			TopApp.getInstance().getLoginService().isServerLogin = false;
			ActivityStack.getInstance().finishAllActivity();
			restart();
		}*/

    /**
     * 功能：注销，跳转到登录界面
     */
    public void logOut(boolean isLogOutFromServer) {
        currentUserPrefDao = null;
        mDataBase = null;
    }

    public void logOut() {
        currentUserPrefDao = null;
        mDataBase = null;
    }

    public void logOutEndNoSikp(){
        currentUserPrefDao = null;
        mDataBase = null;
    }

    public void killApp() {
        ActivityStack.getInstance().AppExit(this);
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void exitApp() {
        ToastUtil.toastCancel();
        ActivityStack.getInstance().finishAllActivity();
    }

    /**
     * 初始化ImageLoader
     */
    public void initImageLoader(Context context) {
        //    File cacheDir = StorageUtils.getOwnCacheDirectory(context, PathUtils.getCacheDir());
        File cacheDir = com.toobei.common.utils.PathUtils.getCacheFileDir();
        ImageLoaderConfiguration config = PhotoUtil.getImageLoaderConfig(context, cacheDir);
        // Initialize ImageLoader with configuration.
        System.out.println("initImageLoader ImageLoader");
        ImageLoader.getInstance().init(config);
    }

    @SuppressWarnings({"rawtypes", "unused"})
    protected String getProcessName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                    // Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
                    // info.processName +"  Label: "+c.toString());
                    // processName = c.toString();
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
    
   abstract public void skipLogin();

    abstract public boolean isLoginOpen() ;
}
