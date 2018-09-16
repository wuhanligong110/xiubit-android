package com.toobei.common.entity;


public class AccountHomeData extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -7485606892491302691L;
	
//	private String accountBalance;//账户余额
	private String totalAmount;//账户余额
	private String blockedAmount;//提现中

    private String totalIncome;//累计收益
    private String userType; //用户类型

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getTotalAmount() {
        return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getBlockedAmount() {
		return blockedAmount;
	}
	public void setBlockedAmount(String blockedAmount) {
		this.blockedAmount = blockedAmount;
	}
	

	
}