package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 报表记录
 */
public class BillDeclarationDetail extends BaseEntity {

    private static final long serialVersionUID = -6537507895917577837L;

    private String investAmt;//     	投资金额	string
    private String feeAmt;//     	佣金	string
    private String platfromName;//   	平台名称	string
    private String productName;//   	产品名称	string
    private String remark;//       未通过原因描述	string
    private String status;//      状态 0=未审核|1=审核通过|2=审核不通过
    private String time;//       时间	object
    private String mobile;//       手机号	string
    private String name;//      	姓名	string

    public String getInvestAmt() {
        return investAmt;
    }

    public void setInvestAmt(String investAmt) {
        this.investAmt = investAmt;
    }

    public String getPlatfromName() {
        return platfromName;
    }

    public void setPlatfromName(String platfromName) {
        this.platfromName = platfromName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(String feeAmt) {
        this.feeAmt = feeAmt;
    }
}