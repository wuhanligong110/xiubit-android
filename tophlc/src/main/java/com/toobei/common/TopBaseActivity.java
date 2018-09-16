package com.toobei.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.toobei.common.event.FinishActivityEvent;
import com.toobei.common.event.RefreshAfterLoginEvent;
import com.toobei.common.utils.StatusBarUtil;
import com.toobei.common.view.FirstGuideView;
import com.toobei.common.view.HeaderLayout;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xsl781.utils.Logger;
import org.xsl781.utils.SystemTool;
import org.xsl781.utils.Utils;

public abstract class TopBaseActivity extends BaseFragmentActivity {

    /**
     * 用于对Fragment进行管理
     */
    protected FragmentManager fragmentManager;
    protected FragmentTransaction mFragmentTransaction;
    public Context appContext;
    public TopBaseActivity ctx;
    protected HeaderLayout headerLayout;
    protected boolean tranToLogin = true;
    private boolean initBus = true; //是否使用EventBus
    protected String TAG = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = getClass().getSimpleName();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        appContext = getApplicationContext();
        ctx = this;
        registBrocast();
        if (initBus) EventBus.getDefault().register(this);

    }

    protected void initStatusBarStyle() {
        //全屏主题
        StatusBarUtil.setTransparentForImageView(this,null);  //全屏
        //白色主题
//        StatusBarUtil.setColorNoTranslucent(ctx,getResources().getColor(R.color.WHITE)); //白色状态栏
//        StatusBarUtil.StatusBarLightMode(this);  //白底黑字
//        //蓝色主题
//        StatusBarUtil.setColorNoTranslucent(ctx,getResources().getColor(R.color.top_title_bg));

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            initStatusBarStyle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ctx = this;

        if (tranToLogin) {
            tranToLogin();
        }
    }
    public void addMineFirstGuide(String viewType, int drawableId) {
        ViewGroup vgFirstGuide = (ViewGroup) findViewById(R.id.root_view);
        vgFirstGuide.addView(new FirstGuideView(ctx, viewType, drawableId), -1, -1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(ctx);
        Utils.hideSoftInputView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //	System.out.println("====onStop" + getClass().getName());
        if (!SystemTool.isAppOnForeground(this)) {
            // app 进入后台
            TopApp.isActive = false;
        }
        TopApp.systime = System.currentTimeMillis();
//        Logger.e("TopApp.systime ="+TopApp.systime);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBraodcast);
        if (initBus) EventBus.getDefault().unregister(this);
//        Logger.d("activityOnDestory=====>"+ this.getClass().getName());
    }



    protected abstract Intent getGestureActivityIntent(TopBaseActivity activity);
    
    protected void tranToLogin() {
        //	System.out.println("=== MyBaseActivity === 是否激活状态 " + TophlcApp.isActive
        //			+ (System.currentTimeMillis() - TophlcApp.systime) * 0.001);
        if (!TopApp.isActive && tranToLogin) {
            // app 从后台唤醒，进入前台
            TopApp.isActive = true;
            if (TopApp.getInstance().getLoginService().curPhone != null && System.currentTimeMillis() - TopApp.systime > 2*60*1000) { //大於10秒生效
                //				Intent intent = new Intent(ctx, TopGestureActivity.class);
                String curUserGestruePsw = TopApp.getInstance().getCurUserSp().getCurUserGestrue();  //本地保存的手势密码
                Intent intent = getGestureActivityIntent(ctx);
                if (intent != null && !TextUtils.isEmpty(curUserGestruePsw)) {
                    intent.putExtra("reVerify", true);
                    showActivity(ctx, intent);
                    System.out.println("==== tranToLogin show GestureActivity");
                }
            }
        }
    }

    /**
     * todo 初始化EventBus 子类必须在父类的OnCreate之前调用
     */
    protected void initBus() {
        initBus = true;
    }

    protected FragmentManager getFragManager() {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        return fragmentManager;
    }

    protected FragmentTransaction ensureTransaction(boolean anim) {
        if (mFragmentTransaction == null) {
            getFragManager();
            mFragmentTransaction = fragmentManager.beginTransaction();
            if (anim) {
                mFragmentTransaction.setCustomAnimations(R.anim.activity_new, R.anim.activity_out, R.anim.activity_exit_in, R.anim.activity_exit_out);
            }
        }
        return mFragmentTransaction;
    }

    protected void commitTransactions() {
        try {
            if (mFragmentTransaction != null && !mFragmentTransaction.isEmpty()) {
                mFragmentTransaction.commit();
                mFragmentTransaction = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能：清除堆栈中的fragment
     */
    public void cleanFragmentBackStack() {
        if (fragmentManager == null) return;
        int backStackCount = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            fragmentManager.popBackStack();
        }

    }

    public void setSoftInputMode() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void hideSoftInputView() {
        Utils.hideSoftInputView(this);
    }

    private BroadcastReceiver myBraodcast = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent mintent) {
            if (tranToLogin && SystemTool.isAppOnForeground(ctx) && TopApp.isActive) {
                if (TopApp.getInstance().getLoginService().curPhone != null) {
                    //	Intent intent = new Intent(ctx, GestureActivity.class);
                    if (TopApp.getInstance().getLoginService().curPhone != null && System.currentTimeMillis() - TopApp.systime > 10*60*1000) {
                        String curUserGestruePsw = TopApp.getInstance().getCurUserSp().getCurUserGestrue();  //本地保存的手势密码
                        Intent intent = getGestureActivityIntent(ctx);
                        if (intent != null && !TextUtils.isEmpty(curUserGestruePsw)) {
                            intent.putExtra("reVerify", true);
                            showActivity(ctx, intent);
                            System.out.println("====myBraodcast onReceive show GestureActivity");
                        }
                    }
                }
            }
        }
    };

    private void registBrocast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(myBraodcast, filter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)  // 刷新未读小红点
    public void finishActivityEvent(FinishActivityEvent event) {
        if (event.activityClass == this.getClass()){
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)  // 登陆之后刷新数据和页面
    public void refreshAfterLogin(RefreshAfterLoginEvent event) {
        refreshAfterLogin();
    }

    public void refreshAfterLogin() {
        Logger.d("refreshAfterLogin ==> "+ this.getLocalClassName());
        //具体交给子类实现
    }

    public void setHeadViewCoverStateBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.getLayoutParams().height = SystemTool.getStatusBarHeight(this);
        }
    }

    /**
     * 找到指定根布局，然后让根布局自动找到其子组件，再递归注册监听器
     *
     * @param view 指定布局
     */
    public void setupUIListenerAndCloseKeyboard(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    com.toobei.common.utils.KeyboardUtil.hideKeyboard(ctx);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup ) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUIListenerAndCloseKeyboard(innerView);
            }
        }
    }
}
