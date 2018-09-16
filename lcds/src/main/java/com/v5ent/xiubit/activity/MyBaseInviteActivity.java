package com.v5ent.xiubit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.toobei.common.TopApp;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.service.TopAppObject;
import com.toobei.common.utils.TextUrlUtils;
import com.toobei.common.view.MyWebView;
import com.toobei.common.view.OnMyWebViewListener;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyTitleBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.service.HttpService;
import com.v5ent.xiubit.util.DynamicSkipManage;
import com.v5ent.xiubit.view.popupwindow.SharePopupWindow;

import org.xsl781.utils.Logger;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/9/18
 */

abstract class MyBaseInviteActivity extends MyTitleBaseActivity {

    RelativeLayout mRootView;
    protected ShareContent shareContent;

    //	private Button btnSendQr;
    private SharePopupWindow popuWindow;
    protected MyWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.e("MyBaseInviteActivity ===> " + this.getLocalClassName());
        initView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.e("MyBaseInviteActivity ===> onNewIntent" + this.getLocalClassName());
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_invite_customer_sendqr;
    }

    protected void initView() {
        mWebView = (MyWebView) findViewById(R.id.webview);
        mRootView = (RelativeLayout) findViewById(R.id.rootView);
//   webView
        mWebView.setAppObject(getAppObject());
        mWebView.setRedirectUsable(false);
        mWebView.setOnMyWebViewListener(new OnMyWebViewListener() {
            @Override
            public void onReceivedTitle(String title) {

            }

            @Override
            public void onUrlRedirectCallBack(boolean isRedirectUsable, String url) {
                
            }

            @Override
            public void onUrlLoading(boolean isRedirectUsable, String redirectUrl) {
                if (!isRedirectUsable) {
                    WebActivityCommon.showThisActivity(ctx, redirectUrl, null);
                }
            }

            @Override
            public void onPageFinished(String url) {

            }

            @Override
            public void onPageStart(String url) {

            }

            @Override
            public void onLoadError(String url) {

            }
        });

    }

    private TopAppObject getAppObject() {
        return new MyTopAppObject();
    }



    private void showPopupWindow(ShareContent shareContent) {
        if (shareContent != null) {

            //添加分享 理财与投呗分享链接带参数标识与理财app内h5流量监控
            String link = shareContent.getLink();
            if (link != null && !link.contains("&fromApp=liecai&os=Android")) {
                shareContent.setLink(TextUrlUtils.addUrlStr(link, HttpService.getInstance().getShareUrlEndSuffix()));
            }

            //因为后台返回的是一个md5 拼接上图片服务器地址
            String shareImgurl = shareContent.getShareImgurl();
            if (!TextUtils.isEmpty(shareImgurl) && !shareImgurl.startsWith("http")) {
                shareImgurl = MyApp.getInstance().getHttpService().getImageServerBaseUrl() + shareImgurl;
                shareContent.setShareImgurl(shareImgurl);
            }
            popuWindow = new SharePopupWindow(ctx, null, shareContent);
            popuWindow.textShareName.setText("选择邀请方式");
//            popuWindow.setShareQr(true);
//            popuWindow.setInviteCfp(false);
//            popuWindow.setSharePurpose(SHAREPURPOSE_INVITECUSTOMERQR);
            popuWindow.showPopupWindow(mRootView);
        }
    }

    protected class MyTopAppObject extends TopAppObject {

        /**
         * token失效
         */
        @JavascriptInterface
        public void tokenExpired() {
            TopApp.loginAndStay = true;
            TopApp.getInstance().logOutEndNoSikp();
            startActivity(new Intent(ctx, LoginActivity.class));
        }

        @JavascriptInterface
        public void getSharedContent(final String json) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        shareContent = JSON.parseObject(json, ShareContent.class);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        /**
         * web内点击分享
         */
        @Override
        @JavascriptInterface
        public void getAppShareFunction(final String json) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Log.d("TopWebActivityCommon", "str========>" + json);
                    try {
                        ShareContent shareContent = JSON.parseObject(json, ShareContent.class);
                        Logger.d("getAppShareFunction ShareContent=====>" + shareContent.toString());
//                        System.out.println("shareContent=====>" + shareContent.toString());
                        if (shareContent != null) {
                            showPopupWindow(shareContent);
                        }
                    } catch (Exception e) {
                        Logger.e(e.toString());
                    }

                }
            });
        }


        @JavascriptInterface
        public void jumpToNativePage(String json) {
            DynamicSkipManage.skipActivityFromWeb(ctx, json);
        }

    }

    @Override
    public void refreshAfterLogin() {
        super.refreshAfterLogin();
        if (mWebView != null) {
            mWebView.reload();
        }
    }
}
