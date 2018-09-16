package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/2/15.
 */

public class InvestProfitType extends BaseEntity {

    private static final long serialVersionUID = -642178293478540707L;
    private String name;//收益名称
    private String value;//收益金额

    public InvestProfitType(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
