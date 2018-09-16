package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/6/26
 */

public class InvestRecordData extends BaseEntity {
    private static final long serialVersionUID = 5359168910344705969L;


    /**
     * canRedemption : 41211
     * startTime : 测试内容f5dq
     * endTime : 2016-12-18
     * investAmt : 100
     * platformlistIco : 59340c99d2773b0a917ed2684337a694
     * productName : 信托宝•灵动4186期
     * profit : 0.05
     */

    private int canRedemption;
    private String startTime;
    private String endTime;
    private String investAmt;
    private String platformlistIco;
    private String productName;
    private String profit;

    public int getCanRedemption() {
        return canRedemption;
    }

    public void setCanRedemption(int canRedemption) {
        this.canRedemption = canRedemption;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getInvestAmt() {
        return investAmt;
    }

    public void setInvestAmt(String investAmt) {
        this.investAmt = investAmt;
    }

    public String getPlatformlistIco() {
        return platformlistIco;
    }

    public void setPlatformlistIco(String platformlistIco) {
        this.platformlistIco = platformlistIco;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }
}
