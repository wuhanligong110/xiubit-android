package com.toobei.common.entity;


public class BankInfo extends BaseEntity {

    private static final long serialVersionUID = -5240103008883041693L;
    private String bankCode = "";// 银行ＣＯＤＥ
    private String bankName = "";// 银行名称
    private String bankId = "";// 银行ID

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
}