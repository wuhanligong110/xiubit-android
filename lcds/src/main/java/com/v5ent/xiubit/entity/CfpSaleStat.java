package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明：理财师销售统计
 *
 * @date 2016-12-6
 */
public class CfpSaleStat extends BaseEntity {


    /**
     * allowance 直接推荐奖励 string
     * childrenAllowance 间接推荐奖励 string
     * salesCount 销售笔数 string
     * totalAmount 销售总额 string
     * totalProfit 总收益
     */

    private String allowance;
    private String childrenAllowance;
    private String salesCount;
    private String totalAmount;
    private String totalProfit;
    private String leaderProfit;
    //3.0.0
    private String directAllowance; //直接管理津贴
    private String teamAllowance; //团队管理津贴

    public String getDirectAllowance() {
        return directAllowance;
    }

    public void setDirectAllowance(String directAllowance) {
        this.directAllowance = directAllowance;
    }

    public String getTeamAllowance() {
        return teamAllowance;
    }

    public void setTeamAllowance(String teamAllowance) {
        this.teamAllowance = teamAllowance;
    }

    public String getAllowance() {
        return allowance;
    }

    public void setAllowance(String allowance) {
        this.allowance = allowance;
    }

    public String getChildrenAllowance() {
        return childrenAllowance;
    }

    public void setChildrenAllowance(String childrenAllowance) {
        this.childrenAllowance = childrenAllowance;
    }

    public String getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(String salesCount) {
        this.salesCount = salesCount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(String totalProfit) {
        this.totalProfit = totalProfit;
    }

    public String getLeaderProfit() {
        return leaderProfit;
    }

    public void setLeaderProfit(String leaderProfit) {
        this.leaderProfit = leaderProfit;
    }
}