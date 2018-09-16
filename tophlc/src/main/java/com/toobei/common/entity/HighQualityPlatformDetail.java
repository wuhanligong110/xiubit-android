package com.toobei.common.entity;

/**
 * home页金融超市平台信息
 */
public class HighQualityPlatformDetail extends BaseEntity {


    private static final long serialVersionUID = 7788350054565419055L;
    private String orgName;
    private String orgIco;//客户详情使用的
    private String orgLogo;//首頁的热门机构使用的
    private String orgNumber;
    private String orgLink;

    public String getOrgLink() {
        return orgLink;
    }

    public void setOrgLink(String orgLink) {
        this.orgLink = orgLink;
    }


    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgLogo() {
        return orgLogo;
    }

    public void setOrgLogo(String orgLogo) {
        this.orgLogo = orgLogo;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getOrgIco() {
        return orgIco;
    }

    public void setOrgIco(String orgIco) {
        this.orgIco = orgIco;
    }
}