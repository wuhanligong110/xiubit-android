package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明：账号余额 月份收益
 * @date 2016-11-23
 */
public class AccountIncome extends BaseEntity {

    private static final long serialVersionUID = 5312590880164157476L;

    private String grantDesc;//发发放描述；例：下月15号发放
    private String month;//月份；例：2016-11
    private String monthDesc;// 月份描述 string 本月收益
    private String totalAmount;// 总计


    public String getGrantDesc() {
        return grantDesc;
    }

    public void setGrantDesc(String grantDesc) {
        this.grantDesc = grantDesc;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonthDesc() {
        return monthDesc;
    }

    public void setMonthDesc(String monthDesc) {
        this.monthDesc = monthDesc;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}