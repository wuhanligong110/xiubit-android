package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/6/30
 */

public class InvitationCfgRecordData extends BaseEntity {
    private static final long serialVersionUID = 8342148659695648961L;


    /**
     * haveInvitation : 测试内容4333
     * mobile : 18676798087
     * registerTime : Fri Apr 08 00:00:00 CST 2016
     * userName : 黄亚林
     */

    private String haveInvitation;
    private String mobile;
    private String registerTime;
    private String userName;

    public String getHaveInvitation() {
        return haveInvitation;
    }

    public void setHaveInvitation(String haveInvitation) {
        this.haveInvitation = haveInvitation;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
