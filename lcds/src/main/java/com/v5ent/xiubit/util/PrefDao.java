package com.v5ent.xiubit.util;

import android.content.Context;

import com.google.gson.Gson;
import com.toobei.common.utils.TopPrefDao;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.entity.DefaultConfig;
import com.toobei.common.entity.FundBaseDefinedEntiy;

public class PrefDao extends TopPrefDao {

    public PrefDao(Context cxt) {
        super(cxt);
    }

    public PrefDao(Context cxt, String prefName) {
        super(cxt, prefName);
    }

    public void setDefaultConfig(DefaultConfig data) {
        if (data == null) return;
        editor.putString("aboutme", data.getAboutme());
        editor.putString("question", data.getQuestion());
        editor.putString("showlevel", data.getShowlevel());
        //	editor.putString("rcintroduction", data.getRcintroduction());
        editor.putString("serviceMail", data.getServiceMail());
        editor.putString("serviceTelephone", data.getServiceTelephone());
        editor.putString("shareLogoIcon", data.getShareLogoIcon());
        editor.putString("wechatNumber", data.getWechatNumber());
        editor.putString("zfxy", data.getZfxy());
        editor.putString("userService", data.getUserService());
        editor.putString("bankLimitDetail", data.getBankLimitDetail());
        editor.putString("img_server_url", data.getImg_server_url());
        editor.putString("saleUserLevelUrl", data.getSaleUserLevelUrl());
        editor.putString("kefuEasemobileName", data.getKefuEasemobileName());
        editor.putString("img_server_url", data.getImg_server_url());
        editor.putString("elearningEntrance", data.getElearningEntrance());
        editor.putString("informationUrl", data.getInformationUrl());
        editor.putString("recommandRule", data.getRecommandRule());
        editor.putString("redpacketOprInstruction", data.getRedpacketOprInstruction());//红包使用说明
        editor.putString("bulletinDetailDefaultUrl", data.getBulletinDetailDefaultUrl());//公告跳转地址url v1.1.0
        editor.putString("informationDetail", data.getInformationDetail()); //资讯详情 V1.2.0
        editor.putString("bulletinDetailDefaultUrl", data.getBulletinDetailDefaultUrl());
        editor.putString("commissionCalculationRuleUrl", data.getCommissionCalculationRuleUrl()); //佣金计算规则
        editor.putString("tutorial", data.getTutorial()); //一分钟教你理财 V1.2.1
        editor.putString("investmentStrategy", data.getInvestmentStrategy()); //理财攻略V1.2.1 2016-10-27
        editor.putString("informationDetailUrl", data.getInformationDetailUrl()); //机构动态V2.0.0 2016-11-15
        editor.putString("orgDetailUrl", data.getOrgDetailUrl()); //机构详情链接 2016/12/6 0006
        editor.putString("frameWebUrl", data.getFrameWebUrl()); //2.0.2以后新加的url 统一使用这个字段的url 拼接Type 切换不同的页面 2016/12/6 0006
        editor.commit();
    }

    public String getUrlAboutMe() {
        return pref.getString("aboutme", C.URL_ABOUT_WE);
    }

    /*
     * 平台消息标示
     */
    public String getPlatformflag() {
        return pref.getString("platformflag", "");
    }

    public void setPlatformflag(String platformflag) {
        editor.putString("platformflag", platformflag).commit();
    }/*
     * 绑卡状态  0 为绑卡
	 */

    public String getBindCardflag() {
        return pref.getString("bindCardflag", "");
    }

    public void setBindCardlag(String bindCardflag) {
        editor.putString("bindCardflag", bindCardflag).commit();
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

    /**
     * 常见问题的网页
     */
    public String getUrlQuestion() {
        return pref.getString("question", C.URL_COMMON_QUESTION);
    }

    /**
     * 我的职级介绍网页
     */
    public String getURLMyLevelIntroduce() {
        return pref.getString("showlevel", C.URL_mine_level_DEFAULT);
    }

    /**
     * 资讯详情
     */
    public String getUrlinformationDetail() {
        return pref.getString("informationDetail", C.URL_INFORMATION_DETAIL);
    }

    /**
     * 功能：获得支付协议地址
     */
    public String getZfxy() {
        return pref.getString("zfxy", C.URL_DEFAULT);
    }

    /**
     * 客服邮箱
     */
    public String getServiceMail() {
        return pref.getString("serviceMail", "tongqingshang@163.com");
    }

    public String getServiceTelephone() {
        return pref.getString("serviceTelephone", "400-888-6987");
    }

    /**
     * 微信公众号
     */
    public String getWechatNumber() {
        return pref.getString("wechatNumber", "tsteven520");
    }

    /**
     * 功能：《貅比特用户服务协议》
     */
    public String getUserService() {
        return pref.getString("userService", C.URL_SERVICE_PROTOCOL);
    }

    public boolean isCustomLoaded() {
        return pref.getBoolean("isCustomLoaded", false);
    }

    public void setCustomLoaded(boolean customLoaded) {
        editor.putBoolean("isCustomLoaded", customLoaded).commit();
    }

    @Override
    public String getCallServiceEMId() {
        return pref.getString("kefuEasemobileName", C.STR_TOPHLCCUSTOMERSERVICEIMACCOUNT);
    }

    /**
     * 功能：职级界面地址
     */
    public String getSaleUserLevelUrl() {
        return pref.getString("saleUserLevelUrl", C.URL_mine_level_DEFAULT);
    }

    @Override
    public String getImgServerUrl() {
        return pref.getString("img_server_url", C.URL_IMAGE_SERVER_DEFAULT);
    }

    public String getElearningEntranceUrl() {
        return pref.getString("elearningEntrance", C.URL_DEFAULT);
    }

    /**
     * 功能：邀请理财师规则介绍的链接地址
     */
    public String getRecommandRule() {
        return pref.getString("recommandRule", C.URL_RECOMMEND_RULE);
    }

    /**
     * 功能：红包使用说明链接
     */
    public String getRedpacketOprInstruction() {
        return pref.getString("redpacketOprInstruction", C.URL_REDPACKET_USE_RULE);
    }

    /**
     * 功能：佣金计算规则链接
     */
    public String getCommissionCalculationRuleUrl() {
        return pref.getString("commissionCalculationRuleUrl", C.URL_CALCULATOR_RULE);
    }

    /**
     * 功能：一分钟教你理财 V1.2.1  2016/10/18 0018
     */
    public String getFinancialUrl() {
        return pref.getString("tutorial", C.URL_TEACH_FANANCIAL);
    }

    /**
     * 2016/10/27 0027  理财攻略
     */
    public String getInvestmentStrategy() {
        return pref.getString("investmentStrategy", C.URL_INVESTMENTSTRATEGY);
    }

    /**
     * 公告詳情跳轉地址
     */
    public String getBulletinDetailDefaultUrl() {
        return pref.getString("bulletinDetailDefaultUrl", C.URL_bulletinDetailDefaultUrl);
    }

    /**
     * 机构详情链接 理财跳转H5
     *
     * @return getOrgDetailUrl
     */
    public String getOrgDetailUrl() {
        return pref.getString("orgDetailUrl", C.URL_ORGDETAILURL);
    }

    /**
     * 2.0.2以后新加的url 统一使用这个字段的url 拼接key 切换不同的页面 2016/12/6 0006
     */
    public String getFrameWebUrl() {
        return pref.getString("frameWebUrl", C.URL_FRAMEWEBURL);
    }

    /**
     * 机构动态 、资讯 、课堂详情 还有 机构详情的机构动态哟   链接 V2.0 2016-11-15
     *
     * @return informationDetailUrl
     */
    public String getInformationDetailUrl() {
        return pref.getString("informationDetailUrl", C.URL_INFORMATIONDETAILURL);
    }

    @Override
    public String getWechatShareLogo() {
        return pref.getString("shareLogoIcon", C.URL_WECHAT_LOGO);

    }
/*-----------------------------------------------------------------------------------------*/

    /**
     * 获取是第一次进来否显示过理财界面
     *
     * @return
     */
    public boolean hasShowLiecaiGuide() {
        return pref.getBoolean("hasShowLiecaiGuide", false);
    }

    /**
     * 设置已经显示了理财界面
     */
    public void setHasShowLiecaiGuideTrue() {
        editor.putBoolean("hasShowLiecaiGuide", true).commit();
    }

    /**
     * 设置新手任务状态
     *
     * @param hasFinish 新手任务状态 true完成 ; false :未完成
     */
    public void setFinishFreshMissionState(boolean hasFinish) {
        editor.putBoolean("hasFinishFreshMission", hasFinish).commit();
    }

    /**
     * 获取新手任务完成状态
     *
     * @return true 完成 ; false 未完成
     */
    public boolean getFinishFreshMissionState() {
        return pref.getBoolean("hasFinishFreshMission", false);
    }

    /**
     * 月度收益页面顶部红色提示文案是否允许显示
     *
     * @param tag true 允许弹出，false 禁止显示
     */
    public void setIncomeDetailRedTopAllowSee(Boolean tag) {
        editor.putBoolean("incomedetailredtopallowsee", tag).commit();
    }

    public Boolean getIncomeDetailRedTopAllowSee() {
        return pref.getBoolean("incomedetailredtopallowsee", true);
    }

    /**
     * 团队leader奖励顶部红色提示文案是否允许显示
     *
     */
    public void setTeamLeaderRedTopAllowSee(Boolean tag) {
        editor.putBoolean("teamLeaderRedTopSee", tag).commit();
    }

    public Boolean getTeamLeaderRedTopAllowSee() {
        return pref.getBoolean("teamLeaderRedTopSee", true);
    }

    /**
     * 保存首页活动弹窗数据的id
     *
     * @param id
     */
    public void setHomePageNewActivityId(int id) {
        String curPhone = MyApp.getInstance().getLoginService().curPhone;
        editor.putInt(curPhone+"homePageNewActivityId", id).commit();
    }

    /**
     * 获取本地保存首页活动弹窗数据的id
     */
    public int getHomePageNewActivityId() {
        String curPhone = MyApp.getInstance().getLoginService().curPhone;
        return pref.getInt(curPhone+"homePageNewActivityId", -1);
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

    /**
     * 是否显示收益榜提示
     *
     * @param hasShowIncomeRankTips
     */
    public void setIsShowInComeRankTips(Boolean hasShowIncomeRankTips) {
        editor.putBoolean("hasShowIncomeRankTips", hasShowIncomeRankTips).commit();
    }

    public Boolean getIsShowInComeRankTips() {
        return pref.getBoolean("hasShowIncomeRankTips", false);
    }

    /**
     * 保存首页职级弹窗是否已弹出过的状态
     */
    public void setCfpLevelWarningDialogHasShowStatus(String time , int stage ,boolean status) {
        String curPhone = MyApp.getInstance().getLoginService().curPhone;
        editor.putBoolean(curPhone + time + stage, status).commit();
    }

    /**
     * 获取本地保存首页活动弹窗数据的id
     */
    public boolean getCfpLevelWarningDialogHasShowStatus(String time , int stage) {
        String curPhone = MyApp.getInstance().getLoginService().curPhone;
        return pref.getBoolean(curPhone + time + stage,false);
    }

    /**
     * 平台详情页面点击去购买是否已经弹过窗
     *
     */
    public boolean getOrgDetailHadShowJumpDialog() {
        String curPhone = MyApp.getInstance().getLoginService().curPhone;
        return pref.getBoolean(curPhone+"OrgDetailHadShowJumpDialog",false);
    }


    public void setOrgDetailHadShowJumpDialog(boolean b) {
        String curPhone = MyApp.getInstance().getLoginService().curPhone;
        editor.putBoolean(curPhone+"OrgDetailHadShowJumpDialog",b).commit();
    }

    public void setFundFilterItems(FundBaseDefinedEntiy response) {
        String json = new Gson().toJson(response);
        editor.putString("fundFilterItems" , json).commit();
    }

    public FundBaseDefinedEntiy getFundFilterItems() {
        String json = pref.getString("fundFilterItems", "");
        if (TextUtil.isEmpty(json)) return null;
        return new Gson().fromJson(json,FundBaseDefinedEntiy.class);
    }

    //皮肤主题
    public void setSkinTheme(String tag) {
        editor.putString("SkinTheme" , tag).commit();
    }

    public String getSkinTheme() {
        MyApp.skinThemeType = pref.getString("SkinTheme",MyApp.skinThemeType);
        return MyApp.skinThemeType;
    }

    public void setAwardPromptHasShow() {
        editor.putBoolean("awardprompt" , true).commit();
    }

    public boolean isAwardPromptHasShow() {
        return pref.getBoolean("awardprompt",false);
    }

    //热修复计数
    public void setHotFixNumToday(String date) {
        editor.putInt("HotFixNumToday"+date , getHotFixNumToday(date)+1).commit();
    }

    public int getHotFixNumToday(String date) {
        return pref.getInt("HotFixNumToday"+date,0);
    }

    //存取热修复补丁版本号
    public void setHotFixVersion(int version) {
        editor.putInt("HotFixVersion" , version).commit();
    }

    public int getHotFixVersion() {
        return pref.getInt("HotFixVersion",0);
    }
}
