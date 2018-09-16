package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

import java.util.List;

public class AccountIncomeHomeData extends BaseEntity {

    private static final long serialVersionUID = 5571944259523153667L;
    private String accountBalance;//账户余额
    private String totalProfix;//今日收益

    //账户每月详情里显示 用
    private String grantDesc;//发放描述；例：下月15号发放
    private String isGrant;//是否发放：1已发放，0未发放

    //猎才2.1
    private String grantedAmount; //已发放金额
    private String waitGrantAmount; //待发放金额

    private List<IncomeType> profixList;//收益项

    public String getGrantedAmount() {
        return grantedAmount;
    }

    public void setGrantedAmount(String grantedAmount) {
        grantedAmount = grantedAmount;
    }

    public String getWaitGrantAmount() {
        return waitGrantAmount;
    }

    public void setWaitGrantAmount(String waitGrantAmount) {
        this.waitGrantAmount = waitGrantAmount;
    }

    public String getAccountBalance() {
        return accountBalance; //accountBalance=366760.72
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getTotalProfix() {
        return totalProfix;
    }

    public void setTotalProfix(String totalProfix) {
        this.totalProfix = totalProfix;
    }

    public List<IncomeType> getProfixList() {
        return profixList;
    }

    public void setProfixList(List<IncomeType> profixList) {
        this.profixList = profixList;
    }

    public String getGrantDesc() {
        return grantDesc;
    }

    public void setGrantDesc(String grantDesc) {
        this.grantDesc = grantDesc;
    }

    public String getIsGrant() {
        return isGrant;
    }

    public void setIsGrant(String isGrant) {
        this.isGrant = isGrant;
    }
}