package com.toobei.common.entity;

/**
 * Created by hasee-pc on 2016/12/27.
 */
public class ExistInPlatformData extends BaseEntity {


    private static final long serialVersionUID = 5563020821681462232L;
    /**
     * isExist : false 是否存在
     */

    private boolean isExist;

    public boolean isIsExist() {
        return isExist;
    }

    public void setIsExist(boolean isExist) {
        this.isExist = isExist;
    }
}

