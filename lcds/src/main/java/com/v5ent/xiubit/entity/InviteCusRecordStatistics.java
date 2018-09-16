package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

public class InviteCusRecordStatistics extends BaseEntity {

    private static final long serialVersionUID = -3254307164042241802L;
    private String investedCount;        //	investedCount	投资用户数	number
    private String regiteredCount;       //	regiteredCount	注册用户数	number

    public void setInvestedCount(String investedCount) {
        this.investedCount = investedCount;
    }

    public void setRegiteredCount(String regiteredCount) {
        this.regiteredCount = regiteredCount;
    }

    public String getInvestedCount() {
        return investedCount;
    }

    public String getRegiteredCount() {
        return regiteredCount;
    }
}