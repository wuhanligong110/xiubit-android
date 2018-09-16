package com.v5ent.xiubit;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.easemob.chat.EMChatManager;
import com.maning.librarycrashmonitor.MCrashMonitor;
import com.maning.librarycrashmonitor.listener.MCrashCallBack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.taobao.sophix.SophixManager;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.BaseResponseEntity;
import com.toobei.common.entity.SysConfigData;
import com.toobei.common.network.NetworkObserver;
import com.toobei.common.network.RetrofitHelper;
import com.toobei.common.network.httpapi.SysConfigApi;
import com.toobei.common.utils.UpdateViewCallBack;
import com.v5ent.xiubit.activity.LoginActivity;
import com.v5ent.xiubit.activity.MainActivity;
import com.v5ent.xiubit.service.AccountService;
import com.v5ent.xiubit.service.ChatService;
import com.v5ent.xiubit.service.HttpService;
import com.v5ent.xiubit.service.LoginService;
import com.v5ent.xiubit.service.UserService;
import com.v5ent.xiubit.util.ParamesHelp;
import com.v5ent.xiubit.util.PrefDao;
import com.v5ent.xiubit.view.LcdsRefresHeader;
import com.umeng.analytics.MobclickAgent;

import org.xsl781.ui.ActivityStack;
import org.xsl781.utils.Logger;
import org.xsl781.utils.MD5Util;
import org.xsl781.utils.SystemTool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import tech.linjiang.pandora.Pandora;


public class MyApp extends TopApp {
    public boolean isNewUser; //是否是新用户
    private RefWatcher mRefWatcher;


    public static synchronized MyApp getInstance() {
        return (MyApp) _theApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /**非主进程 后面的方法不再调用，避免重复初始化
         * 注意即使 TopAPP onCreate 中做过判断return掉了，myApp 的onCreate 依然会被调用（原因不明） 
         * 所以在 Appclication的所有层级的类中都需要做判断*/
        if (!isAppMainProcess()) return;

        if (!LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            mRefWatcher = LeakCanary.install(this);
        }

        saveHotfixPatchVersion();

        initFont();

        initRefreshView();
        updataWindowDisplayInfo();
//        getPageName();
//        initSkinTheme();
        ininCashPage();
        initUITools();
    }

    private void initUITools() {
        //UI调试工具
//        目前 UETool 提供以下功能：
//
//        移动屏幕上的任意 view，如果重复选中一个 view，将会选中其父 view
//        查看/修改常用控件的属性，比如修改 TextView 的文本内容、文本大小、文本颜色等等
//        如果你的项目里正在使用 Fresco 的 DraweeView 来呈现图片，那么 UETool 将会提供更多的属性比如图片 URI、默认占位图、圆角大小等等
//        你可以很轻松的定制任何 view 的属性，比如你想查看一些额外的业务参数
//        有的时候 UETool 为你选中的 view 并不是你想要的，你可以选择打开 ValidView，然后选中你需要的 View
//        显示两个 view 的相对位置关系
//        显示网格栅栏，方便查看控件是否对齐
//        https://github.com/eleme/UETool/blob/master/README_zh.md
//        UETool.showUETMenu();


        if (BuildConfig.LogOpen) {
            Pandora.init(this);
        }
//        Pandora.init(this).open() ;
    }


    private void ininCashPage() {
        MCrashMonitor.init(this, BuildConfig.LogOpen, new MCrashCallBack() {
            @Override
            public void onCrash(File file) {
                //可以在这里保存标识，下次再次进入把日志发送给服务器
//                Log.i(TAG, "CrashMonitor回调:" + file.getAbsolutePath());
            }
        });
    }

    private void saveHotfixPatchVersion() {
        if (SophixStubApplication.patchVersion != 0) {
            getDefaultSp().setHotFixVersion(SophixStubApplication.patchVersion);
        }
    }

    public void qureHotfixPatch() {
        int hotFixNumToday = getDefaultSp().getHotFixNumToday(getCurTodayDateStr());
        if (hotFixNumToday < 15) {
            Logger.d("sophix:请求修复补丁---今日第" + hotFixNumToday + "次");
            SophixManager.getInstance().queryAndLoadNewPatch();
            MyApp.getInstance().getDefaultSp().setHotFixNumToday(getCurTodayDateStr());
        } else {
            Logger.d("sophix:超过每日允许请求次数");
        }
    }


    private String getCurTodayDateStr() {
        Calendar c = Calendar.getInstance();
        String date = "" + c.get(Calendar.YEAR) + c.get(Calendar.MONTH) + c.get(Calendar.DAY_OF_MONTH);
        Logger.d("sophix_date ==" + date);
        return date;
    }

    private void initSkinTheme() {
        RetrofitHelper.getInstance().getRetrofit().create(SysConfigApi.class)
                .sysConfig(new ParamesHelp()
                        .put("appType", "1")
                        .put("configKey", "switch_config")
                        .put("configType", "lcs_app_skin_config")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<BaseResponseEntity<SysConfigData>>() {
                    @Override
                    public void bindViewWithDate(BaseResponseEntity<SysConfigData> response) {
                        MyApp.getInstance().getDefaultSp().setSkinTheme(response.getData().configValue);
                    }
                });
    }

    @Override
    protected boolean isOpenLog() {
        return BuildConfig.LogOpen;
    }

    private void getPageName() {
        PackageManager pManager = getPackageManager();
        List<PackageInfo> appList = pManager.getInstalledPackages(0);

        for (int i = 0; i < appList.size(); i++) {
            PackageInfo pinfo = appList.get(i);
            //set Package Name   
            String packageName = pinfo.applicationInfo.packageName;
            Logger.e("packageName" + packageName);

        }
    }

    /**
     * 上传设备屏幕参数至友盟统计
     */
    private void updataWindowDisplayInfo() {
        try {
            //获取屏幕宽高数据

            int width = displayWidth;
            int height = displayHeight;
            Logger.d("WindowDimens===>" + getResources().getDimension(R.dimen.w1) + ""
                    + "WindowWidth===>" + com.jungly.gridpasswordview.Util.px2dp(this, width)
                    + "\nWindowHeight===>" + com.jungly.gridpasswordview.Util.px2dp(this, height));
            //将屏幕的dp和px 数据上传友盟统计
            Map<String, String> map_value = new HashMap<String, String>();
            map_value.put("screen_px", width + "*" + height);
            map_value.put("screen_dp", com.jungly.gridpasswordview.Util.px2dp(this, width) + "*" + com.jungly.gridpasswordview.Util.px2dp(this, height));
            map_value.put("androidOsVersion", Build.VERSION.RELEASE);
            map_value.put("Phone_model", Build.BRAND + "___" + Build.MODEL);
            String cpuAbi = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String[] supportedAbis = Build.SUPPORTED_ABIS;
                if (supportedAbis.length > 0) cpuAbi = supportedAbis[0];
            } else {
                cpuAbi = Build.CPU_ABI;
            }
            map_value.put("cpu_abi", cpuAbi);
            Logger.d("cpu_abi", cpuAbi);
            //统计测试设备
            if (BuildConfig.LogOpen) {
                map_value.put("TestDeviceid", "model_" + Build.BRAND + "___" + Build.MODEL + "&&os_" + Build.VERSION.RELEASE + "&&id_" + SystemTool.getDeviceid(this));
            }

            MobclickAgent.onEventValue(this, "DeviceInfo", map_value, 0);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initRefreshView() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setHeaderHeight(80)
                        .setReboundDuration(500);
                //指定为经典Header，默认是 贝塞尔雷达Header
//                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);

                return new LcdsRefresHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                layout.setFooterHeight(40).setEnableLoadmore(false);
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);   //这步分包必须要做，否则在android 4.4 及以下的机子上会出问题
    }

    public RefWatcher getRefWatcher() {
        return mRefWatcher;
    }


    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 为了区别测试设备与正常设备，我们需要您提供测试设备的识别信息。为此我们提供了一个专用函数来帮助您完成设备信息采集，您可以按照以下步骤进行：
     * <p>
     * 1.将 getDeviceInfo 函数拷贝至您的代码中，并进行调用。
     * 2.这段代码将产生以下格式的IDE日志输出，即设备识别信息
     *
     * @param context
     * @return
     */
    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 全局替换字体
     */
    private void initFont() {
        Typeface fontPingFang = Typeface.createFromAsset(getAssets(), "fonts/PingFang-Medium.ttf");
        try {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, fontPingFang);
        } catch (NoSuchFieldException e) {
            Logger.e(e.toString());
        } catch (IllegalAccessException e) {
            Logger.e(e.toString());
        }

    }

    @Override
    public void initImportParams() {
        appPackageName = "com.v5ent.xiubit";
        appName = "lcds";
        appPathRela = "/tophlc/" + appName + "/";

        //选择对应的BuildType 运行时会自动选择
        IS_DEBUG = BuildConfig.IS_DEBUG;//是否是调试模式 发布时false
        IS_HTTP_TEST = BuildConfig.IS_DEBUG;//是否只采用http请求 发布时false

    }

    public synchronized HttpService getHttpService() {
        return HttpService.getInstance();
    }

    @Override
    public ChatService getChatService() {
        return ChatService.getInstance();
    }

    @Override
    public UserService getUserService() {
        return UserService.getInstance();
    }

    @Override
    public LoginService getLoginService() {
        return LoginService.getInstance();
    }

    @Override
    public AccountService getAccountService() {
        return AccountService.getInstance();
    }

    public synchronized PrefDao getCurUserSp() {
        if (currentUserPrefDao == null && MyApp.getInstance().getLoginService().curPhone != null && MyApp.getInstance().getLoginService().isServerLogin) {
            currentUserPrefDao = new PrefDao(this, MD5Util.MD5("xiaoniu_" + MyApp.getInstance().getLoginService().curPhone));
        } else if (MyApp.getInstance().getLoginService().curPhone != null) {
            return new PrefDao(this, MD5Util.MD5("xiaoniu_" + MyApp.getInstance().getLoginService().curPhone));
        } else {
            return getDefaultSp();
        }
        return (PrefDao) currentUserPrefDao;
    }

    public synchronized PrefDao getDefaultSp() {
        return new PrefDao(this, MD5Util.MD5("xiaoniu_defaultsp"));
    }

    @Override
    public void logOut() {
        super.logOut();
        final TopBaseActivity activity = (TopBaseActivity) ActivityStack.getInstance().topActivity();
        if (activity == null) return;
        getLoginService().logoutFromServer(activity, null);
        //EMChatManager.getInstance().logout();
        //ProductService.getInstance();
        getLoginService().clearLoginCach();
        getAccountService().clearAccoutService();
        ActivityStack.getInstance().finishAllActivity();
        activity.showActivity(activity, MainActivity.class);

    }

    @Override
    public void logOut(boolean isLogOutFromServer) {
        super.logOut();
        final TopBaseActivity activity = (TopBaseActivity) ActivityStack.getInstance().topActivity();
        if (activity == null) return;
        if (isLogOutFromServer) {
            getLoginService().logoutFromServer(activity, null);
        }

        if (MyApp.getInstance().getDefaultSp().getAppFirstRun()) {
            return;
        }
        getLoginService().clearLoginCach();
        getAccountService().clearAccoutService();
        EMChatManager.getInstance().logout();
        ActivityStack.getInstance().finishOthersActivity(LoginActivity.class);
        activity.showActivity(activity, MainActivity.class);
        activity.showActivity(activity, LoginActivity.class);

    }

    @Override
    public void logOutEndNoSikp() {
        super.logOutEndNoSikp();
        final TopBaseActivity activity = (TopBaseActivity) ActivityStack.getInstance().topActivity();
        if (activity == null) return;

        if (MyApp.getInstance().getDefaultSp().getAppFirstRun()) {
            return;
        }
        getLoginService().clearLoginCach();
        getAccountService().clearAccoutService();
        EMChatManager.getInstance().logout();

    }

    @Override
    public void skipLogin() {
        TopBaseActivity activity = (TopBaseActivity) ActivityStack.getInstance().topActivity();
        activity.showActivity(activity, LoginActivity.class);
    }


    /**
     * 从后台判断是否是新用户并且保存起来
     *
     * @return
     */
    public void getIsNewUserAndSave(final UpdateViewCallBack<String> callBack) {
//        if (TextUtils.isEmpty(getLoginService().token)){
//            return;
//        }
//        final TopBaseActivity activity = (TopBaseActivity) ActivityStack.getInstance().topActivity();
//        new MyNetAsyncTask(activity, false) {
//
//            UserIsNewEntity response;
//
//            @Override
//            protected void doInBack() throws Exception {
//                response = getHttpService()
//                        .getUseIsNew(MyApp.getInstance().getLoginService().token);
//            }
//
//            @Override
//            protected void onPost(Exception e) {
//                if (e == null && response != null) {
//                    if (response.getCode().equals("0")) {
//                        // 成功
//                        isNewUser = response.getData().getIsNew();
//                        Logger.d("isNews","myApp-setisNewUser"+isNewUser);
//                        if (callBack != null){
//                            callBack.updateView(null,null);
//                        }
//                    } else {
//                            ToastUtil.showCustomToast(activity, response.getErrorsMsgStr());
//                    }
//                } else {
//                        ToastUtil.showCustomToast(activity, getString(R.string.pleaseCheckNetwork));
//                }
//            }
//        }.execute();
    }

    @Override
    public int getHeadBgColor() {
        return R.color.top_title_bg;
    }

    @Override
    public String getShareDefaultImageUrl() {
        return "dfa3e35be331f6ec67566130f67820b9";
    }

    @Override
    public boolean isLoginOpen() {
        return BuildConfig.LogOpen;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }
}
