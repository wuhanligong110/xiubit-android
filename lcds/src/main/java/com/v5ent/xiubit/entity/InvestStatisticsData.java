package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

public class InvestStatisticsData extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6238829553161272996L;

    private String investTotal;//投资总额 // investTotal=219058.00
    private String investCount;//投资笔数
    private String investNumber;//投资人数 // investNumber=1
    private String feeAmt;//赚取佣金

    public String getInvestTotal() {
        return investTotal;
    }

    public void setInvestTotal(String investTotal) {
        this.investTotal = investTotal;
    }

    public String getInvestCount() {
        return investCount;
    }

    public void setInvestCount(String investCount) {
        this.investCount = investCount;
    }

    public String getInvestNumber() {
        return investNumber;
    }

    public void setInvestNumber(String investNumber) {
        this.investNumber = investNumber;
    }

    public String getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(String feeAmt) {
        this.feeAmt = feeAmt;
    }
}