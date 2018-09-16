package com.toobei.tb.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.toobei.tb.R;
import com.toobei.tb.service.JumpOrgService;

/**
 * 公司: tophlc
 * 类说明：合作机构个人中心
 *
 * @date 2015-10-28
 */
public class OrgUserCenterWebActivity extends WebActivityCommon {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String orgNo = getIntent().getStringExtra("orgNo");
        String orgName = getIntent().getStringExtra("orgName");
        if (!TextUtils.isEmpty(orgNo)) {
            JumpOrgService.bindOrgAccountAndJumpUserCenter(orgNo, ctx, mWebView, orgName);
        }
        headerLayout.showRightTextButton(R.string.go_back_tbei, this);
        headerLayout.showLeftBackButton(this);
    }

    @Override
    public void onPageFinished(String url) {
        super.onPageFinished(url);
//        mWebView.setRedirectUsable(false);   //跳转其他网页时新起activity
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
