package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/2/10.
 */
public class RewardDetail extends BaseEntity {

    private static final long serialVersionUID = 2746374091870915369L;
    /**
     * amount : 交易金额
     * failureCause : 提现失败原因
     * remark : 备注
     * status : 提现状态
     * tranName : 交易名称
     * tranTime : 交易时间
     * userType : 区分那个端（T呗，貅比特）
     * withdrawRemark : 提现备注
     */

    private String amount;
    private String failureCause;
    private String remark;
    private String status;
    private String tranName;
    private String tranTime;
    private String userType;
    private String withdrawRemark;

    public String getFailureCause() {
        return failureCause;
    }

    public void setFailureCause(String failureCause) {
        this.failureCause = failureCause;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTranName() {
        return tranName;
    }

    public void setTranName(String tranName) {
        this.tranName = tranName;
    }

    public String getTranTime() {
        return tranTime;
    }

    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }

    public String getWithdrawRemark() {
        return withdrawRemark;
    }

    public void setWithdrawRemark(String withdrawRemark) {
        this.withdrawRemark = withdrawRemark;
    }
}
