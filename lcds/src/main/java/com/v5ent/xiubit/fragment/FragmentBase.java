package com.v5ent.xiubit.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.squareup.leakcanary.RefWatcher;
import com.toobei.common.event.RefreshAfterLoginEvent;
import com.toobei.common.utils.ToastUtil;
import com.v5ent.xiubit.MyApp;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xsl781.utils.Logger;
import org.xsl781.utils.SystemTool;
import org.xsl781.utils.Utils;

public class FragmentBase extends RxFragment implements OnTouchListener {
    public Activity ctx;
    private boolean initBus=true;
    protected Context mAppContext;
    protected ProgressDialog mRefreshDialog;  //刷新时使用进度dialog

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppContext = MyApp.getInstance().getApplicationContext();
        ctx = getActivity();
        if (initBus) EventBus.getDefault().register(this);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // 拦截触摸事件，防止泄露下去
        view.setOnTouchListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        Utils.hideSoftInputView(getActivity());
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (initBus)
        EventBus.getDefault().unregister(this);
        RefWatcher refWatcher = MyApp.getInstance().getRefWatcher();
        if (refWatcher!= null) refWatcher.watch(this);
    }

    // onTouch事件 将上层的触摸事件拦截
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    protected  void initBus(){
        initBus=true;
    }

    public void setHeadViewCoverStateBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.getLayoutParams().height = SystemTool.getStatusBarHeight(mAppContext);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)  // 登陆之后刷新数据和页面
    public void refreshAfterLogin(RefreshAfterLoginEvent event) {
        refreshAfterLogin();
    }

    public void refreshAfterLogin() {
        Logger.d("refreshAfterLogin ==>" + this.getClass().getName());
        //具体交给子类实现
    }

    /**
     * 用于数据页面刷新时展示
     */
    public void showRefreshProgress() {
        if (this != null ) {
            if (mRefreshDialog == null) {
                mRefreshDialog = ToastUtil.showCustomDialog();
            } else if (!mRefreshDialog.isShowing()) {
                mRefreshDialog.show();
            }
        }
    }

    /**
     * /**
     * 用于数据页面刷新结束后展示
     */

    public void dismissRefreshProgress() {
        if (mRefreshDialog != null && mRefreshDialog.isShowing() && this != null ) {
            mRefreshDialog.dismiss();
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
