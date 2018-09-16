package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/2
 */

public class HaveGoodTransNoReadData extends BaseEntity{
    private static final long serialVersionUID = 7302134347871727523L;
    
    private String haveRead;

    public String getHaveRead() {
        return haveRead;
    }

    public void setHaveRead(String haveRead) {
        this.haveRead = haveRead;
    }
}
