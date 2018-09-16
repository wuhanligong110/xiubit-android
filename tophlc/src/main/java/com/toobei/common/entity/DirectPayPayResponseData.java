package com.toobei.common.entity;


public class DirectPayPayResponseData extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -1432950701473518293L;

	private String inRecordNo;
	private String method;
	private DirectPayPayDataReqdata payParaMap;
	private String payUrl;

	public String getInRecordNo() {
		return inRecordNo;
	}

	public void setInRecordNo(String inRecordNo) {
		this.inRecordNo = inRecordNo;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public DirectPayPayDataReqdata getPayParaMap() {
		return payParaMap;
	}

	public void setPayParaMap(DirectPayPayDataReqdata payParaMap) {
		this.payParaMap = payParaMap;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

}