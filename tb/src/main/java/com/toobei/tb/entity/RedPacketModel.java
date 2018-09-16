package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

public class RedPacketModel extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 8370039548993217785L;
	private String busType;//红包业务类型
	private String dateStr;//过期时间描述
	private String fid;//红包编号
	private String redpaperMoney;//红包金额
	private String showName;//显示名称
	private String useRemark;//描述
	private String redpaperType;//发放红包类型 0=平台|1=理财师

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getRedpaperMoney() {
		return redpaperMoney;
	}

	public void setRedpaperMoney(String redpaperMoney) {
		this.redpaperMoney = redpaperMoney;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getUseRemark() {
		return useRemark;
	}

	public void setUseRemark(String useRemark) {
		this.useRemark = useRemark;
	}

	public String getRedpaperType() {
		return redpaperType;
	}

	public void setRedpaperType(String redpaperType) {
		this.redpaperType = redpaperType;
	}

}