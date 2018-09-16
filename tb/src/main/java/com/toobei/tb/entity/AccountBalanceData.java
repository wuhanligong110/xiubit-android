package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/2/10.
 */
public class AccountBalanceData extends BaseEntity {

    private static final long serialVersionUID = -1297655850999354914L;

    private String rewardBalance;  //T呗奖励余额
    private String rewardIncome;  //奖励收入
    private String rewardOut;  //奖励支出

    public String getRewardBalance() {
        return rewardBalance;
    }

    public void setRewardBalance(String rewardBalance) {
        this.rewardBalance = rewardBalance;
    }

    public String getRewardIncome() {
        return rewardIncome;
    }

    public void setRewardIncome(String rewardIncome) {
        this.rewardIncome = rewardIncome;
    }

    public String getRewardOut() {
        return rewardOut;
    }

    public void setRewardOut(String rewardOut) {
        this.rewardOut = rewardOut;
    }
}
