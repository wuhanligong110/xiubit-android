package com.toobei.common.entity;

/**
 * Created by hasee-pc on 2016/12/27.
 */
public class OrgProductUrlEntityData extends BaseEntity {

    private static final long serialVersionUID = -1925936405507433207L;
    /**
     * orgAccount : 第三方机构用户账号
     * orgKey : 	机构公钥
     * orgNumber : 	机构编码
     * orgProductUrl : http://test.touchouwang.net/lh_api/index/product 	机构产品跳转地址
     * requestFrom : 测试内容w1gu 	请求来源
     * sign : 710F41F18250F3E4CB82F765B6EAF712 	签名
     * thirdProductId : 257 第三方机构产品id
     * timestamp : 2016-08-16 15:51:29 	时间戳
     * txId : 测试内容m92v 交易流水号
     */

    private String orgAccount;
    private String orgKey;
    private String orgNumber;
    private String orgProductUrl;
    private String requestFrom;
    private String sign;
    private String thirdProductId;
    private String timestamp;
    private String txId;

    public String getOrgAccount() {
        return orgAccount;
    }

    public void setOrgAccount(String orgAccount) {
        this.orgAccount = orgAccount;
    }

    public String getOrgKey() {
        return orgKey;
    }

    public void setOrgKey(String orgKey) {
        this.orgKey = orgKey;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getOrgProductUrl() {
        return orgProductUrl;
    }

    public void setOrgProductUrl(String orgProductUrl) {
        this.orgProductUrl = orgProductUrl;
    }

    public String getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(String requestFrom) {
        this.requestFrom = requestFrom;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getThirdProductId() {
        return thirdProductId;
    }

    public void setThirdProductId(String thirdProductId) {
        this.thirdProductId = thirdProductId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }
}
