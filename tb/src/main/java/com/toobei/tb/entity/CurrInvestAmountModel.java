package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/2/21.
 */
public class CurrInvestAmountModel extends BaseEntity {
    private static final long serialVersionUID = -2257790737302944195L;

    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
