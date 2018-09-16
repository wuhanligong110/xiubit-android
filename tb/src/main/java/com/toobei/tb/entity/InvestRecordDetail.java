package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 我的投资-陈衡-已实现
 * Created by hasee-pc on 2017/1/3.
 */
public class InvestRecordDetail extends BaseEntity {

    private static final long serialVersionUID = 7719293230254951413L;

    /**
     * platfrom : 测试内容4sf4 		平台编号
     * platfromName : 1 	平台名称
     * day : 180 产品期限
     * dayType : 1 是否固定期限(1=固定期限|2=浮动期限)
     * endDate : 2017-01-23  	到期时间
     * investAmount : 123456  	投资金额
     * productId : b73bb12e-c4e2-4065-8328-2a49b8c70a46  产品编号
     * productName : 产品名称（李启） 产品名称
     * profit : 2400  预期收益
     * startDate : 2016-07-27 起息日期
     * updateDate : 2016-07-27  更新时间
     */

    private String platfrom;
    private String platfromName;
    private String day;
    private String dayType;
    private String endDate;
    private String investAmount;
    private String productId;
    private String productName;
    private String profit;
    private String startDate;
    private String updateDate;

    public String getPlatfrom() {
        return platfrom;
    }

    public void setPlatfrom(String platfrom) {
        this.platfrom = platfrom;
    }

    public String getPlatfromName() {
        return platfromName;
    }

    public void setPlatfromName(String platfromName) {
        this.platfromName = platfromName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(String investAmount) {
        this.investAmount = investAmount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
