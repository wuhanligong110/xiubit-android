package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/6/26
 */

public class PersonalCenterData extends BaseEntity{
    private static final long serialVersionUID = -151272570074652447L;


    /**
     * authenName : 测试内容lyh5
     * bankCard : 测试内容q630
     * headImage : 测试内容q70h
     * mobile : 测试内容l0li
     */

    private String authenName;
    private String bankCard;
    private String headImage;
    private String mobile;

    public String getAuthenName() {
        return authenName;
    }

    public void setAuthenName(String authenName) {
        this.authenName = authenName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

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
}
