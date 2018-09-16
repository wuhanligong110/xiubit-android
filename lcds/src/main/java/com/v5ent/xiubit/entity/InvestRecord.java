package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;


/**
 * 客户->投资统计 客户本月投资
 */
public class InvestRecord extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6657490484192144895L;

    private String count;//投资笔数
    private String date;//日期格式:2016-07-29 11:41	string
    private String rate;//年化
    private String feeRate;//佣金
    private String investAmt;//金额(投资、赎回)
    private String mobile;//客户手机号码
    private String name;//客户名称
    private String productName;// 产品名称
    private String image;// 用户头像
    private String uid;// 用户编号


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInvestAmt() {
        return investAmt;
    }

    public void setInvestAmt(String investAmt) {
        this.investAmt = investAmt;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductName(int length) {
        if (productName != null && productName.length() > length) {
            return productName.substring(0, length);
        }
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}