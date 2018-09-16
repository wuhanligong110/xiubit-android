package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/3/23.
 */

public class UserIsNewData extends BaseEntity{

    private static final long serialVersionUID = -5269842816429072352L;

    private boolean isNew;

    public boolean getIsNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}
