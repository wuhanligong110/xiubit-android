package com.toobei.common.entity;

/**
 * Created by yangLin on 2018/1/5.
 */

public class IdcardRecoData extends BaseEntity {
    private static final long serialVersionUID = 6763725871032449184L;


    /**
     * errorCode : 太原
     * errorMsg : 测试内容0w74
     * idCard : 6222020502010740202
     * name : 中国工商银行
     */

    private String errorCode;
    private String errorMsg;
    private String idCard;
    private String name;

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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
