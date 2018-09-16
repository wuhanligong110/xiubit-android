package com.toobei.common.entity;

public class UserLevelInfo extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4189847088840456253L;

    private String cfpBenormalTime;//理财师转正时间
    private String cfpLevel;//理财师级别(1试用期理财师,2实习期理财师,3初级理财师,4中级理财师,5高级理财师,6SENIOR资深理财师,7超级理财师)
    private String cfpLevelName;//理财师级别名称
    private String cfpRegTime;//理财师注册时间
    private String cfpUpdateTime;//升级时间
    private String mobile;//手机号
    private String userName;//姓名
    private String partnerLevel;//合伙人级别(1合伙人,2高级合伙人,3资深合伙人)
    private String partnerLevelName;//合伙人级别名称
    private String partnerRegTime;//合伙人注册时间
    private String partnerUpTime;//合伙人升级时间

    private String userId;
    private String easemobAcct;
    private String easemobPassword;
    private String headImage;


    public String getCfpBenormalTime() {
        return cfpBenormalTime;
    }

    public void setCfpBenormalTime(String cfpBenormalTime) {
        this.cfpBenormalTime = cfpBenormalTime;
    }

    public String getCfpLevel() {
        return cfpLevel;
    }

    public void setCfpLevel(String cfpLevel) {
        this.cfpLevel = cfpLevel;
    }

    public String getCfpLevelName() {
        return cfpLevelName;
    }

    public void setCfpLevelName(String cfpLevelName) {
        this.cfpLevelName = cfpLevelName;
    }

    public String getCfpRegTime() {
        return cfpRegTime;
    }

    public void setCfpRegTime(String cfpRegTime) {
        this.cfpRegTime = cfpRegTime;
    }

    public String getCfpUpdateTime() {
        return cfpUpdateTime;
    }

    public void setCfpUpdateTime(String cfpUpdateTime) {
        this.cfpUpdateTime = cfpUpdateTime;
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

    public String getPartnerLevel() {
        return partnerLevel;
    }

    public void setPartnerLevel(String partnerLevel) {
        this.partnerLevel = partnerLevel;
    }

    public String getPartnerLevelName() {
        return partnerLevelName;
    }

    public void setPartnerLevelName(String partnerLevelName) {
        this.partnerLevelName = partnerLevelName;
    }

    public String getPartnerRegTime() {
        return partnerRegTime;
    }

    public void setPartnerRegTime(String partnerRegTime) {
        this.partnerRegTime = partnerRegTime;
    }

    public String getPartnerUpTime() {
        return partnerUpTime;
    }

    public void setPartnerUpTime(String partnerUpTime) {
        this.partnerUpTime = partnerUpTime;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public UserInfo toUserInfo() {
        return new UserInfo(easemobAcct, userName, mobile, userId, easemobPassword, headImage,
                cfpLevelName,cfpLevel, "true");
    }

}