package com.v5ent.xiubit.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.toobei.common.TopApp;
import com.toobei.common.activity.TopLaunchActivity;
import com.toobei.common.entity.BaseResponseEntity;
import com.toobei.common.network.NetworkObserver;
import com.toobei.common.network.RetrofitHelper;
import com.v5ent.xiubit.BuildConfig;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.entity.DefaultConfig;
import com.v5ent.xiubit.network.httpapi.SysDefaultConfigApi;
import com.v5ent.xiubit.util.ParamesHelp;
import com.v5ent.xiubit.view.dialog.EnvironmentVersionsSelectDiolog;

import org.xsl781.utils.SystemTool;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Activity-启动页
 */
public class LaunchActivity extends TopLaunchActivity {
    private static final String TAG = "LaunchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //弹出环境提示框
        if (!"rel".equals(BuildConfig.FLAVOR)) {
            new EnvironmentVersionsSelectDiolog().show();
        }


    }


    @Override
    protected int getLaunchImgResource() {
        return R.drawable.img_launch;
    }

    @Override
    protected boolean tranAdvWeb() {
        if (TextUtils.isEmpty(openAdvWebUrl)) return false;
        WebActivityCommon.showThisActivity(this, openAdvWebUrl, "");// 跳转开屏广告
        return true;
    }

    @Override
    protected void tranActivity() {

        if (MyApp.getInstance().getDefaultSp().getAppFirstRun() || (MyApp.getInstance().isShowWelcome && MyApp.getInstance().getDefaultSp().getCurVersionFirstRun(SystemTool.getAppVersion(this)))) {
            skipActivity(this, WelcomeActivity.class);
        } else {
            String curUserGestruePsw = TopApp.getInstance().getCurUserSp().getCurUserGestrue();  //本地保存的手势密码
            if (MyApp.getInstance().getLoginService().isTokenExsit()) {
                if (TextUtils.isEmpty(curUserGestruePsw)) {   //登录过，但是没有设置手势密码
                    skipActivity(this, GestureSetActivity.class);
                } else {
                    skipActivity(this, GestureActivity.class);
                }
            } else {
                skipActivity(this, MainActivity.class);
            }
        }
    }

    @Override
    protected void getDefaultConfig() {
        // 获取配置信息
        RetrofitHelper.getInstance().getRetrofit().create(SysDefaultConfigApi.class).sysConfig(new ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<BaseResponseEntity<DefaultConfig>>() {
                    @Override
                    public void bindViewWithDate(BaseResponseEntity<DefaultConfig> response) {
                        MyApp.getInstance().getDefaultSp().setDefaultConfig(response.getData());
                        //只有当后台开启热修复，并且补丁版本号未安装过时才发起热修复补丁查询请求
                        int hotFixVersion = MyApp.getInstance().getDefaultSp().getHotFixVersion();
                        if ("true".equals(response.getData().android_hotfix_isopen)
                                && !("" + hotFixVersion).equals(response.getData().android_hotfix_patch_version)) {
                            MyApp.getInstance().qureHotfixPatch();

                        }

                    }

                });

//        new NetCachAsyncTask<DefaultConfigEntity>("clientGetDefaultConfig", 1 * 24 * 60, false, true) {
//            @Override
//            protected DefaultConfigEntity getData() throws Exception {
//                return MyApp.getInstance().getHttpService().clientGetDefaultConfig();
//            }
//
//            @Override
//            protected void onPost(Exception e, DefaultConfigEntity response, boolean isDataFromServer) {
//                if (e == null && response != null) {
//                    if (response.getCode().equals("0") && isDataFromServer) {
//                        MyApp.getInstance().getDefaultSp().setDefaultConfig(response.getData());
//                    } else {
//
//                    }
//                } else {
//                    //	com.tophlc.common.utils.ToastUtil
//                    //			.showCustomToast(getString(R.string.pleaseCheckNetwork));
//                }
//            }
//        }.execute();
    }


}
