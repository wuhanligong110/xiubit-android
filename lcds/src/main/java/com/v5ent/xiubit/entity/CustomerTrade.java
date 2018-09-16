package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;


/**
 * 客户->交易动态
 * <p>
 * 接口名称 交易动态-陈衡-已实现
 * 请求类型 get
 * 请求Url investRecord/cfplanner/customerTradeMsg
 */
public class CustomerTrade extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6657490484192144895L;

    private String time;//发生时间

    private String feeProfit;//我的佣金
    private String readFlag;//是否已读(true已读，false未读)
    private String remark;//描述,佣金等于0时显示

    private String regbizfrom;//客户来源：领会金服|钱罐子|新财富
    private String investCount;//投资笔数
    private String type;//类型 (1=申购|2=赎回)


    private String customerId;//客户id
    private String image;//客户头像 [待确定，看图片保存的形式]
    private String mobile;//客户手机号码
    private String name;//昵称
    private String startDate;// 购买日期
    private String endDate;//赎回日期
    private String productName;///产品名称
    private String platfrom;///平台名
    private String rate;//年化
    private String feeRate;//佣金率
    private String profit;//客户收益
    private String feeAmt;//我的佣金  [待确定，可能需要前端自己算]
    private String amt;//赎回额
    private String investState = "0";// V2.0.3 2016/12/21 0021    投资状态 0=到期|1=可赎回|2=可转让|3=可赎回且可转让
    private String status;//	产品状态 1=募集中|0=募集完成="0";
    //V2.2.0leadr奖励
    private String leaderProfit;//	产品状态 1=募集中|0=募集完成="0";

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductName(int length) {
        if (productName != null && productName.length() > length) {
            return productName.substring(0, length);
        }
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(String feeAmt) {
        this.feeAmt = feeAmt;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getFeeProfit() {
        return feeProfit;
    }

    public void setFeeProfit(String feeProfit) {
        this.feeProfit = feeProfit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInvestCount() {
        return investCount;
    }

    public void setInvestCount(String investCount) {
        this.investCount = investCount;
    }

    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }

    public String getRegbizfrom() {
        return regbizfrom;
    }

    public void setRegbizfrom(String regbizfrom) {
        this.regbizfrom = regbizfrom;
    }


    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPlatfrom() {
        return platfrom;
    }

    public void setPlatfrom(String platfrom) {
        this.platfrom = platfrom;
    }

    public String getInvestState() {
        return investState;
    }

    public void setInvestState(String investState) {
        this.investState = investState;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLeaderProfit() {
        return leaderProfit;
    }

    public void setLeaderProfit(String leaderProfit) {
        this.leaderProfit = leaderProfit;
    }
}