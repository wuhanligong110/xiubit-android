package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/2/13.
 */

public class IncomeDetailType extends BaseEntity {

    public IncomeDetailType(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    private static final long serialVersionUID = -1051668756466164534L;
    private String type ; //
    private String typeName ; //全部，收入，支出

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
