package com.toobei.tb.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.toobei.common.TopApp;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.view.HeaderLayout;
import com.toobei.common.view.RemoteImageView;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyBaseActivity;
import com.toobei.tb.R;
import com.toobei.tb.entity.MyQRCodeEntity;
import com.toobei.tb.view.popupwindow.SharePopupWindow;
import com.umeng.analytics.MobclickAgent;

/**
 * 公司: tophlc
 * 类说明：我的二维码 邀请朋友
 *
 * @date 2016-3-17
 */
public class MyQRCodeActivity extends MyBaseActivity implements OnClickListener {

    private RemoteImageView qrIv;
    private Button shareBtn;
    private SharePopupWindow popuWindow;
    private ShareContent ShareContent;
    private String mobile;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_qrcode);
        initView();
    }

    private void initView() {
        headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
        headerLayout.showTitle(R.string.invite_friend);
        headerLayout.showRightTextButton(R.string.invite_recode, new OnClickListener() {
            @Override
            public void onClick(View view) {
                //邀请记录
                showActivity(ctx, MyInviteHistoryListActivity.class);
            }
        });
        headerLayout.showLeftBackButton();
        setTranslucentStatus(true);
        headerLayout.setHeadViewCoverStateBar();

        shareBtn = (Button) findViewById(R.id.shareBtn);
        qrIv = (RemoteImageView) findViewById(R.id.qrIv);

        shareBtn.setOnClickListener(this);
        mobile = MyApp.getInstance().getDefaultSp().getCurUserPhone();
        getMyQRCode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onEvent(MyQRCodeActivity.this, "my_invite");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shareBtn:
                if (ShareContent != null) {
                    if (popuWindow == null) {
                        popuWindow = new SharePopupWindow(ctx, mobile, ShareContent);
                        // TODO: 2017/1/13 0013 qq分享图片错误会调用appicon 而微信会报错  可以不设置分享图片 为null时微信和qq都会调用开发者平台配置的默认图片
                        ShareContent.setShareImgurl(TopApp.getInstance().getDefaultSp().getWechatShareLogo());
                        popuWindow.textShareName.setText("选择邀请方式");
                        popuWindow.setShareQr(true);
                    }
                    popuWindow.showPopupWindow(shareBtn.getRootView());
                } else {
                    com.toobei.common.utils.ToastUtil.showCustomToast("亲，拉取分享数据失败了，请稍后再试试吧~");
                }
                break;
        }
    }

    /*
     * 请求我的二维码
     */
    private void getMyQRCode(final boolean flag) {
        new MyNetAsyncTask(ctx, flag) {
            MyQRCodeEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().getMyQRCode(MyApp.getInstance().getLoginService().token);

            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        System.out.println("success");
                        url = response.getData().getUrl();

                        if (!url.startsWith("http"))
                            url = MyApp.getInstance().getHttpService().getImageServerBaseUrl() + url;
                        ShareContent = response.getData().getShareContent();
                        qrIv.setImageUri(url);
                    } else {
                        com.toobei.common.utils.ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    com.toobei.common.utils.ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

}
