package com.v5ent.xiubit.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopWebActivityCommon;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.utils.TextUrlUtils;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.service.HttpService;
import com.v5ent.xiubit.service.JumpInsuranceService;
import com.v5ent.xiubit.util.DynamicSkipManage;
import com.v5ent.xiubit.view.popupwindow.SharePopupWindow;

import static com.v5ent.xiubit.activity.WebActivityCommon.WebActivityType.MINE_LEVEL;

/**
 * 公司: tophlc
 * 类说明：Activity-通用网页
 *
 * @date 2015-10-28
 */
public class WebActivityCommon extends TopWebActivityCommon {

    private static final String TAG = "WebActivityCommon";

    protected SharePopupWindow popuWindow;

    

    @Override
    protected Intent getGestureActivityIntent(TopBaseActivity activity) {
        return new Intent(activity, GestureActivity.class);
    }

    @Override
    public void showThis(TopBaseActivity ctx, String url, String title) {
        showThisActivity(ctx,url,title);
    }

    public static void showThisActivity(TopBaseActivity ctx, String url, String title) {
        Intent intent = new Intent(ctx, WebActivityCommon.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        ctx.showActivity(ctx, intent);
    }

    /**
     * 功能:  传入isRedirectUsable为false webView支持重定向 新起一个activitiy
     *
     * @param ctx              ctx
     * @param url              url
     * @param title            title
     * @param isRedirectUsable 传入isRedirectUsable为false webView支持重定向 新起一个activitiy
     */
    public static void showThisActivity(TopBaseActivity ctx, String url, String title, boolean isRedirectUsable) {
        Intent intent = new Intent(ctx, WebActivityCommon.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("isRedirectUsable", isRedirectUsable);
        ctx.showActivity(ctx, intent);
    }

   

    @Override
    public void showPopupWindow(ShareContent shareContent) {
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
            popuWindow.showPopupWindow(headerLayout);

            // 友盟统计分享点击次数
            switch (webActivityType) {
                case WebActivityType.LIECAI_CLASS_ROOM:

                    break;
                case WebActivityType.LIECAI_NEWS:

                    break;
                case WebActivityType.LIECAI_PLATFORM_ACTIVITIES:
                    break;
                case MINE_LEVEL:
                    break;
            }
        }
    }

    /**
     * 跳转邀请客户页面
     */
    @Override
    public void skipToinvitedCustomer() {
        ctx.startActivity(new Intent(ctx, InviteCustomerQr.class));
    }

    /**
     * 跳转推荐理财师页面
     */
    @Override
    public void skipToInviteCfpQr() {
        ctx.startActivity(new Intent(ctx, InviteCfpQr.class));
    }

    @Override
    public void skipToLoginActivity() {
        showActivity(ctx, LoginActivity.class);
    }

    @Override
    public void skipToCardAdd() {
        startActivity(new Intent(ctx, CardManagerAdd.class));
    }

    @Override
    public void skipToCfgLevelCalculate() {
        startActivity(new Intent(ctx, CfgLevelCalculateActivity.class));
    }

    @Override
    public void skipToPlatfromDetail(String json) {
        String orgNo = (String) JSON.parseObject(json).get("orgNo");
        Intent intent = new Intent(ctx, OrgInfoDetailActivity.class);
        intent.putExtra("orgNumber", orgNo);
        startActivity(intent);
    }

    @Override
    public void skipToActivity(String json) {
        String str = new String(Base64.decode(json.getBytes(), Base64.DEFAULT));
        DynamicSkipManage.skipActivity(this, str);
    }

    @Override
    protected void jumpToThirdInsurancePage(String caseCode,String tag) {
        new JumpInsuranceService(ctx,caseCode,tag).run();
    }

    @Override
    public void onPageStart(String url) {

    }

    @Override
    public void onLoadError(String url) {

    }

    public interface WebActivityType {
        int LIECAI_CLASS_ROOM = 01;
        int LIECAI_NEWS = 02;
        int LIECAI_PLATFORM_ACTIVITIES = 03;
        int MINE_LEVEL = 04;
    }

    

    @Override
    public void refreshAfterLogin() {
        super.refreshAfterLogin();
        mWebView.reload();
    }
}
