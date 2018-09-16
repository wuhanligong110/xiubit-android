package com.v5ent.xiubit.entity;

import com.toobei.common.entity.PageListBase;

public class InviteCustomHisDatas extends PageListBase<InviteCustomHisDetail> {

    private static final long serialVersionUID = 2331082201834645424L;


    /**
     * investFlag : 未投资
     * mobile : 15966666666
     * registerDate : 2016-04-19
     * userId : ea87f31c1a724896b088f22e7741ef2e
     * userName :
     */

    private String investFlag;   //investFlag	是否投资	string
    private String mobile;       //	mobile	电话	string
    private String registerDate; //	registerDate	注册时间	string
    private String userId;       //	userId	用户id	string
    private String userName;     //	userName	姓名	string

    public void setInvestFlag(String investFlag) {
        this.investFlag = investFlag;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInvestFlag() {
        return investFlag;
    }

    public String getMobile() {
        return mobile;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}