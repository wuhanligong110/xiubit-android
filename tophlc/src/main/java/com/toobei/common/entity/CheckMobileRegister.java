package com.toobei.common.entity;


public class CheckMobileRegister extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -3899980765489695446L;

	private String regSource = "";//注册来源 钱罐子
	private String regFlag = "";// 0未注册(非第三方账号),1未注册(第三方账号) 2已注册
	private String regLimit = "";//注册限制(true限制注册,false可以注册)
	private String regLimitMsg = "";//您是理财师专属客户，需由您的理财师推荐您升级”//注册限制提示语

	public boolean isRegisterCfg() {
		return regFlag != null && regFlag.equals("2");
	}

	public String getRegSource() {
		return regSource;
	}

	public void setRegSource(String regSource) {
		this.regSource = regSource;
	}

	public String getRegFlag() {
		return regFlag;
	}

	public void setRegFlag(String regFlag) {
		this.regFlag = regFlag;
	}

	public String getRegLimit() {
		return regLimit;
	}

	public void setRegLimit(String regLimit) {
		this.regLimit = regLimit;
	}

	public String getRegLimitMsg() {
		return regLimitMsg;
	}

	public void setRegLimitMsg(String regLimitMsg) {
		this.regLimitMsg = regLimitMsg;
	}

}