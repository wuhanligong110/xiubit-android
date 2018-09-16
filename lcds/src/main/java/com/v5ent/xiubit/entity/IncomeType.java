package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明：收益类别 实体
 *
 * @date 2015-10-22
 */
public class IncomeType extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7981969641841305502L;

    private String profixType;//收益类别id 收益类型：1销售佣金，2推荐津贴，3活动奖励，4团队leader奖励
    private String profixTypeName;//收益类别
    private String amount;//金额
/*

    private String profitType;//金额
    private String profitName;//收益类别
    private String amt;//金额
    private String configCode;//收益类别id  类别 空或者(1000)表示全部 1001=佣金;1002=推荐收益;1003=理财师职位津贴;1004=季度奖励;
    private String configName;//收益类别
*/


    public IncomeType() {
        super();
    }
/*
    public IncomeType(String profitType, String profitName) {
        super();
        this.profitType = profitType;
        this.configCode = profitType;
        this.profitName = profitName;
        this.configName = profitName;
    }

    public String getProfitType() {
        return profitType;
    }

    public void setProfitType(String profitType) {
        this.profitType = profitType;
    }

    public String getProfitName() {
        return profitName;
    }

    public void setProfitName(String profitName) {
        this.profitName = profitName;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
        this.profitType = configCode;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
        this.profitName = configName;

    }*/

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}