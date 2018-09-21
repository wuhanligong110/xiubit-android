package com.toobei.tb.util;

import android.content.Context;

import com.toobei.common.utils.TopPrefDao;
import com.toobei.tb.entity.DefaultConfig;

public class PrefDao extends TopPrefDao {

    public PrefDao(Context cxt) {
        super(cxt);
    }

    public PrefDao(Context cxt, String prefName) {
        super(cxt, prefName);
    }

	/*
     * 有购买，充值，提现动作，刷新界面
	 */
/*	public void setRefresh(boolean refreshFlag) {
        editor.putBoolean("refreshFlag", refreshFlag).commit();
	}

	public boolean getRefresh() {
		return pref.getBoolean("refreshFlag", false);
	}*/

    /*
     * 平台消息标示
     */
    public String getPlatformflag() {
        return pref.getString("platformflag", "");
    }

    public void setPlatformflag(String platformflag) {
        editor.putString("platformflag", platformflag).commit();
    }

    /*
     * 互动消息标示
     */
    public String getInteractflag() {
        return pref.getString("interactflag", "");
    }

    public void setInteractflag(String interactflag) {
        editor.putString("interactflag", interactflag).commit();
    }

    public void setLcsEasemobAcct(String easemobAcct) {
        editor.putString("LcsEasemobAcct", easemobAcct).commit();
    }

    /**
     * 功能：理财 师 环信账号
     *
     * @return
     */
    public String getLcsEasemobAcct() {
        return pref.getString("easemobAcct", null);
    }

    public void setDefaultConfig(DefaultConfig data) {
        if (data == null) return;
        //关于我们的链接地址
        editor.putString("aboutme", data.getAboutme());
        //常见问题的链接地址
        editor.putString("question", data.getQuestion());
        editor.putString("showlevel", data.getShowlevel());
        editor.putString("rcintroduction", data.getRcintroduction());
        //客服邮箱
        editor.putString("serviceMail", data.getServiceMail());
        //客服电话
        editor.putString("serviceTelephone", data.getServiceTelephone());
        //微信分享logo
        editor.putString("shareLogoIcon", data.getShareLogoIcon()); //shareLogoIcon https://image.toobei.com/74dd50c622b6f99ed0ad831b977708a9?f=png&q=100
        //微信公众号
        editor.putString("wechatNumber", data.getWechatNumber());
        editor.putString("zfxy", data.getZfxy());
        editor.putString("userService", data.getUserService());
        editor.putString("bankLimitDetail", data.getBankLimitDetail());
        editor.putString("kefuEasemobileName", data.getKefuEasemobileName());
        editor.putString("img_server_url", data.getImg_server_url());
        editor.putString("homeRecommendUrl", data.getHomeRecommendUrl());
        editor.putString("cfpRecommendUrl", data.getCfpRecommendUrl());


        editor.putString("informationDetailUrl", data.getInformationDetailUrl()); //机构动态V2.0.0 2016-11-15
        editor.putString("orgDetailUrl", data.getOrgDetailUrl()); //机构详情链接 2016/12/6 0006
        editor.putString("frameWebUrl", data.getFrameWebUrl()); //2.0.2以后新加的url 统一使用这个字段的url 拼接Type 切换不同的页面 2016/12/6 0006
        editor.putString("safeShield", data.getSafeShield());
        editor.putString("toobeiTrain", data.getToobeiTrain());
        editor.commit();
    }

    public String getUrlAboutMe() {
        return pref.getString("aboutme", C.URL_MINE_MORE_ABOUT_US);
    }

    /**
     * 常见问题的网页
     */
    public String getUrlQuestion() {
        return pref.getString("question", C.URL_DEFAULT);
    }


    /**
     * 客服邮箱
     */
    public String getServiceMail() {
        return pref.getString("serviceMail", "kefu@toobei.com");
    }

    public String getServiceTelephone() {
        return pref.getString("serviceTelephone", "0755-86725461");
    }

    @Override
    public String getZfxy() {
        return null;
    }

    /**
     * 微信公众号
     */
    public String getWechatNumber() {
        return pref.getString("wechatNumber", "领会");
    }

    /**
     * 功能：是否是第一次进入，显示引导页
     *
     * @param viewName
     * @return
     */
    public boolean isFirstGuide(String viewName) {
        return pref.getBoolean("FirstGuide" + viewName, true);
    }

    /**
     * 功能：设置第一次引导是否完成
     *
     * @param viewName
     * @param bool
     */
    public void setFirstGuide(String viewName, boolean bool) {
        editor.putBoolean("FirstGuide" + viewName, bool).commit();
    }

    /**
     * 功能：是否显示我的资产
     *
     * @param
     * @return
     */
    public boolean getShowMyAccount() {
        return pref.getBoolean("showFlag", true);
    }

    /**
     * 功能：是否显示我的资产
     */
    public void setShowMyAccount(boolean showFlag) {
        editor.putBoolean("showFlag", showFlag).commit();
    }

    @Override
    public String getCallServiceEMId() {
        return pref.getString("kefuEasemobileName", C.str_tophlcCustomerServiceImAccount);
    }

    @Override
    public String getImgServerUrl() {
        return pref.getString("img_server_url", C.URL_IMAGE_SERVER_DEFAULT);
    }

    //TODO
    @Override
    public String getBulletinDetailDefaultUrl() {
        return null;
    }

    /**
     * 微信QQ 分享Logo
     * @return
     */
    @Override
    public String getWechatShareLogo() {

        return pref.getString("shareLogoIcon", C.URL_WECHAR_SHARE_IMAGE);
    }


    /**
     * 2.0.2以后新加的url 统一使用这个字段的url 拼接key 切换不同的页面 2016/12/6 0006
     */
    public String getFrameWebUrl() {
        return pref.getString("frameWebUrl", C.URL_FRAMEWEBURL);
    }


    /**
     * 2016/10/27 0027  貅比特攻略
     */
    public String getInvestmentStrategy() {
        return pref.getString("investmentStrategy", C.URL_INVESTMENTSTRATEGY);
    }

    /**
     * 律盾
     */
    public String getSafeShield() {
        return pref.getString("safeShield", C.URL_SAFESHIELD);
    }

    /**
     * 小白训练营
     */
    public String getToobeiTrain() {
        return pref.getString("toobeiTrain", C.URL_TOOBEI_TRAIN);
    }

    /**
     * (貅比特:机构动态 、资讯 、课堂详情 ) 还有 机构详情的机构动态哟   链接 V2.0 2016-11-15
     *
     * @return informationDetailUrl
     */
    public String getInformationDetailUrl() {
        return pref.getString("informationDetailUrl", C.URL_ORGNIZATION_NEWS);
    }

    /**
     * 当前登录的是否貅比特跳转过来的token
     * @param tag
     */
   public void setIsLcdsJumpToken(boolean tag){
       editor.putBoolean("isLcdsJumpTag",tag).commit();
   }

    /**
     * 貅比特跳转过来的token
     * @return
     */
    public boolean getIsLcdsJumpToken(){
        return pref.getBoolean("isLcdsJumpTag",false);
    }

    /**
     * @return 短信跳转的Intent 内容
     */
    public String getSMS_jump_msg() {
        return pref.getString("SMS_jum_msg", "");
    }

    public void setSMS_jump_msg(String SMS_jum_msg) {
        editor.putString("SMS_jum_msg", SMS_jum_msg).commit();
    }
}
