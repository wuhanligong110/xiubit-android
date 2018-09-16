package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by Administrator on 2016/9/7 0007.
 */
public class MycfpInfo extends BaseEntity {

//    "cfpIsSelf": "1",
//            "cfpLevel": "1",
//            "cfpLevelName": "理财师",
//            "easemobAcct": "cfpc1add0a7c3e2438f853ae270e975d958",
//            "easemobPassword": "pwc1add0a7c3e2438f853ae270e975d958",
//            "headImage": "d8e1df790c6c12c2c91988f066e87d7a",
//            "levelExperience": "0",
//            "mobile": "18782985332",
//            "userName": "青烨宸"
    private static final long serialVersionUID = -7830287466529733926L;

    private String easemobAcct;//	环信帐号	string
    private String easemobPassword;// 环信密码	string
    private String mobile;//         手机号码	string
    private String userName;//       姓名	string
    private String cfpLevelName;//     理财师级别
    private String headImage;//        头像

    public String getEasemobAcct() {
        return easemobAcct;
    }

    public void setEasemobAcct(String easemobAcct) {
        this.easemobAcct = easemobAcct;
    }

    public String getEasemobPassword() {
        return easemobPassword;
    }

    public void setEasemobPassword(String easemobPassword) {
        this.easemobPassword = easemobPassword;
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

    public String getCfpLevelName() {
        return cfpLevelName;
    }

    public void setCfpLevelName(String cfpLevelName) {
        this.cfpLevelName = cfpLevelName;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }
}
