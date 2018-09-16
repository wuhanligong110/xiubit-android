package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明: 津贴明细
 * v3.0.0
 * @author yangLin
 * @time 2017/4/6
 */

public class AllowanceDatas extends BaseEntity{

    private static final long serialVersionUID = -5308514142587407094L;
    private String amount; //金额
    private String type; //类型； 1推荐奖励，2直接管理，3团队管理
    private String typeDesc;  //类型描述；例：推荐奖励

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }
}
