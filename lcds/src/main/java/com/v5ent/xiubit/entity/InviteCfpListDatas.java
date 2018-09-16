package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;
import com.toobei.common.entity.ShareContent;

public class InviteCfpListDatas extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 9162733259715656577L;

	private String customerId;//客户id 
	private String userNumber;//理财师编号(未注册时为空)
	private String customerName;//"客户名称", 
	private String customerMobile;//客户手机号
	private String rcDate;//邀请时间
	private String registerDate;//注册时间
	private String firstRcpDate;//首单时间
	private String rcWay;//邀请方式(1外部邀请，2内部签名，3内部匿名)
	private String regFlag;//是否注册(0:未注册；1:已注册)
	private ShareContent shareContent;
	private String maillistContent;//通讯录发送内容
	

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getRcDate() {
		return rcDate;
	}

	public void setRcDate(String rcDate) {
		this.rcDate = rcDate;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getFirstRcpDate() {
		return firstRcpDate;
	}

	public void setFirstRcpDate(String firstRcpDate) {
		this.firstRcpDate = firstRcpDate;
	}

	/**
	 * 功能：邀请方式(1外部邀请，2内部签名，3内部匿名)
	 * @return
	 */
	public String getRcWay() {
		return rcWay;
	}

	/**
	 * 1微信推荐，2客户升级-内部签名，3客户升级-内部匿名,4、通讯录推荐
	 * 功能：
	 * @return
	 */
	public String getRcWayStr() {
		if (rcWay == null) {
			return "";
		}
		if (rcWay.equals("1")) {
			return "(外部推荐)";
		} else if (rcWay.equals("2")) {
			return "(客户升级-签名)";
		} else if (rcWay.equals("3")) {
			return "(客户升级-匿名)";
		}else if (rcWay.equals("4")) {
			return "(通讯录推荐)";
		}
		return "";
	}

	public void setRcWay(String rcWay) {
		this.rcWay = rcWay;
	}

	/**
	 * 功能：是否注册(0:未注册；1:已注册)
	 * @return
	 */
	public String getRegFlag() {
		return regFlag;
	}

	/**
	 * 功能：是否注册 已注册为true
	 * @return
	 */
	public boolean isRegister() {
		return regFlag != null && regFlag.equals("1");
	}

	public void setRegFlag(String regFlag) {
		this.regFlag = regFlag;
	}

	public ShareContent getShareContent() {
		return shareContent;
	}

	public void setShareContent(ShareContent shareContent) {
		this.shareContent = shareContent;
	}

	public String getMaillistContent() {
		return maillistContent;
	}

	public void setMaillistContent(String maillistContent) {
		this.maillistContent = maillistContent;
	}

	
}