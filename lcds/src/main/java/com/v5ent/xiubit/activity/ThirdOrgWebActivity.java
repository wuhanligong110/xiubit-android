package com.v5ent.xiubit.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.toobei.common.view.dialog.JumpProgressDialog;
import com.v5ent.xiubit.R;

import static com.v5ent.xiubit.service.JumpInsuranceService.TAG_USER_CENTER;

public class ThirdOrgWebActivity extends WebActivityCommon {
    private String mPostUrl;
    private String mOrgName;
    private static byte[] mPostDatas;
    private boolean isPostFlag;  //判断此activity 的启动方式是PostUrl还是默认
    private JumpProgressDialog jumpProgressDialog;
    private String webTag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isPostFlag = getIntent().getBooleanExtra("isPostFlag", false);
        mOrgName = getIntent().getStringExtra("orgName");
        mPostUrl = getIntent().getStringExtra("postUrl");
        webTag = getIntent().getStringExtra("webTag");
        super.onCreate(savedInstanceState);

        headerLayout.showRightTextButton("返回貅比特", this);
        headerLayout.showLeftBackButton(this);
    }

    @Override
    protected void loadUrl(String url) {
        if (!TextUtils.isEmpty(mOrgName)) {
            String diaLogText = "正在为您跳转至" + mOrgName;
            jumpProgressDialog = new JumpProgressDialog(this, diaLogText);
            jumpProgressDialog.show();

        }

        if (isPostFlag && mPostDatas != null) {
            mWebView.postUrl(mPostUrl, mPostDatas);
        } else {
            mWebView.loadUrl(url);
        }
    }

    public static void showThisActivityForPost(Context ctx, String orgProductUrl, byte[] postDatas, String orgName) {
        mPostDatas = postDatas;
        Intent intent = new Intent(ctx, ThirdOrgWebActivity.class);
        intent.putExtra("title", orgName);
        intent.putExtra("isPostFlag", true);
        intent.putExtra("orgName", orgName);
        intent.putExtra("postUrl", orgProductUrl);
        ctx.startActivity(intent);
    }


    public static void showThis(Context ctx, String url, boolean isHideTitle, String orgName, String webTag) {
        Intent intent = new Intent(ctx, ThirdOrgWebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("orgName", orgName);
        intent.putExtra("isHideTitle", isHideTitle);
        intent.putExtra("webTag", webTag);
        ctx.startActivity(intent);
    }

    @Override
    public void onPageFinished(String url) {
        super.onPageFinished(url);
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    jumpProgressDialog.dismiss();
                    if (TAG_USER_CENTER.equals(webTag)) {
                        //写入js隐藏标题头方法
                        mWebView.loadUrl("javascript:function hideHead() {document.getElementsByTagName('header')[0].style['display']='none';}");
                        //执行方法
                        mWebView.loadUrl("javascript:hideHead();");
    
                    } ;
    
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUrlLoading(boolean isRedirectUsable, String redirectUrl) {
        super.onUrlLoading(isRedirectUsable, redirectUrl);
        if (redirectUrl.equals("https://cps.qixin18.com/m/lq1009128/")) {
            Intent intent = new Intent(ctx, MainActivity.class);
            intent.putExtra("skipTab", "p0");
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rightContainer:
                finish();
                break;
            case R.id.backBtn:
                if (mWebView != null && mWebView.canGoBack()) {

//                        //获取webView的浏览记录  
//                        WebBackForwardList mWebBackForwardList = mWebView.copyBackForwardList();
//                        //这里的判断是为了让页面在有上一个页面的情况下，跳转到上一个html页面，而不是退出当前activity  
//                        if (mWebBackForwardList.getCurrentIndex() > 0) {
//                            String historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex() - 1).getUrl();
//                            if (!historyUrl.equals(url)) {
//
//                               mWebView.goBack();
//                            }
//                        }


                    mWebView.goBack();
                } else {
                    finish();
                }
                break;
        }
    }
}
