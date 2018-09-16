package com.toobei.common.entity;


public class BundBankcardData extends BaseEntity {

    private static final long serialVersionUID = -5917189069182930413L;

    private boolean bundBankcard;//银行卡绑定状态(true/false
    
    public boolean onceMoreBindCard; //是否解绑后重新绑卡	boolean
    

    public boolean isBundBankcard() {
        return bundBankcard;
    }

    public void setBundBankcard(boolean bundBankcard) {
        this.bundBankcard = bundBankcard;
    }
}