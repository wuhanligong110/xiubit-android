package com.toobei.common.entity;

public class AppVersion extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 4646664626681586624L;


//	downloadUrl	下载地址	string
//	issueTime	发布时间	string
//	minVersion	支持的最低版本	string
//	name	版本名称	string
//	openReg	是否开放注册	boolean	0开放,1不开放
//	regHints	注册提示	string
//	updateHints	升级提示	string
//	upgrade	升级状态	string	0-不升级 1-可升级 2-强制升级
//	version	版本号	string


	private String downloadUrl;//下载地址
	private String issueTime;//发布时间
	private String minVersion;//最低支持版本
	private String name;//版本名称
	private String openReg;//是否开放注册(true开放false不开放)
	private String regHints;//注册提示
	private String updateHints;//更新内容
	private String upgrade;//更新级别 升级(0不需要升级,1提示升级,2强制升级)
	private String version;//最新版本号

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(String issueTime) {
		this.issueTime = issueTime;
	}

	/**
	 * 功能：升级(0不需要升级,1提示升级,2强制升级)
	 * @return
	 */
	public String getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(String upgrade) {
		this.upgrade = upgrade;
	}

	public String getMinVersion() {
		return minVersion;
	}

	public void setMinVersion(String minVersion) {
		this.minVersion = minVersion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpenReg() {
		return openReg;
	}

	public void setOpenReg(String openReg) {
		this.openReg = openReg;
	}

	public String getRegHints() {
		return regHints;
	}

	public void setRegHints(String regHints) {
		this.regHints = regHints;
	}

	public String getUpdateHints() {
		return updateHints;
	}

	public void setUpdateHints(String updateHints) {
		this.updateHints = updateHints;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}