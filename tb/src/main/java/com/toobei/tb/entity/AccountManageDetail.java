package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 平台账户管理列表 -钟灵-已实现
 */
public class AccountManageDetail extends BaseEntity {


    private static final long serialVersionUID = -2525240871986405409L;

    /**
     * bindDate : 2016-07-27 绑定时间
     * cooperationEndUrl : 测试内容lbfa 	机构合作结束跳转地址
     * deadLineValueText : 测试内容r13h 期限
     * grade : 测试内容1507 安全级别
     * investAmount : 测试内容6m1b 累计投资金额
     * investCount : 测试内容z4xq   累计投资次数
     * isInvested : 0   是否投资 1已投资 0未投资
     * maxDeadLine : 测试内容v6i3   	最大期限
     * maxProfit : 测试内容0d68 	最大收益率
     * minDeadLine : 测试内容45pn   最小期限
     * minProfit : 测试内容orxc 	最小收益率
     * orgAccount : abcdefg 平台帐号
     * orgIsstaticproduct : 36053   是否静态产品
     * orgName : 合盘贷    平台名称
     * orgNumber : 测试内容32x4 	机构编码
     * orgUserProperties : 新用户  	平台用户属性
     * platformListIco : 测试内容4n85   	图标
     * status : 31206   	机构合作状态
     * totalProfix : 测试内容sy03 累计收益
     * usableProductNums : 测试内容2y8c 可投标数
     */

    private String bindDate;// 绑定时间	string
    private String isInvested;// 是否投资 1已投资 0未投资	string
    private String orgAccount;// 平台帐号	string
    private String orgIsstaticproduct;//	是否静态产品	number	是否静态产品 (1：是 ,0：否)
    private String orgName;// 平台名称	string
    private String orgNumber;//	机构编码	string
    private String orgUserProperties;//	平台用户属性	string
    private boolean isHidden=false;

    private String cooperationEndUrl;
    private String deadLineValueText;
    private String grade;
    private String investAmount;
    private String investCount;
private String maxDeadLine;
    private String maxProfit;
    private String minDeadLine;
    private String minProfit;
    private String platformListIco;
    private int status;
    private String totalProfix;
    private String usableProductNums;

    public String getCooperationEndUrl() {
        return cooperationEndUrl;
    }

    public void setCooperationEndUrl(String cooperationEndUrl) {
        this.cooperationEndUrl = cooperationEndUrl;
    }

    public String getDeadLineValueText() {
        return deadLineValueText;
    }

    public void setDeadLineValueText(String deadLineValueText) {
        this.deadLineValueText = deadLineValueText;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(String investAmount) {
        this.investAmount = investAmount;
    }

    public String getInvestCount() {
        return investCount;
    }

    public void setInvestCount(String investCount) {
        this.investCount = investCount;
    }

    public String getMaxDeadLine() {
        return maxDeadLine;
    }

    public void setMaxDeadLine(String maxDeadLine) {
        this.maxDeadLine = maxDeadLine;
    }

    public String getMaxProfit() {
        return maxProfit;
    }

    public void setMaxProfit(String maxProfit) {
        this.maxProfit = maxProfit;
    }

    public String getMinDeadLine() {
        return minDeadLine;
    }

    public void setMinDeadLine(String minDeadLine) {
        this.minDeadLine = minDeadLine;
    }

    public String getMinProfit() {
        return minProfit;
    }

    public void setMinProfit(String minProfit) {
        this.minProfit = minProfit;
    }

    public String getPlatformListIco() {
        return platformListIco;
    }

    public void setPlatformListIco(String platformListIco) {
        this.platformListIco = platformListIco;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTotalProfix() {
        return totalProfix;
    }

    public void setTotalProfix(String totalProfix) {
        this.totalProfix = totalProfix;
    }

    public String getUsableProductNums() {
        return usableProductNums;
    }

    public void setUsableProductNums(String usableProductNums) {
        this.usableProductNums = usableProductNums;
    }

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

    public boolean getHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }



}
