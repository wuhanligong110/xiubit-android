package com.toobei.common.entity;


public class BankCardInfo extends BaseEntity {

    private static final long serialVersionUID = 8877252060074885609L;

    private String bankCard;// 银行卡号
    private String bankName;// 银行名称
    private String city;// 城市 string
    private String fee;//手续费金额,单位: 元 string
    private boolean hasFee;//是否需要提现手续费 boolean
    private String kaiHuHang;//开户行 string
    private int limitTimes;//剩余免费提现次数 number
    private String mobile;// 手机号码 string
    private String totalAmount;// 账户余额 string
    private String userName;// 客户姓名 string

    private String idCard;// 身份证 string

    private String paymentDate;// 预计2016-8-31 24点前
    private boolean needkaiHuHang;// 是否需要开户行 boolean
    private String province;// 省份 string
    private String remark;
    
    private String haveBind;  //0否 1绑定过
    public String withdrawRemark;  //到账提示信息
    public String image;  //银行卡图片
    public String ico; //银行logo

    public String getHaveBind() {
        return haveBind;
    }

    public void setHaveBind(String haveBind) {
        this.haveBind = haveBind;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public boolean isHasFee() {
        return hasFee;
    }

    public void setHasFee(boolean hasFee) {
        this.hasFee = hasFee;
    }

    public String getKaiHuHang() {
        return kaiHuHang;
    }

    public void setKaiHuHang(String kaiHuHang) {
        this.kaiHuHang = kaiHuHang;
    }

    public int getLimitTimes() {
        return limitTimes;
    }

    public void setLimitTimes(int limitTimes) {
        this.limitTimes = limitTimes;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public boolean isNeedkaiHuHang() {
        return needkaiHuHang;
    }

    public void setNeedkaiHuHang(boolean needkaiHuHang) {
        this.needkaiHuHang = needkaiHuHang;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
}