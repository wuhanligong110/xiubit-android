package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 我的投资(其他)-陈衡-已实现
 * Created by hasee-pc on 2017/1/3.
 */
public class UnRecordInvestDetail extends BaseEntity {

    private static final long serialVersionUID = 6655948356667528830L;
    /**
     * deadLineType : 测试内容vaya 期限类型
     * url : 1  跳转链接
     * deadLine : 期限
     * investAmt : 100.00  投资金额
     * platfromName : 小牛在线  平台名称
     * productName : 月月牛Y60201602   	产品名称
     * time :
     */

    private String deadLineType;
    private String url;
    private String deadLine;
    private String investAmt;
    private String platfromName;
    private String productName;
    private String time;

    public String getDeadLineType() {
        return deadLineType;
    }

    public void setDeadLineType(String deadLineType) {
        this.deadLineType = deadLineType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getInvestAmt() {
        return investAmt;
    }

    public void setInvestAmt(String investAmt) {
        this.investAmt = investAmt;
    }

    public String getPlatfromName() {
        return platfromName;
    }

    public void setPlatfromName(String platfromName) {
        this.platfromName = platfromName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
