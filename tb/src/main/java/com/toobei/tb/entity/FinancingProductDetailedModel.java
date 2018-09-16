package com.toobei.tb.entity;

import java.util.List;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc 类说明：投资活动 实体
 * 
 * @date 2015-10-20
 */
public class FinancingProductDetailedModel extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -8428663685395492424L;

	private String buyIncreaseMoney;// 购买递增金额
	private String buyMaxMoney;// 单笔购买最大额度
	private String buyMinMoney;// 单笔购买最小额度
	private String buyTotalMoney;// 募集总额
	private String buyedTotalMoney;// 已募集总额
	private String buyedTotalPeople;// 购买人数
	private String custBuyMaxMoney;// 用户可购买最大额度
	private String deadLineType;// 期限类型：1天，2月
	private String deadLineValue;// 期限
	private String fixRate;// 固定利率
	private String flowMaxRate;// 浮动最大利率
	private String flowMinRate;// 浮动最小利率
	private String isFlow;// 1固定利率；2浮动利率
	private String isRedemption;// 是否可赎回；1不赎回，2赎回
	private List<String> productDesc;// 产品描述
	private String productId;// 产品id
	private String productManageWay;// 产品管理方式1=利息复投
	private String productName;// 产品名称
	private String productProtocalId;// 协议id
	private String productProtocalName;//协议名称
	private String productProtocalUrl;//minvestor.xiaoniuapp.com/pages/agreement/credit_manage.html",//协议url
	private String ransferProtocalName;//收益转让协议名称
	private String ransferProtocalUrl;//minvestor.xiaoniuapp.com/pages/agreement/ttn_equity_transfer.html",//收益转让协议url

	private String redemptionArrivalTime;// 活期赎回到账时间
	private List<String> redemptionRule;// 活期赎回规则
	private String remaMoney;// 剩余金额
	private String repaymentWay;// 还本付息方式(1一次性到期;2一次性按日;3一次性按月;4一次性按季;5等额本息(按月);6等额本息(按季)
	private String status;// 产品状态(1=待审核(数据初始化)|2=审核通过(上架)|3=募集完成|4=还款中|5=(已到期)下架|6=已还款|7=已删除|8=驳回
	private String typeName;// 产品类型名称
	private String typeValue;// (1=天添牛|2=指数牛|3=活期宝 | 4= 惠房宝)
	private String validBeginDate;// 起息日
	private String validEndDate;// 结息日

	private String acountBalance;// 我的账户余额
	private String productIllustrationUrl;// www.baidu.com”//产品说明url
	private String securityGuaranteeUrl;// www.baidu.com”//安全保障url
	private List<ProductDetailProductInfo> productInfoList;//产品信息
	private String saleStatus; //销售状态(产品销售状态:1-预售、2-在售、3-售罄、4-下架)
	private String isQuota;//是否限额产品。1-限额、2-不限额
	private String beginSaleTimeText;//产品开售时间
	private String productInvestType;//收益类型
	private String collectLineMaxValue;
	private String collectLineMinValue;

	public String getBuyIncreaseMoney() {
		return buyIncreaseMoney;
	}

	public void setBuyIncreaseMoney(String buyIncreaseMoney) {
		this.buyIncreaseMoney = buyIncreaseMoney;
	}

	public String getBuyMaxMoney() {
		return buyMaxMoney;
	}

	public void setBuyMaxMoney(String buyMaxMoney) {
		this.buyMaxMoney = buyMaxMoney;
	}

	public String getBuyMinMoney() {
		return buyMinMoney;
	}

	public void setBuyMinMoney(String buyMinMoney) {
		this.buyMinMoney = buyMinMoney;
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

	public String getBuyedTotalPeople() {
		return buyedTotalPeople;
	}

	public void setBuyedTotalPeople(String buyedTotalPeople) {
		this.buyedTotalPeople = buyedTotalPeople;
	}

	public String getCustBuyMaxMoney() {
		return custBuyMaxMoney;
	}

	public void setCustBuyMaxMoney(String custBuyMaxMoney) {
		this.custBuyMaxMoney = custBuyMaxMoney;
	}

	public String getDeadLineType() {
		return deadLineType;
	}

	public void setDeadLineType(String deadLineType) {
		this.deadLineType = deadLineType;
	}

	public String getDeadLineValue() {
		return deadLineValue;
	}

	public void setDeadLineValue(String deadLineValue) {
		this.deadLineValue = deadLineValue;
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

	public String getIsRedemption() {
		return isRedemption;
	}

	public void setIsRedemption(String isRedemption) {
		this.isRedemption = isRedemption;
	}

	public List<String> getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(List<String> productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductManageWay() {
		return productManageWay;
	}

	public void setProductManageWay(String productManageWay) {
		this.productManageWay = productManageWay;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductProtocalId() {
		return productProtocalId;
	}

	public void setProductProtocalId(String productProtocalId) {
		this.productProtocalId = productProtocalId;
	}

	public String getProductProtocalName() {
		return productProtocalName;
	}

	public void setProductProtocalName(String productProtocalName) {
		this.productProtocalName = productProtocalName;
	}

	public String getRedemptionArrivalTime() {
		return redemptionArrivalTime;
	}

	public void setRedemptionArrivalTime(String redemptionArrivalTime) {
		this.redemptionArrivalTime = redemptionArrivalTime;
	}

	public List<String> getRedemptionRule() {
		return redemptionRule;
	}

	public void setRedemptionRule(List<String> redemptionRule) {
		this.redemptionRule = redemptionRule;
	}

	public String getRemaMoney() {
		return remaMoney;
	}

	public void setRemaMoney(String remaMoney) {
		this.remaMoney = remaMoney;
	}

	public String getRepaymentWay() {
		return repaymentWay;
	}

	public void setRepaymentWay(String repaymentWay) {
		this.repaymentWay = repaymentWay;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getValidBeginDate() {
		return validBeginDate;
	}

	public void setValidBeginDate(String validBeginDate) {
		this.validBeginDate = validBeginDate;
	}

	public String getValidEndDate() {
		return validEndDate;
	}

	public void setValidEndDate(String validEndDate) {
		this.validEndDate = validEndDate;
	}

	public String getAcountBalance() {
		return acountBalance;
	}

	public void setAcountBalance(String acountBalance) {
		this.acountBalance = acountBalance;
	}

	public String getProductIllustrationUrl() {
		return productIllustrationUrl;
	}

	public void setProductIllustrationUrl(String productIllustrationUrl) {
		this.productIllustrationUrl = productIllustrationUrl;
	}

	public String getSecurityGuaranteeUrl() {
		return securityGuaranteeUrl;
	}

	public void setSecurityGuaranteeUrl(String securityGuaranteeUrl) {
		this.securityGuaranteeUrl = securityGuaranteeUrl;
	}

	public String getProductProtocalUrl() {
		return productProtocalUrl;
	}

	public void setProductProtocalUrl(String productProtocalUrl) {
		this.productProtocalUrl = productProtocalUrl;
	}

	public String getRansferProtocalName() {
		return ransferProtocalName;
	}

	public void setRansferProtocalName(String ransferProtocalName) {
		this.ransferProtocalName = ransferProtocalName;
	}

	public String getRansferProtocalUrl() {
		return ransferProtocalUrl;
	}

	public void setRansferProtocalUrl(String ransferProtocalUrl) {
		this.ransferProtocalUrl = ransferProtocalUrl;
	}

	public List<ProductDetailProductInfo> getProductInfoList() {
		return productInfoList;
	}

	public void setProductInfoList(List<ProductDetailProductInfo> productInfoList) {
		this.productInfoList = productInfoList;
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

	public String getBeginSaleTimeText() {
		return beginSaleTimeText;
	}

	public void setBeginSaleTimeText(String beginSaleTimeText) {
		this.beginSaleTimeText = beginSaleTimeText;
	}

	public String getProductInvestType() {
		return productInvestType;
	}

	public void setProductInvestType(String productInvestType) {
		this.productInvestType = productInvestType;
	}

	public String getCollectLineMaxValue() {
		return collectLineMaxValue;
	}

	public void setCollectLineMaxValue(String collectLineMaxValue) {
		this.collectLineMaxValue = collectLineMaxValue;
	}

	public String getCollectLineMinValue() {
		return collectLineMinValue;
	}

	public void setCollectLineMinValue(String collectLineMinValue) {
		this.collectLineMinValue = collectLineMinValue;
	}
}