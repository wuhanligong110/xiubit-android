package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/3/1.
 */
public class LeaderProfitData extends BaseEntity {
    private static final long serialVersionUID = 4191384180498910453L;


    /**
     * contrProfit : 100.00
     * indiNumbers : 10
     * monthProfit : 200.00
     * totalProfit : 500.00
     */

    private String contrProfit;    //	间接理财师贡献奖励	string
    private String indiNumbers;    //	间接理财师人数	string
    private String monthProfit;    //	本月奖励	string
    private String totalProfit;    //	累计奖励	string
    private String directNumbers;  //   直接理财师人数
    private String haveLeader; //   是否满足leader奖励  0=满足1=不满足

    public String getDirectNumbers() {
        return directNumbers;
    }

    public void setDirectNumbers(String directNumbers) {
        this.directNumbers = directNumbers;
    }

    public String getContrProfit() {
        return contrProfit;
    }

    public void setContrProfit(String contrProfit) {
        this.contrProfit = contrProfit;
    }

    public String getIndiNumbers() {
        return indiNumbers;
    }

    public void setIndiNumbers(String indiNumbers) {
        this.indiNumbers = indiNumbers;
    }

    public String getMonthProfit() {
        return monthProfit;
    }

    public void setMonthProfit(String monthProfit) {
        this.monthProfit = monthProfit;
    }

    public String getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(String totalProfit) {
        this.totalProfit = totalProfit;
    }

    public String getHaveLeader() {
        return haveLeader;
    }

    public void setHaveLeader(String haveLeader) {
        this.haveLeader = haveLeader;
    }
}
