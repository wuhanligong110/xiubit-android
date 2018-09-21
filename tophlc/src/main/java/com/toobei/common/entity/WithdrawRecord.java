package com.toobei.common.entity;


/**
 * 公司: tophlc
 * 类说明：提现记录 实体
 *
 * @date 2015-10-23
 */
public class WithdrawRecord extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2859898847864026612L;
    private String orderId;//提现流水号
    private String partnerId; //业务方id
    private String userId;//用户id
    private String userName; //用户姓名
    private String bisName; //交易名称
    private String transDate;//提现时间
    private String transAmount;//提现金额
    private String amount;//实际到账金额
    private String fee;//手续费
    private String status;//提现处理状态
    private String paymentDate;//预计到账时间
    private String userType;//区分哪个端(T呗，貅比特)
    private String remark; //提现失败显示字段	string	提现状态6跟7时显示

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
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

    public String getBisName() {
        return bisName;
    }

    public void setBisName(String bisName) {
        this.bisName = bisName;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 功能：("0",  "提现记录"),
     * ("1",  "可以审核提现资金已冻结"),
     * ("2",  "审核通过，要查询支付平台打款结果"),
     * ("3",  "审核不通过，解冻"),
     * ("4",  "冻结失败"),
     * ("5",  "提现成功"),
     * ("6",  "打款失败，需要解冻"),
     * ("7",  "打款失败，已处理解冻")
     *
     * @return
     */
    public String getStatusStr() {
        if (status == null)
            return "";
        String str = "";
        switch (Integer.parseInt(status)) {
            case 5:
                str = "提现成功";
                break;

            default:
                str = "提现中";
                break;
        }
        return str;
    }

}