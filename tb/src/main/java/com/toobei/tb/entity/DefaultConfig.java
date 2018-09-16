package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

public class DefaultConfig extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 2146572538530368073L;

	private String aboutme;//关于我们的链接地址
	private String question;//常见问题的链接地址
	private String showlevel;//查看等级职级介绍的链接地址
	private String rcintroduction;//邀请理财师规则介绍的链接地址
	private String serviceMail;//客服邮箱
	private String serviceTelephone;//客服电话
	private String shareLogoIcon;//微信分享logo
	private String wechatNumber;//微信公众号	
	private String zfxy;//支付协议
	private String userService;//用户协议
	private String bankLimitDetail;//银行充值额度明细表
	private String img_server_url;//图片服务器地址
	private String kefuEasemobileName;//客服环信帐号名称
	private String homeRecommendUrl;//金服首页产品推荐地址
	private String cfpRecommendUrl;//金服理财师产品推荐地址



	private String informationDetailUrl;// 机构动态  2016-11-15
	//V2.0.2添加
	private String orgDetailUrl;//机构详情链接	(跳转T呗) 2016/12/6
	private String frameWebUrl;//2.0.2以后新加的url 统一使用这个字段的url 拼接Type 切换不同的页面
	private String  safeShield;//律盾链接
	private String  toobeiTrain;//小白训练营



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

	public String getRcintroduction() {
		return rcintroduction;
	}

	public void setRcintroduction(String rcintroduction) {
		this.rcintroduction = rcintroduction;
	}

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

	public String getKefuEasemobileName() {
		return kefuEasemobileName;
	}

	public void setKefuEasemobileName(String kefuEasemobileName) {
		this.kefuEasemobileName = kefuEasemobileName;
	}

	public String getHomeRecommendUrl() {
		return homeRecommendUrl;
	}

	public void setHomeRecommendUrl(String homeRecommendUrl) {
		this.homeRecommendUrl = homeRecommendUrl;
	}

	public String getCfpRecommendUrl() {
		return cfpRecommendUrl;
	}

	public void setCfpRecommendUrl(String cfpRecommendUrl) {
		this.cfpRecommendUrl = cfpRecommendUrl;
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

	public String getSafeShield() {
		return safeShield;
	}

	public void setSafeShield(String safeShield) {
		this.safeShield = safeShield;
	}

	public String getToobeiTrain() {
		return toobeiTrain;
	}

	public void setToobeiTrain(String toobeiTrain) {
		this.toobeiTrain = toobeiTrain;
	}
}