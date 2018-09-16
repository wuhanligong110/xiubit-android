package com.v5ent.xiubit.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.toobei.common.TopApp;
import com.toobei.common.service.TopAppObject;
import com.toobei.common.utils.BitmapUtil;
import com.toobei.common.utils.PathUtils;
import com.toobei.common.utils.ScreenShotUtils;
import com.toobei.common.view.MyWebView;
import com.v5ent.xiubit.MyTitleBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.entity.CommonTabEntity;
import com.v5ent.xiubit.util.C;
import com.v5ent.xiubit.view.popupwindow.OneImageSharePopupWindow;

import org.xsl781.utils.MD5Util;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/7/3
 */

public class PersonbusinessCardActivity extends MyTitleBaseActivity {

    @BindView(R.id.tabLayout)
    CommonTabLayout mTabLayout;
    @BindView(R.id.webview)
    MyWebView mWebview;
    @BindView(R.id.shareTv)
    TextView mShareTv;
    @BindView(R.id.nextIv)
    ImageView mNextIv;
    //    @BindView(R.id.progressView)
//    View mProgressView;
    private int type = 1; // 1: 推荐理财师 2： 邀请客户
    private final String baseUrl = TopApp.getInstance().getHttpService().getBaseH5urlByAppkind() + C.URL_PERSON_CARD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_person_business_card;
    }

    private void initView() {
        //tab
        ArrayList<CustomTabEntity> tabList = new ArrayList<>();
        tabList.add(new CommonTabEntity("推荐理财师"));
        tabList.add(new CommonTabEntity("邀请客户"));
        mTabLayout.setTabData(tabList);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                type = type == 1 ? 2 : 1;
                mWebview.loadUrl(baseUrl + type);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        headerLayout.showLeftBackButton();
        headerLayout.showTitle("个人名片");
        initWebView();

        mWebview.loadUrl(baseUrl + type);


    }


    private void initWebView() {

        mWebview.setAppObject(new TopAppObject() {
        });

    }

    

    @OnClick({R.id.shareTv,R.id.nextIv})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.shareTv:
                try {
                    //截图
                    Bitmap bm = ScreenShotUtils.shot(this, mWebview);
                    String filename = MD5Util.MD5(System.currentTimeMillis()+"");
                    //保存
                    File file = BitmapUtil.saveBitmap(PathUtils.getImagePath(), filename, bm, false);
                    Uri uri = Uri.fromFile(file);
                    new OneImageSharePopupWindow(ctx,file.getAbsolutePath()).showPopupWindow(mWebview);
                    //分享单张图片
//                    ShareService shareService = new ShareService();
//                    shareService.shareImageToWeixinFriend(file.getAbsolutePath());
//分享多张图片
//                    imageList.add(uri);
//                    ShareUtils.shareToWxcircle(ctx, imageList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.nextIv:
                mWebview.loadUrl("javascript:androidEvent("+"'"+"swiperEvent"+"'"+")");
                break;
        }
    }
    

   

}
