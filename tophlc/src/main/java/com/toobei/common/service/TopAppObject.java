package com.toobei.common.service;

import android.webkit.JavascriptInterface;

import com.toobei.common.TopApp;

import org.xsl781.utils.Logger;
import org.xsl781.utils.SystemTool;

/**
 * 公司: tophlc
 * 类说明：提供给H5调用的接口
 *
 * @date 2016-4-12
 */
public abstract class TopAppObject {


    @JavascriptInterface
    public String getAppResource() {
        return "android";
    }

    /**
     * 给H5 返回app 版本
     *
     * @return
     */
    @JavascriptInterface
    public String getAppVersion() {
        return SystemTool.getAppVersion(TopApp.getInstance());
    }

    @JavascriptInterface
    public String getAppToken() {
        Logger.d("getAppToken");
        return TopApp.getInstance().getLoginService().token;
    }

    /**
     * H5判断是哪一个端
     *
     * @return t呗 = "investor";     貅比特 = "channel";
     */
    @JavascriptInterface
    public String getAppKind() {
        return TopApp.getInstance().getHttpService().appKind;
    }

    @JavascriptInterface
    public void getAppLogOut() {
        TopApp.getInstance().logOut();
    }

    /**
     * 功能：显示提示弹出框
     *
     * @param title
     */
    @JavascriptInterface
    public void showAppPrompt(String title) {

    }


    /**
     * 功能：拉取app分享功能 传入分享对象的json
     *
     * @param str
     */
    @JavascriptInterface
    public void getAppShareFunction(String str) {

    }

    /**
     * 功能：跳转到产品详情界面  传入产品对象的json
     *
     * @param str
     */
    @JavascriptInterface
    public void getAppProductDetailFunction(String str) {

    }

    /**
     * 功能：金服里购买产品跳转，传入产品对象的json
     *
     * @param str
     */
    @JavascriptInterface
    public void buyProduct(String str) {

    }

    /**
     * 功能：设置web界面的标题
     *
     * @param title {"title":"产品名称"}
     */
    @JavascriptInterface
    public void setAppWebTitle(String title) {

    }

    /**
     * 功能：设置理财师推荐产品成功
     */
    @JavascriptInterface
    public void setApplhlcsProRecommendSucc() {

    }

    /**
     * 功能：得到理财师佣金计算功能
     */
    @JavascriptInterface
    public void getApplhlcsCommissionCalc() {

    }
    //  native.action('productRecommend',{'productId':self.productId});

    /**
     * 功能：跳转推荐产品列表推荐产品
     */
    @JavascriptInterface
    public void productRecommend() {

    }

    /**
     * 功能：得到理财师平台详情界面功能
     *
     * @param orgNoJson {"orgNo":"平台id"}
     */
    @JavascriptInterface
    public void getAppPlatfromDetail(String orgNoJson) {

    }

    /**
     * 功能： //  2016/12/7 0007  产品详情页自己购买产品
     *
     * @param url {"orgNo":"平台id"}
     */
    @JavascriptInterface
    public void buyTBProduct(String url) {

    }

    /**
     * 显示产品详情->
     */
    @JavascriptInterface
    public void showInvestCaseTip(String jsonStr) {

    }


    /**
     * V2.0.3
     * 新手攻略->绑卡
     */
    @JavascriptInterface
    public void bindCardAuthenticate() {

    }

    /**
     * V2.0.3
     * 新手攻略->邀请用户
     */
    @JavascriptInterface
    public void invitedCustomer() {

    }

    /**
     * V2.0.3
     * 新手攻略->邀请理财师
     */
    @JavascriptInterface
    public void invitedCfg() {

    }

    /**
     * 跳转保险第三方页面
     *
     * @param str Json格式 包括两个参数
     *            caseCode  保险id
     *            tag  1: 保险详情  2：个人中心
     */
    @JavascriptInterface
    public void jumpThirdInsurancePage(String json) {

    }

}