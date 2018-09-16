package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/1/4.
 */
public class AccountDetail extends BaseEntity {

    private static final long serialVersionUID = 4495745073873311341L;


    /**
     * fee : 测试内容e3u5 	手续费
     * remark : 	备注
     * transAmount : 1.50 	交易金额
     * transDate : 2016-07-27T14:32:13+08:00  	交易日期
     * typeName : 【佣金明细】  交易类型
     */

    private String fee;
    private String remark;
    private String transAmount;
    private String transDate;
    private String typeName;

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
