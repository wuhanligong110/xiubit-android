package com.v5ent.xiubit.activity;

import android.content.Intent;
import android.view.View;

import com.toobei.common.TopApp;
import com.v5ent.xiubit.util.C;

/**
 * 公司: tophlc
 * 类说明：Activity-二维码邀请客户界面
 *
 * @date 2015-10-19
 */
public class InviteCustomerQr extends MyBaseInviteActivity {

    protected void initView() {
        super.initView();
//        title
        headerLayout.showTitle("");
        headerLayout.showLeftBackButton();
        headerLayout.showRightTextButton("邀请理财师", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, InviteCfpQr.class);
                ctx.startActivity(intent);
            }
        });

        mWebView.loadUrl(TopApp.getInstance().getHttpService().getBaseH5urlByAppkind() + C.INVEST_CUSTOMER);
    }

    

}
