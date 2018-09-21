package com.toobei.tb.activity;

import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopWebActivityCommon;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.utils.TextUrlUtils;
import com.toobei.tb.MyApp;
import com.toobei.tb.service.HttpService;
import com.toobei.tb.util.DynamicSkipManage;
import com.toobei.tb.view.popupwindow.SharePopupWindow;


/**
 * 公司: tophlc
 * 类说明：通用网页
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
        Intent intent = new Intent(ctx, WebActivityCommon.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        ctx.showActivity(ctx, intent);
    }

    public static void showThisActivity(TopBaseActivity ctx, String url, String title) {
        Intent intent = new Intent(ctx, WebActivityCommon.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        ctx.showActivity(ctx, intent);
    }

    /**
     * 功能:  传入isRedirectUsable为false webView支持重定向 不新起一个activitiy
     *
     * @param ctx              ctx
     * @param url              url
     * @param title            title
     * @param isRedirectUsable 传入isRedirectUsable为false webView支持重定向 不新起一个activitiy
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
            if (popuWindow == null) {


                //添加分享 貅比特与投呗分享链接带参数标识与貅比特app内h5流量监控
                String link = shareContent.getLink();
                if (link != null && !link.contains("&fromApp=toobei&os=Android")) {

                    shareContent.setLink(TextUrlUtils.addUrlStr(link, HttpService.getInstance().getShareUrlEndSuffix()));
                }
                //因为后台返回的是一个md5 拼接上图片服务器地址
                String shareImgurl = shareContent.getShareImgurl();
                if (!TextUtils.isEmpty(shareImgurl) && !shareImgurl.startsWith("http")) {
                    shareImgurl = MyApp.getInstance().getHttpService().getImageServerBaseUrl() + shareImgurl;
                    shareContent.setShareImgurl(shareImgurl);
                }
                popuWindow = new SharePopupWindow(ctx, null, shareContent);
            }
            popuWindow.showPopupWindow(headerLayout);

//            // todo 友盟统计分享点击次数
//            switch (webActivityType) {
//                case WebActivityType.LIECAI_CLASS_ROOM:
//
//                    break;
//                case WebActivityType.LIECAI_NEWS:
//
//                    break;
//                case WebActivityType.LIECAI_PLATFORM_ACTIVITIES:
//                    break;
//                case MINE_LEVEL:
//                    break;
//            }
        }
    }


    public interface WebActivityType {
        int LIECAI_CLASS_ROOM = 01;
        int LIECAI_NEWS = 02;
        int LIECAI_PLATFORM_ACTIVITIES = 03;
        int MINE_LEVEL = 04;
    }

    @Override
    public void skipToinvitedCustomer() {
        if (!MyApp.getInstance().getLoginService().isCachPhoneExist()) {
            showActivity(ctx, LoginActivity.class);
        } else {
            showActivity(ctx, MyQRCodeActivity.class);
        }
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
        try {
            String pageClass= (String) JSON.parseObject(json).get("pageClass");
            String params = (String) JSON.parseObject(json).get("android");
            DynamicSkipManage.skipActivity(this,pageClass,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void jumpToThirdInsurancePage(String caseCode, String tag) {

    }

}
