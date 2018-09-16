package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明： 月收益详情实体
 *
 * @date 2016-11-24
 */
public class IncomeDetail extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3745359143666133243L;


    /**
     * amount : 150.00
     * deadline : 3个月
     * description : 客户15083841185购买 天天牛A款90天 金额2000.00元
     * feeRate : 2.5%
     * profixType : 1
     * profixTypeName : 销售佣金
     * time : 2016-11-11 10:10
     */

    private String amount;              //amount	金额	string
    private String deadline;            //	deadline	产品期限	string
    private String description;         //	description	描述	string
    private String feeRate;             //	feeRate	年化佣金率；例：2.5%	string
    private String productType;         //	productType	产品类型：1首投，2首投可赎回，3复投，4复投可赎回	string
    private String profixType;          //	profixType	收益类型：1销售佣金，2推荐津贴，3活动奖励，4团队leader奖励	string
    private String profixTypeName;      //	profixTypeName	收益类型名称：例：销售佣金	string
    private String time;                //	remark	疑问描述	string
    private String remark;              //	time	时间	string
    private String typeSuffix;          // 类型描述后缀：理财活动	,间接，T呗

    public String getTypeSuffix() {
        return typeSuffix;
    }

    public void setTypeSuffix(String typeSuffix) {
        this.typeSuffix = typeSuffix;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    public String getProfixType() {
        return profixType;
    }

    public void setProfixType(String profixType) {
        this.profixType = profixType;
    }

    public String getProfixTypeName() {
        return profixTypeName;
    }

    public void setProfixTypeName(String profixTypeName) {
        this.profixTypeName = profixTypeName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}