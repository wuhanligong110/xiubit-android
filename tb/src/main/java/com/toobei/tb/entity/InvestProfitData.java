package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/2/15.
 */
public class InvestProfitData extends BaseEntity {
    private static final long serialVersionUID = -7665121767632900486L;


    /**
     * investAmt	募集中收益	string
     * paymentAmt	回款中收益	string
     * paymentDoneAmt	回款完成收益	string
     */

    private String investAmt;
    private String paymentAmt;
    private String paymentDoneAmt;

    public String getInvestAmt() {
        return investAmt;
    }

    public void setInvestAmt(String investAmt) {
        this.investAmt = investAmt;
    }

    public String getPaymentAmt() {
        return paymentAmt;
    }

    public void setPaymentAmt(String paymentAmt) {
        this.paymentAmt = paymentAmt;
    }

    public String getPaymentDoneAmt() {
        return paymentDoneAmt;
    }

    public void setPaymentDoneAmt(String paymentDoneAmt) {
        this.paymentDoneAmt = paymentDoneAmt;
    }
}
