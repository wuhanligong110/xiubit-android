package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 投呗平台管理 (绑定和没有绑定)
 */
public class InstitutionManageDetail extends BaseEntity {
    private static final long serialVersionUID = -4262352587665749364L;
    private String bindDate;//	绑定时间	string
    private String isInvested;// 是否投资 1已投资 0未投资	string
    private String orgAccount;// 平台帐号	string
    private String orgIsstaticproduct;//	是否静态产品	number	是否静态产品 (1：是 ,0：否)
    private String orgName;// 平台名称	string
    private String orgNumber;//	机构编码	string
    private String orgUserProperties;//	平台用户属性	string


    public String getBindDate() {
        return bindDate;
    }

    public void setBindDate(String bindDate) {
        this.bindDate = bindDate;
    }

    public String getIsInvested() {
        return isInvested;
    }

    public void setIsInvested(String isInvested) {
        this.isInvested = isInvested;
    }

    public String getOrgAccount() {
        return orgAccount;
    }

    public void setOrgAccount(String orgAccount) {
        this.orgAccount = orgAccount;
    }

    public String getOrgIsstaticproduct() {
        return orgIsstaticproduct;
    }

    public void setOrgIsstaticproduct(String orgIsstaticproduct) {
        this.orgIsstaticproduct = orgIsstaticproduct;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getOrgUserProperties() {
        return orgUserProperties;
    }

    public void setOrgUserProperties(String orgUserProperties) {
        this.orgUserProperties = orgUserProperties;
    }
}