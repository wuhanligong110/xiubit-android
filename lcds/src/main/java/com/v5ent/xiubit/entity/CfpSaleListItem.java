package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明：团队本月销售记录列表-钟灵-已完成
 *
 * @date 2016-12-6
 */
public class CfpSaleListItem extends BaseEntity {
    private static final long serialVersionUID = 2021109741903785319L;
    /**
     * bizTime 业务时间 string
     * feeAmount 推荐奖励金额 string
     * feeRate 佣金率 string
     * headImage 头像 string
     * mobile
     * productName 产品名称 string
     * saleAmount 销售金额 string
     * type 推荐奖励类型：1直接，2间接 string
     * userName 团队成员姓名 string
     */
    private String bizTime;
    private String feeAmount;
    private String feeRate;
    private String headImage;
    private String mobile;
    private String productName;
    private String saleAmount;
    private String type;
    private String userName;
    //V2.2.0 Leader奖励
    private String leaderProfit;
    private String leaderRemark; //leader奖励说明	string	1%团队年化业绩
    //V3.0.0
    private List<AllowanceDatas> allowanceList; //	津贴明细
    private String gradeDesc; //级别描述；例：下级理财师

    public String getGradeDesc() {
        return gradeDesc;
    }

    public void setGradeDesc(String gradeDesc) {
        this.gradeDesc = gradeDesc;
    }

    public List<AllowanceDatas> getAllowanceList() {
        return allowanceList;
    }

    public void setAllowanceList(List<AllowanceDatas> allowanceList) {
        this.allowanceList = allowanceList;
    }

    public String getBizTime() {
        return bizTime;
    }

    public void setBizTime(String bizTime) {
        this.bizTime = bizTime;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    public String getHeadImage() {
        return headImage;  //headImage=92f2ec029dda9a343ddcc06632f4ec7b
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(String saleAmount) {
        this.saleAmount = saleAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getLeaderProfit() {
        return leaderProfit;
    }

    public void setLeaderProfit(String leaderProfit) {
        this.leaderProfit = leaderProfit;
    }

    public String getLeaderRemark() {
        return leaderRemark;
    }

    public void setLeaderRemark(String leaderRemark) {
        this.leaderRemark = leaderRemark;
    }
}