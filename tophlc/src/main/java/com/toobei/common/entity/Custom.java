package com.toobei.common.entity;

import com.toobei.common.utils.PinyinUtils;
import com.toobei.common.view.timeselector.Utils.TextUtil;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明：客户 实体
 *
 * @date 2015-10-20
 */
public class Custom extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2770902095703102239L;


    /* 客户详情*/
    private String minTime;//交易记录最小时间
    private String memberLevel;//会员等级
    private String memberLevelDesc;//会员等级
    private String registerDate;//注册时间
    private String firstRcpDate;//首单时间
    private String currInvestAmt;//当前在投(单位元)
    private String feeAmt;//获取佣金(单位元)
    private String easemobPassword;
    private String newRegist;
    private String investFlag;//是否投资(true已投资)
    private String profitDesc;//收益描述 (跟产品沟通暂时没用到)

    /*下面字段客户列表*/
    private String easemobAcct;  //环信帐号	string
    private String firstLetter; // 姓名首字母	string
    private String freecustomer;//是否自由客户：1-是|0-否
    private String headImage;
    private String important;//是否为重要客户 true是
    private String isRead;//读 0未读 1已读	string
    private String mobile;//客户手机号码
    private String nearEndDate;//最近回款时间	string
    private String nearInvestAmt;//最近投资金额	string
    private String nearInvestDate;//最近投资时间
    private String registerTime;//注册时间
    private String totalInvestAmt;//历史投资总额(单位元)  //totalInvestAmt=2000.0000
    private String totalInvestCount;//累计投资次数	string

    private String userId;  //用户ID
    private String userName;//客户名称

    //1.2.1 registeredOrgList	已注册平台列表	array<object>
    private List<HighQualityPlatformDetail> registeredOrgList;//已注册平台列表	array<object>
    private String registeredOrgCount;//已注册平台数量

    /*邀请列表*/
    private String customerName;
    private String customerMobile;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNearEndDate() {
        return nearEndDate;
    }

    public void setNearEndDate(String nearEndDate) {
        this.nearEndDate = nearEndDate;
    }

    public String getTotalInvestCount() {
        return totalInvestCount;
    }

    public void setTotalInvestCount(String totalInvestCount) {
        this.totalInvestCount = totalInvestCount;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getMemberLevelDesc() {
        return memberLevelDesc;
    }

    public void setMemberLevelDesc(String memberLevelDesc) {
        this.memberLevelDesc = memberLevelDesc;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getFirstRcpDate() {
        return firstRcpDate;
    }

    public void setFirstRcpDate(String firstRcpDate) {
        this.firstRcpDate = firstRcpDate;
    }

    public String getTotalInvestAmt() {
        return totalInvestAmt;
    }

    public void setTotalInvestAmt(String totalInvestAmt) {
        this.totalInvestAmt = totalInvestAmt;
    }

    public String getCurrInvestAmt() {
        return currInvestAmt;
    }

    public void setCurrInvestAmt(String currInvestAmt) {
        this.currInvestAmt = currInvestAmt;
    }

    public String getNearInvestAmt() {
        return nearInvestAmt;
    }

    public void setNearInvestAmt(String nearInvestAmt) {
        this.nearInvestAmt = nearInvestAmt;
    }

    public String getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(String feeAmt) {
        this.feeAmt = feeAmt;
    }

    public String getMinTime() {
        return minTime;
    }

    public void setMinTime(String minTime) {
        this.minTime = minTime;
    }

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {

        this.important = important;

    }

    public void setImportantBool(boolean important) {
        if (important) {
            this.important = "true";
        } else {
            this.important = "false";
        }
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getFreecustomer() {
        return freecustomer;
    }

    public void setFreecustomer(String freecustomer) {
        this.freecustomer = freecustomer;
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

    public String getNewRegist() {
        return newRegist;
    }

    public void setNewRegist(String newRegist) {
        this.newRegist = newRegist;
    }

    public String getInvestFlag() {
        return investFlag;
    }

    public void setInvestFlag(String investFlag) {
        this.investFlag = investFlag;
    }

    public String getProfitDesc() {
        return profitDesc;
    }

    public void setProfitDesc(String profitDesc) {
        this.profitDesc = profitDesc;
    }

    public String getHeadImage() {
        return  headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public UserInfo toUserInfo(String isCfp) {
        return new UserInfo(easemobAcct, userName, mobile, userId, easemobPassword, headImage, null, null, isCfp);
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getFirstLetter() {
        String pinyin = PinyinUtils.getPinyin(userName);
        if (!TextUtil.isEmpty(pinyin)) return firstLetter = pinyin.substring(0,1);
        else return "#";
    }

    public String getNearInvestDate() {
        return nearInvestDate;
    }

    public void setNearInvestDate(String nearInvestDate) {
        this.nearInvestDate = nearInvestDate;
    }


    public List<HighQualityPlatformDetail> getRegisteredOrgList() {
        return registeredOrgList;
    }

    public void setRegisteredOrgList(List<HighQualityPlatformDetail> registeredOrgList) {
        this.registeredOrgList = registeredOrgList;
    }

    public String getRegisteredOrgCount() {
        return registeredOrgCount;
    }

    public void setRegisteredOrgCount(String registeredOrgCount) {
        this.registeredOrgCount = registeredOrgCount;
    }
}