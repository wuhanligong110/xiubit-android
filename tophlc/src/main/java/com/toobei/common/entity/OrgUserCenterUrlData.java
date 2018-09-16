package com.toobei.common.entity;

/**
 * Created by hasee-pc on 2016/12/27.
 */
public class OrgUserCenterUrlData extends BaseEntity {

    private static final long serialVersionUID = -4728623687198001863L;
    /**
     * orgAccount : xyz
     * orgKey : 660E1F6111614123A351B5618E713BFC
     * orgNumber : OPEN_TOUCHOU_WEB
     * orgUsercenterUrl : http://test.touchouwang.net/lh_api/index/personal
     * requestFrom : 测试内容ctx5
     * sign : BA49BA8C7DD128C36CE28DDA0C52D9C7
     * timestamp : 2016-08-16 15:56:03
     */

    private String orgAccount;
    private String orgKey;
    private String orgNumber;
    private String orgUsercenterUrl;
    private String requestFrom;
    private String sign;
    private String timestamp;

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

    public String getOrgUsercenterUrl() {
        return orgUsercenterUrl;
    }

    public void setOrgUsercenterUrl(String orgUsercenterUrl) {
        this.orgUsercenterUrl = orgUsercenterUrl;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
