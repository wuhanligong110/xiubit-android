package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

public class RedPacket extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 689116628673247173L;
    private String deadline;                 // deadline	产品期限 产品限制=2|3有效	string
    private String expireTime;               //	        expireTime	过期时间 格式：yyyy-MM-dd HH:mm:ss	string
    private int investLimit;                //	        investLlimit	投资限制 0=不限|1=用户首投|2=平台首投	number
    private String name;                     //	        name	红包名称	string
    private String platform;                 //	        platform	限制平台 平台限制 =true 有效	string
    private boolean platformLimit;           //	        platformLimit	平台限制 true=限制|false=不限制	boolean
    private int productLimit;                //	        productLimit	产品限制 0=不限|1=限制产品|2=等于产品期限|3=大于等于产品期限	number
    private String productName;              //	        productName	产品名称 （产品限制等于1时有效）	string
    private String redpacketCount;           //	        redpacketCount	红包数量	string
    private String redpacketMoney;           //	        redpacketMoney	红包金额	string
    private String redpacketType;            //	        redpacketType	红包发放类型 0=平台|1=理财师	string
    private String remark;                   //	        remark	红包描述	string
    private String rid;                      //	        rid	红包编号	string
    private String useStatus;                //	        useStatus	使用状态(0=未使用|1=已使用|2=已过期)	string
    private String userImage;                //	        userImage	用户头像	string
    private String userMobile;               //	        userMobile	用户手机	string
    private String userName;                 //	        userName	用户名称	string

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public void setInvestLimit(int investLimit) {
        this.investLimit = investLimit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setPlatformLimit(boolean platformLimit) {
        this.platformLimit = platformLimit;
    }

    public void setProductLimit(int productLimit) {
        this.productLimit = productLimit;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setRedpacketCount(String redpacketCount) {
        this.redpacketCount = redpacketCount;
    }

    public void setRedpacketMoney(String redpacketMoney) {
        this.redpacketMoney = redpacketMoney;
    }

    public void setRedpacketType(String redpacketType) {
        this.redpacketType = redpacketType;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public int getInvestLimit() {
        return investLimit;
    }

    public String getName() {
        return name;
    }

    public String getPlatform() {
        return platform;
    }

    public boolean getPlatformLimit() {
        return platformLimit;
    }

    public int getProductLimit() {
        return productLimit;
    }

    public String getProductName() {
        return productName;
    }

    public String getRedpacketCount() {
        return redpacketCount;
    }

    public String getRedpacketMoney() {
        return redpacketMoney;
    }

    public String getRedpacketType() {
        return redpacketType;
    }

    public String getRemark() {
        return remark;
    }

    public String getRid() {
        return rid;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getUserName() {
        return userName;
    }
}