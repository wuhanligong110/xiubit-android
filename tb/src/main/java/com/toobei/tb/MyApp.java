package com.toobei.tb;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.easemob.chat.EMChatManager;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.utils.UpdateViewCallBack;
import com.toobei.tb.activity.LoginActivity;
import com.toobei.tb.activity.MainActivity;
import com.toobei.tb.service.AccountService;
import com.toobei.tb.service.ChatService;
import com.toobei.tb.service.HttpService;
import com.toobei.tb.service.LoginService;
import com.toobei.tb.service.UserService;
import com.toobei.tb.util.PrefDao;

import org.xsl781.ui.ActivityStack;
import org.xsl781.utils.MD5Util;

public class MyApp extends TopApp {
    public static synchronized MyApp getInstance() {
        return (MyApp) _theApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _theApp = this;
    }

    @Override
    protected boolean isOpenLog() {
        return BuildConfig.LogOpen;
    }

    @Override
    public void initImportParams() {
        appName = "tb";
        appPathRela = "/tophlc/" + appName + "/";
        IS_DEBUG = BuildConfig.IS_DEBUG;//是否是调试模式 发布时false
        IS_HTTP_TEST = BuildConfig.IS_DEBUG;//是否只采用http请求 发布时全部设置为false
        appPackageName = "com.toobei.tb";

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);  ////这步分包必须要做，否则在android 4.4 及以下的机子上会出问题
    }

    public synchronized HttpService getHttpService() {
        return HttpService.getInstance();
    }

    @Override
    public synchronized ChatService getChatService() {
        return ChatService.getInstance();
    }

    @Override
    public synchronized UserService getUserService() {
        return UserService.getInstance();
    }

    @Override
    public synchronized LoginService getLoginService() {
        return LoginService.getInstance();
    }

    @Override
    public synchronized AccountService getAccountService() {
        return AccountService.getInstance();  //获取账户信息
    }

    public synchronized PrefDao getCurUserSp() {
        if (currentUserPrefDao == null && MyApp.getInstance().getLoginService().curPhone != null && MyApp.getInstance().getLoginService().isServerLogin) {
            currentUserPrefDao = new PrefDao(this, MD5Util.MD5("toobei_" + MyApp.getInstance().getLoginService().curPhone));
        } else if (MyApp.getInstance().getLoginService().curPhone != null) {
            return new PrefDao(this, MD5Util.MD5("toobei_" + MyApp.getInstance().getLoginService().curPhone));
        } else {
            return getDefaultSp();
        }
        return (PrefDao) currentUserPrefDao;
    }

    public synchronized PrefDao getDefaultSp() {
        return new PrefDao(this, MD5Util.MD5("toobei_defaultsp"));
    }

    @Override
    public void logOut() {
        // MyApp.getInstance().getCurUserSp().setCurUserGestrue(null); //清除手势密码
        super.logOut();
        final TopBaseActivity activity = (TopBaseActivity) ActivityStack.getInstance().topActivity();
        if (activity == null) return;
        getLoginService().logoutFromServer(activity, new UpdateViewCallBack<Boolean>() {

            @Override
            public void updateView(Exception e, Boolean object) {
                EMChatManager.getInstance().logout();
                // 2016/12/26 0026 退出登录后跳转MainActivity
                ActivityStack.getInstance().finishOthersActivity(ActivityStack.getInstance().topActivity().getClass());
                //清空缓存数据
                getLoginService().clearLoginCach();
                getAccountService().clearAccoutService();
                MyApp.getInstance().getDefaultSp().setCurUserPhone(null);
                //先跳主页，防止点击X退出登录页后直接退出了整个应用
                activity.showActivity(activity, MainActivity.class);
                activity.showActivity(activity, LoginActivity.class);
            }
        });
    }


    @Override
    public void logOut(boolean isLogOutFromServer) {
        super.logOut();
        final TopBaseActivity activity = (TopBaseActivity) ActivityStack.getInstance().topActivity();
        if (activity == null) return;
        EMChatManager.getInstance().logout();
        ActivityStack.getInstance().finishOthersActivity(ActivityStack.getInstance().topActivity().getClass());
        //清空缓存数据
        getLoginService().clearLoginCach();
        getAccountService().clearAccoutService();
        MyApp.getInstance().getDefaultSp().setCurUserPhone(null);
        //先跳主页，防止点击X退出登录页后直接退出了整个应用
        activity.showActivity(activity, MainActivity.class);
        activity.showActivity(activity, LoginActivity.class);
        if (isLogOutFromServer) {
            getLoginService().logoutFromServer(activity, null);
        }
    }
    
    @Override
    public void logOutEndNoSikp(){
        super.logOut();
        final TopBaseActivity activity = (TopBaseActivity) ActivityStack.getInstance().topActivity();
        if (activity == null) return;
        EMChatManager.getInstance().logout();
        //清空缓存数据
        getLoginService().clearLoginCach();
        getAccountService().clearAccoutService();
    }

    @Override
    public void skipLogin() {
        TopBaseActivity activity = (TopBaseActivity) ActivityStack.getInstance().topActivity();
        activity.showActivity(activity, LoginActivity.class);
    }

    @Override
    public boolean isLoginOpen() {
        return BuildConfig.LogOpen;
    }


    @Override
    public int getHeadBgColor() {
        return R.color.top_title_bg;
    }

    @Override
    public String getShareDefaultImageUrl() {
        return "95cb38fb9ed0ff753706f62e3be7b144";
    }
}
