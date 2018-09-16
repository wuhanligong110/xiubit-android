package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/7/26
 */

public class GoodTransData extends BaseEntity{
    private static final long serialVersionUID = 4825735654973580744L;


    /**
     * amount : 测试内容4l14
     * billId : 测试内容22p6
     * investTime : 测试内容c3b4
     * userName : 测试内容ny1v
     */

    private String amount;
    private String billId;
    private String investTime;
    private String userName;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
