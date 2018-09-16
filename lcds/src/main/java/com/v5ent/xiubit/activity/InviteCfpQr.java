package com.v5ent.xiubit.activity;

import com.toobei.common.TopApp;
import com.v5ent.xiubit.util.C;

/**
 * 公司: tophlc
 * 类说明：Activity-推荐理财师界面
 *
 * @date 2016-2-24
 */
public class InviteCfpQr extends MyBaseInviteActivity {

    protected void initView() {
        super.initView();
//        title
        headerLayout.showTitle("邀请理财师");
        headerLayout.showLeftBackButton();
//        headerLayout.showRightTextButton("邀请客户", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ctx, InviteCustomerQr.class);
//                ctx.startActivity(intent);
//            }
//        });
        mWebView.loadUrl(TopApp.getInstance().getHttpService().getBaseH5urlByAppkind() + C.INVEST_CFG);
        
    }

}
