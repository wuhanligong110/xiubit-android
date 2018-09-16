package com.toobei.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.AdvertisementOpening;
import com.toobei.common.entity.AdvertisementOpeningDataEntity;
import com.toobei.common.entity.AppVersionEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.common.utils.ReadCachAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.utils.Utils;

import org.xsl781.utils.Logger;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class TopLaunchActivity extends TopBaseActivity implements OnClickListener {

    private static final int TRANACTIVITY = 0;
    private static final int SHOW_ADVERTISE = 1;
    private static final String TAG = "TopAdvertiseLaunchActivity";
    private int delayTime = 1500;
    private MyHandler mHandler;
    private View rootView;
    //图片缓存加载完成
    private boolean loadPicComplete = false;
    private String version;
    // 广告
    private ImageView advIV;
    private TextView timeTV;
    private int time = 3;
    private Timer timer;
    public String openAdvWebUrl;//开屏广告跳转链接
    private RelativeLayout advRL;
    private RelativeLayout logoRL;
    private boolean hasTranAdvWeb = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tranToLogin = false;
        //	EMChatManager.getInstance().logout();
        getDefaultConfig();
        getAdvertisementData();
        getAppVersion();
        if (!TopApp.getInstance().isLaunched) {
            rootView = initView();
            setContentView(rootView);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.launch_alpha);
            rootView.startAnimation(animation);
        } else {
            delayTime = 0;
        }
        mHandler = new MyHandler();
        //！emId = lcs53c2d442740c3b5fa644e705ecfb1a6a1464056061135,emPwdnWEevBoK5bwWQmJGOWmnQE6opYo=
        mHandler.sendEmptyMessageDelayed(SHOW_ADVERTISE, delayTime);
//                mHandler.sendEmptyMessageDelayed(TRANACTIVITY, delayTime);// 不显示广告
        //Log.d(TAG, "不显示广告=============");

        setTranslucentStatus(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasTranAdvWeb) {
            tranActivity();
        }
    }

    @Override
    protected void onDestroy() {
        setContentView(R.layout.view_null);
        super.onDestroy();
        System.gc();

    }

    /**
     * 功能：初始化视图，返回rootView
     *
     * @return
     */
    protected View initView() {

        View rootView = LayoutInflater.from(ctx).inflate(R.layout.activity_launch, null);
        ImageView img = (ImageView) rootView.findViewById(R.id.img_launch);
        ImageView imgBg = (ImageView) rootView.findViewById(R.id.img_launch_bg);
        img.setImageResource(getLaunchImgResource());
        imgBg.setBackgroundColor(ContextCompat.getColor(ctx,R.color.WHITE));
        TextView textVersion = (TextView) rootView.findViewById(R.id.text_version);
        version = Utils.getAppVersion(ctx);
        textVersion.setText("版本 " + version);

        advRL = (RelativeLayout) rootView.findViewById(R.id.lunch_advRL);

        logoRL = (RelativeLayout) rootView.findViewById(R.id.lunch_logoRL);
        advIV = (ImageView) rootView.findViewById(R.id.lunch_advertiseIV);
        advIV.setOnClickListener(this);
        timeTV = (TextView) rootView.findViewById(R.id.lunch_TimeTV);
        rootView.findViewById(R.id.rl_lunch_TimeTV).setOnClickListener(this);

        return rootView;

    }


    /**
     * 功能：跳转界面处理
     */
    protected abstract void tranActivity();

    /**
     * 功能：获取默认配置信息，开异常任务跑
     */
    protected abstract void getDefaultConfig();

    protected abstract int getLaunchImgResource();

    @Override
    public void onClick(View v) {
        //跳转广告
        if (v.getId() == R.id.lunch_advertiseIV) {
            if (tranAdvWeb()) {
                if (timer != null) {
                    timer.cancel();
                }
                hasTranAdvWeb = true;
            }
        } else if (v.getId() == R.id.rl_lunch_TimeTV) {
            if (timer != null) {
                timer.cancel();
            }
            tranActivity();
        }
    }

    /**
     * 开屏广告跳转
     */
    protected abstract boolean tranAdvWeb();

    /**
     * 使用静态的内部类，不会持有当前对象的引用
     */
    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SHOW_ADVERTISE:

                    showAdvertise();

                    break;

                case TRANACTIVITY:
                    tranActivity();
                    break;
            }

        }

    }

    private void getAppVersion() {
        new MyNetAsyncTask(ctx, false) {
            AppVersionEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp.getInstance().getHttpService().clientAppversion();
            }

            @Override
            protected void onPost(Exception e) {
                // 登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        TopApp.getInstance().appVersion = response.getData();
                    } else {

                    }
                } else {
                    ToastUtil.showCustomToast((Activity) ctx, getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    /**
     * 功能： 从缓存获取开屏广告的数据
     */
    protected void getAdvertisementData() { //缓存线程获取缓存的广告图片
        new ReadCachAsyncTask<AdvertisementOpeningDataEntity>("advertisementopening") {


            @Override
            protected void onPost(Exception e, AdvertisementOpeningDataEntity result) {

                if (e == null && result != null) {

                    if (result.getCode().equals("0")) { //  有缓存加载广告

                        List<AdvertisementOpening> advDatas = result.getData().getDatas();

                        AdvertisementOpening advertisementOpening = advDatas.get(0);
                        Logger.d(" 缓存线程获取缓存的广告图片 data   " + advertisementOpening.toString());
                        String imgUrl = advertisementOpening.getImgUrl();
                        openAdvWebUrl = advertisementOpening.getLinkUrl();

                        if (imgUrl == null) {

                            return;
                        }

                        //显示缓存的数据
                        ImageLoader.getInstance().displayImage(imgUrl, advIV, PhotoUtil.normalImageOptions, new ImageLoadingListener() {

                            @Override
                            public void onLoadingStarted(String arg0, View arg1) {
                            }

                            @Override
                            public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                            }

                            @Override
                            public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                                loadPicComplete = true;
                            }

                            @Override
                            public void onLoadingCancelled(String arg0, View arg1) {

                            }
                        });

                    } else {

                        Logger.e(result.getErrorsMsgStr());
                    }
                } else {
                    Logger.e(e.toString());
                }

            }
        }.execute();
    }

    /**
     * 显示广告页
     */

    public void showAdvertise() {

        if (advIV == null || !loadPicComplete) {//加载完成
            tranActivity();
            return;
        }

        advRL.setVisibility(View.VISIBLE);
        logoRL.setVisibility(View.INVISIBLE);
        timer = new Timer();
        timer.schedule(task, 1000, 1000);
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    time--;
                    timeTV.setText("跳过(" + time + "s)");
                    if (time <= 0) {
                        if (timer != null) {
                            timer.cancel();
                        }
                        tranActivity();
                    }
                }
            });
        }
    };

//    /**
//     * 获取首页banner类型
//     *
//     * @return appType    number	理财师1，投资端2
//     */
//    protected abstract int getAppType();

    @Override
    protected Intent getGestureActivityIntent(TopBaseActivity activity) {
        return null;
    }

}
