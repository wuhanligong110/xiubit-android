package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc 类说明：投资活动 实体
 * 
 * @date 2015-10-20
 */
public class FinancingProductModel extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -8428663685395492424L;

	// "buyTotalMoney":"5000000.0000",//募集总额
	// "buyedTotalMoney":"5000000.0000",//已募集总额
	// "deadLine":"30",//期限
	// "deadLineType":"1",//期限类型：1天，2月
	// "fixRate":"0.00",//固定利率
	// "flowMaxRate":"11.00",//浮动最大利率
	// "flowMinRate":"5.50",//浮动最小利率
	// "isFlow":"2",//1固定利率；2浮动利率
	// "productDesc":["双十一狂欢周","额外加息1%"],//产品描述
	// "productId":"b58fb38c-a7c0-11e5-b5dd-005056bf130b",//产品id
	// "productName":"活期宝",//产品名称
	// "remaMoney":"0.0000",//剩余金额
	// "typeName":"活期宝",//产品类型名称
	// "typeValue":"3"//产品类型(1=天添牛|2=指数牛|3=活期宝 | 4= 惠房宝)}…
	//"invLabelStr":["热销","加息1%"]//投资者标签

	private int resId;//背景颜色
	private String cateId;//分类id
	private String cateName;//分类名称
	private String identifier;//分类标示
	private String buyTotalMoney;
	private String buyedTotalMoney;
	private String deadLine;
	private String deadLineType;
	private String fixRate;
	private String flowMaxRate;
	private String flowMinRate;
	private String isFlow;
	private String[] productDesc;
	private String productId;
	private String productName;
	private String remaMoney;
	private String typeName;
	private String typeValue;
	private String[] invLabelStr;
	private String openType; //打开方式 0或者空-原方式打开  1-新页面html5打开
	private String openLinkUrl;//链接的URL
	private String saleStatus;//销售状态(产品销售状态: 1-预售、2-在售、3-售罄、4-下架,6-募集中，7-募集失败，8-募集成功)
	private String beginSaleTime; //产品开售时间
	private String isQuota;// //是否限额产品。1-限额、2-不限额

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getBuyTotalMoney() {
		return buyTotalMoney;
	}

	public void setBuyTotalMoney(String buyTotalMoney) {
		this.buyTotalMoney = buyTotalMoney;
	}

	public String getBuyedTotalMoney() {
		return buyedTotalMoney;
	}

	public void setBuyedTotalMoney(String buyedTotalMoney) {
		this.buyedTotalMoney = buyedTotalMoney;
	}

	public String getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(String deadLine) {
		this.deadLine = deadLine;
	}

	public String getDeadLineType() {
		return deadLineType;
	}

	public void setDeadLineType(String deadLineType) {
		this.deadLineType = deadLineType;
	}

	public String getFixRate() {
		return fixRate;
	}

	public void setFixRate(String fixRate) {
		this.fixRate = fixRate;
	}

	public String getFlowMaxRate() {
		return flowMaxRate;
	}

	public void setFlowMaxRate(String flowMaxRate) {
		this.flowMaxRate = flowMaxRate;
	}

	public String getFlowMinRate() {
		return flowMinRate;
	}

	public void setFlowMinRate(String flowMinRate) {
		this.flowMinRate = flowMinRate;
	}

	public String getIsFlow() {
		return isFlow;
	}

	public void setIsFlow(String isFlow) {
		this.isFlow = isFlow;
	}

	public String[] getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String[] productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getRemaMoney() {
		return remaMoney;
	}

	public void setRemaMoney(String remaMoney) {
		this.remaMoney = remaMoney;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String[] getInvLabelStr() {
		return invLabelStr;
	}

	public void setInvLabelStr(String[] invLabelStr) {
		this.invLabelStr = invLabelStr;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public String getOpenLinkUrl() {
		return openLinkUrl;
	}

	public void setOpenLinkUrl(String openLinkUrl) {
		this.openLinkUrl = openLinkUrl;
	}

	public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}

	public String getIsQuota() {
		return isQuota;
	}

	public void setIsQuota(String isQuota) {
		this.isQuota = isQuota;
	}

	public String getBeginSaleTime() {
		return beginSaleTime;
	}

	public void setBeginSaleTime(String beginSaleTime) {
		this.beginSaleTime = beginSaleTime;
	}
}