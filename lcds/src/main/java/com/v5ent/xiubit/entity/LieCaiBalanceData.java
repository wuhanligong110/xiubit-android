package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/2/14.
 */
public class LieCaiBalanceData extends BaseEntity {
    private static final long serialVersionUID = 8892484263743451224L;

    /**
     * accountBalance : 	账户余额
     * incomeSummary : 累计收入
     * outSummary : 累计支出
     */

    private String accountBalance;
    private String incomeSummary;
    private String outSummary;

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getIncomeSummary() {
        return incomeSummary;
    }

    public void setIncomeSummary(String incomeSummary) {
        this.incomeSummary = incomeSummary;
    }

    public String getOutSummary() {
        return outSummary;
    }

    public void setOutSummary(String outSummary) {
        this.outSummary = outSummary;
    }
}
