package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/3/1.
 */
public class ContribuPageData extends BaseEntity {

    private static final long serialVersionUID = 4372105717486967769L;


    /**
     * contrProfit : 100.00
     * headImage : http://123.jpg
     * remark : 测试内容uqb3
     * saleAmount : 200.00
     * userName : 张三
     */

    private String contrProfit;     //	贡献奖励	string
    private String headImage;        //头像	string
    private String remark;            //说明	string	该成员满足leader奖励独立核算，不再计入贡献明细
    private String saleAmount;      //	销售金额	string
    private String userName;        //	用户名

    public String getContrProfit() {
        return contrProfit;
    }

    public void setContrProfit(String contrProfit) {
        this.contrProfit = contrProfit;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(String saleAmount) {
        this.saleAmount = saleAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
