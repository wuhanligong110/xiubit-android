package com.toobei.tb.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.toobei.common.TopApp;
import com.toobei.common.activity.TopLaunchActivity;
import com.toobei.common.entity.HomePagerBannersDatasDataEntity;
import com.toobei.common.utils.NetCachAsyncTask;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.entity.DefaultConfigEntity;

import org.xsl781.utils.SystemTool;

public class LaunchActivity extends TopLaunchActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPagerBanners();
    }

    @Override
    protected boolean tranAdvWeb() {
        if (TextUtils.isEmpty(openAdvWebUrl)) return false;
        WebActivityCommon.showThisActivity(this, openAdvWebUrl, "");// 跳转开屏广告
        return true;
    }

    @Override
    protected int getLaunchImgResource() {
        return R.drawable.img_launch;
    }

    @Override
    protected void tranActivity() {
        if (MyApp.getInstance().getDefaultSp().getAppFirstRun() || (MyApp.getInstance().isShowWelcome && MyApp.getInstance().getDefaultSp().getCurVersionFirstRun(SystemTool.getAppVersion(this)))) {
            skipActivity(this, WelcomeActivity.class);
        } else {
            String curUserGestruePsw = TopApp.getInstance().getCurUserSp().getCurUserGestrue();  //本地保存的手势密码
            if (MyApp.getInstance().getLoginService().isCachPhoneExist() && !TextUtils.isEmpty(curUserGestruePsw)) {
                skipActivity(this, GestureActivity.class);
            } else {
                skipActivity(this, MainActivity.class);
            }
        }
    }

    @Override
    protected void getDefaultConfig() {

        // 获取配置信息
        new NetCachAsyncTask<DefaultConfigEntity>("clientGetDefaultConfig", 1 * 24 * 60, false, true) {
            @Override
            protected DefaultConfigEntity getData() throws Exception {
                return MyApp.getInstance().getHttpService().clientGetDefaultConfig();
            }

            @Override
            protected void onPost(Exception e, DefaultConfigEntity response, boolean isDataFromServer) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0") && isDataFromServer) {
                        MyApp.getInstance().getDefaultSp().setDefaultConfig(response.getData());
                    } else {

                    }
                } else {
                    com.toobei.common.utils.ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    /**
     * 获取banner 数据
     * advPlacement	广告位置描述	string   pc首页页中：pc_idx_middle (必填),pc端banner：pc_banner,平台banner:platform_banner,产品banner:product_banner
     * appType	端口	number                   理财师1，投资端2 （必填）
     */
    private void initPagerBanners() {
        new NetCachAsyncTask<HomePagerBannersDatasDataEntity>("homePagerBanners", 60 * 24 * 1, false, true) {

            @Override
            protected HomePagerBannersDatasDataEntity getData() throws Exception {
                return MyApp.getInstance().getHttpService().homepageAdvs(MyApp.getInstance().getLoginService().token, "app_home_page", "2");
            }
        }.execute();
        new NetCachAsyncTask<HomePagerBannersDatasDataEntity>("getInstitutionBannersDatas", 60 * 24 * 1, false, true) {

            @Override
            protected HomePagerBannersDatasDataEntity getData() throws Exception {
                return MyApp.getInstance().getHttpService().homepageAdvs(MyApp.getInstance().getLoginService().token, "platform_banner", "2");
            }
        }.execute();
    }


}
