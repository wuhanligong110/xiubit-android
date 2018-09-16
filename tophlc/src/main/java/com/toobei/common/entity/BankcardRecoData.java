package com.toobei.common.entity;

/**
 * Created by yangLin on 2018/1/5.
 */

public class BankcardRecoData extends BaseEntity {
    private static final long serialVersionUID = 6289321543114309495L;

    /**
     * bankCard : false
     * errorCode : 预计2017-12-23 24点前
     * errorMsg : 山西
     */

    private String bankCard;
    private String errorCode;
    private String errorMsg;

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
