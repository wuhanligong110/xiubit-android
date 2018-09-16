package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/2/13.
 */

public class ProfixType extends BaseEntity {
    private static final long serialVersionUID = 8981501077399465515L;
    private String profixType;//收益类别id 收益类型：1待发放，2已发放
    private String profixTypeName;//收益类别
    private String amount;//金额

    public ProfixType(String profixType, String profixTypeName, String amount) {
        this.profixType = profixType;
        this.profixTypeName = profixTypeName;
        this.amount = amount;
    }

    public String getProfixType() {
        return profixType;
    }

    public void setProfixType(String profixType) {
        this.profixType = profixType;
    }

    public String getProfixTypeName() {
        return profixTypeName;
    }

    public void setProfixTypeName(String profixTypeName) {
        this.profixTypeName = profixTypeName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
