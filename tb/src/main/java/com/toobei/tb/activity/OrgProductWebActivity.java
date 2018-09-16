package com.toobei.tb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.view.dialog.JumpProgressDialog;
import com.toobei.tb.R;

import org.xsl781.ui.ActivityStack;

/**
 * 公司: tophlc
 * 类说明：合作机构产品中心
 */
public class OrgProductWebActivity extends WebActivityCommon {
    private static String mPostUrl;
    private static String mOrgName;
    private static byte[] mPostDatas;
    private static boolean isPostFlag;  //判断此activity 的启动方式是PostUrl还是默认
    private JumpProgressDialog jumpProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String diaLogText = "正在为您跳转至" + mOrgName;
        jumpProgressDialog = new JumpProgressDialog(ActivityStack.getInstance().topActivity(), diaLogText);
        jumpProgressDialog.show();
        mWebView.postUrl(mPostUrl, mPostDatas);
        headerLayout.showRightTextButton(R.string.go_back_tbei, this);
        headerLayout.showLeftBackButton(this);
    }

    public static void showThisActivityForPost(TopBaseActivity ctx, String orgProductUrl, byte[] postDatas, String orgName) {
        mPostUrl = orgProductUrl;
        mPostDatas = postDatas;
        mOrgName = orgName;
        isPostFlag = true;
        Intent intent = new Intent(ctx, OrgProductWebActivity.class);
        intent.putExtra("title", mOrgName);
        ctx.showActivity(ctx, OrgProductWebActivity.class);
    }

    @Override
    public void onPageFinished(String url) {
        super.onPageFinished(url);
        jumpProgressDialog.dismiss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rightContainer:
                finish();
                break;
            case R.id.backBtn:
                if (mWebView != null && mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
                break;
        }
    }
}
