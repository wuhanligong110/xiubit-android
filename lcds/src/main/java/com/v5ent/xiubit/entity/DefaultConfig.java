package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

public class DefaultConfig extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2146572538530368073L;

    private String aboutme;//关于我们的链接地址
    private String question;//常见问题的链接地址
    private String showlevel;//查看等级职级介绍的链接地址
    //private String rcintroduction;//邀请理财师规则介绍的链接地址
    private String serviceMail;//客服邮箱
    private String serviceTelephone;//客服电话
    // shareLogoIcon=http://liecai.tophlc.com/static/images/lcds_icon_80.png
    private String shareLogoIcon;//微信分享logo
    private String wechatNumber;//微信公众号
    private String zfxy;//支付协议
    private String userService;//《貅比特用户服务协议》(注册界面用)
    private String bankLimitDetail;//银行充值额度明细表
    private String img_server_url;//图片服务器地址
    private String saleUserLevelUrl;//职级界面地址
    private String kefuEasemobileName;//客服环信帐号名称
    private String elearningEntrance;//时代光华地址
    private String informationUrl;//资讯
    private String recommandRule;//邀请理财师规则介绍的链接地址

    /* 红包使用说明*/
    private String redpacketOprInstruction;//红包使用说明url v1.1.0
    private String bulletinDetailDefaultUrl;//公告跳转地址url v1.1.0
    private String informationDetail;//资讯详情url v1.2.0
    private String commissionCalculationRuleUrl;// 佣金计算规则 v1.2.0

    private String tutorial;// 一分钟教你理财 v1.2.1
    private String investmentStrategy;// 机构详情 理财攻略 v1.2.1  2016-10-27
    private String domainUrl;// baseUrl v1.2.1  2016-10-27
    private String informationDetailUrl;// 机构动态  2016-11-15
    //V2.0.2添加
    private String orgDetailUrl;//机构详情链接	(跳转T呗) 2016/12/6
    private String frameWebUrl;//2.0.2以后新加的url 统一使用这个字段的url 拼接Type 切换不同的页面
    //热修复
    public String android_hotfix_isopen;
    public String android_hotfix_patch_version;

    public DefaultConfig(String informationDetailUrl) {
        this.informationDetailUrl = informationDetailUrl;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getShowlevel() {
        return showlevel;
    }

    public void setShowlevel(String showlevel) {
        this.showlevel = showlevel;
    }

//	public String getRcintroduction() {
//		return rcintroduction;
//	}
//
//	public void setRcintroduction(String rcintroduction) {
//		this.rcintroduction = rcintroduction;
//	}

    public String getServiceMail() {
        return serviceMail;
    }

    public void setServiceMail(String serviceMail) {
        this.serviceMail = serviceMail;
    }

    public String getServiceTelephone() {
        return serviceTelephone;
    }

    public void setServiceTelephone(String serviceTelephone) {
        this.serviceTelephone = serviceTelephone;
    }

    public String getShareLogoIcon() {
        return shareLogoIcon;
    }

    public void setShareLogoIcon(String shareLogoIcon) {
        this.shareLogoIcon = shareLogoIcon;
    }

    public String getWechatNumber() {
        return wechatNumber;
    }

    public void setWechatNumber(String wechatNumber) {
        this.wechatNumber = wechatNumber;
    }

    public String getZfxy() {
        return zfxy;
    }

    public void setZfxy(String zfxy) {
        this.zfxy = zfxy;
    }

    public String getUserService() {
        return userService;
    }

    public void setUserService(String userService) {
        this.userService = userService;
    }

    public String getBankLimitDetail() {
        return bankLimitDetail;
    }

    public void setBankLimitDetail(String bankLimitDetail) {
        this.bankLimitDetail = bankLimitDetail;
    }

    public String getImg_server_url() {
        return img_server_url;
    }

    public void setImg_server_url(String img_server_url) {
        this.img_server_url = img_server_url;
    }

    public String getSaleUserLevelUrl() {
        return saleUserLevelUrl;
    }

    public void setSaleUserLevelUrl(String saleUserLevelUrl) {
        this.saleUserLevelUrl = saleUserLevelUrl;
    }

    public String getKefuEasemobileName() {
        return kefuEasemobileName;
    }

    public void setKefuEasemobileName(String kefuEasemobileName) {
        this.kefuEasemobileName = kefuEasemobileName;
    }

    public String getElearningEntrance() {
        return elearningEntrance;
    }

    public void setElearningEntrance(String elearningEntrance) {
        this.elearningEntrance = elearningEntrance;
    }

    public String getInformationUrl() {
        return informationUrl;
    }

    public void setInformationUrl(String informationUrl) {
        this.informationUrl = informationUrl;
    }

    public String getRecommandRule() {
        return recommandRule;
    }

    public void setRecommandRule(String recommandRule) {
        this.recommandRule = recommandRule;
    }

    public String getRedpacketOprInstruction() {
        return redpacketOprInstruction;
    }

    public void setRedpacketOprInstruction(String redpacketOprInstruction) {
        this.redpacketOprInstruction = redpacketOprInstruction;
    }

    public String getBulletinDetailDefaultUrl() {
        return bulletinDetailDefaultUrl;
    }

    public void setBulletinDetailDefaultUrl(String bulletinDetailDefaultUrl) {
        this.bulletinDetailDefaultUrl = bulletinDetailDefaultUrl;
    }

    public String getInformationDetail() {
        return informationDetail;
    }

    public void setInformationDetail(String informationDetail) {
        this.informationDetail = informationDetail;
    }

    public String getCommissionCalculationRuleUrl() {
        return commissionCalculationRuleUrl;
    }

    public void setCommissionCalculationRuleUrl(String commissionCalculationRuleUrl) {
        this.commissionCalculationRuleUrl = commissionCalculationRuleUrl;
    }

    public String getTutorial() {
        return tutorial;
    }

    public void setTutorial(String tutorial) {
        this.tutorial = tutorial;
    }

    public String getInvestmentStrategy() {
        return investmentStrategy;
    }

    public void setInvestmentStrategy(String investmentStrategy) {
        this.investmentStrategy = investmentStrategy;
    }

    public String getDomainUrl() {
        return domainUrl;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    public String getInformationDetailUrl() {
        return informationDetailUrl;
    }

    public void setInformationDetailUrl(String informationDetailUrl) {
        this.informationDetailUrl = informationDetailUrl;
    }

    public String getOrgDetailUrl() {
        return orgDetailUrl;
    }

    public void setOrgDetailUrl(String orgDetailUrl) {
        this.orgDetailUrl = orgDetailUrl;
    }

    public String getFrameWebUrl() {
        return frameWebUrl;
    }

    public void setFrameWebUrl(String frameWebUrl) {
        this.frameWebUrl = frameWebUrl;
    }

}