package com.toobei.common.entity;

import com.toobei.common.TopApp;
import com.toobei.common.utils.StringUtil;

import org.xsl781.db.annotation.PrimaryKey;
import org.xsl781.db.annotation.PrimaryKey.AssignType;
import org.xsl781.db.annotation.Table;

@Table("UserInfo")
public class UserInfo extends BaseEntity {

    @Override
    public String toString() {
        return "UserInfo{" +
                "easemobAcct='" + easemobAcct + '\'' +
                ", cfpLevelName='" + cfpLevelName + '\'' +
                ", cfpLevel='" + cfpLevel + '\'' +
                ", userName='" + userName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userId='" + userId + '\'' +
                ", easemobPassword='" + easemobPassword + '\'' +
                ", isCfp='" + isCfp + '\'' +
                ", headImage='" + headImage + '\'' +
                '}';
    }

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8807709150237695203L;

    @PrimaryKey(AssignType.BY_MYSELF)
    private String easemobAcct;//环信用户名

    private String cfpLevelName;//理财师等级
    private String cfpLevel;//理财师等级
    private String userName;
    private String mobile;//手机号
    private String userId;
    private String easemobPassword;//环信密码
    private String isCfp;//是否理财师

    private String headImage; //headImage=9942d62b17476c01a50af381792af166

    public UserInfo() {
        super();
    }

    public UserInfo(String easemobAcct, String userName, String mobile, String userId,
                    String easemobPassword, String image, String cfpLevelName, String cfpLevel, String isCfp) {
        super();
        this.easemobAcct = easemobAcct;
        this.userName = userName;
        this.mobile = mobile;
        this.userId = userId;
        this.easemobPassword = easemobPassword;
        this.headImage = image;
        this.isCfp = isCfp;
        this.cfpLevel = cfpLevel;
        this.cfpLevelName = cfpLevelName;
    }

    public String getCfpLevelName() {
        return cfpLevelName;
    }

    public void setCfpLevelName(String cfpLevelName) {
        this.cfpLevelName = cfpLevelName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEasemobAcct() {
        return easemobAcct;
    }

    public void setEasemobAcct(String easemobAcct) {
        this.easemobAcct = easemobAcct;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNameOrPhone() {
        if (userName != null && userName.length() > 0 && !userName.equals("未认证")) {
            return userName;
        }
        return mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEasemobPassword() {
        return easemobPassword;
    }

    public void setEasemobPassword(String easemobPassword) {
        this.easemobPassword = easemobPassword;
    }

    public String getHeadImage() {
        return TopApp.getInstance().getHttpService().getImageServerBaseUrl() + headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getIsCfp() {
        return isCfp;
    }

    public void setIsCfp(String isCfp) {
        this.isCfp = isCfp;
    }

    public boolean isCfp() {
        return isCfp != null && isCfp.equals("true");
    }

    public String getCfpLevel() {
        return cfpLevel;
    }

    public void setCfpLevel(String cfpLevel) {
        this.cfpLevel = cfpLevel;
    }

    public int getCfpLevelInt() {
        if (!StringUtil.isEmpty(cfpLevel)) {
            try {
                return Integer.parseInt(cfpLevel);
            } catch (Exception e) {
            }
        }
        return 0;
    }
}