package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/2/14.
 */
public class IncomeAndOutDetail extends BaseEntity {

    private static final long serialVersionUID = 2874919452316919968L;

    /**
     * amount : 交易金额
     * failureCause : 提现失败原因	status为6、7是失败
     * remark :备注
     * status : 提现状态（支出）  1=提现中| 2、8=已提交银行，待到账| 3审核不通过| 5=提现成功| 、6、7=提现失败
     * tranName : 提现交易名称
     * tranTime : 交易时间
     * withdrawRemark :提现备注（支出）
     * userType :区分那个端（T呗、理财）
     */

    private String amount;
    private String failureCause;
    private String remark;
    private String status;
    private String tranName;
    private String tranTime;
    private String withdrawRemark;
    private String userType;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFailureCause() {
        return failureCause;
    }

    public void setFailureCause(String failureCause) {
        this.failureCause = failureCause;
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
