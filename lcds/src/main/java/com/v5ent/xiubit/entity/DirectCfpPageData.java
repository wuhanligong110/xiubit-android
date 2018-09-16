package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/3/1.
 */
public class DirectCfpPageData extends BaseEntity {
    private static final long serialVersionUID = -177424541505298958L;

    /**
     * headImage : setHeadImage
     * mobile : 135****0524
     * userName : userName
     */

    private String headImage;
    private String mobile;
    private String userName;

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
