package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;
import com.toobei.common.utils.StringUtil;
import com.toobei.common.utils.Utils;

public class RecommendProductData extends BaseEntity {
    private static final long serialVersionUID = -3203542771735019689L;


    private String cfp;// 是否为理财师	boolean
    private String cfplanner;//             当前理财师	string
    private String createTime;//       创建时间	string
    private String delStatus;//         	状态	number	0可用,1不可用
    private String easemobAcct;//     环信帐号	string
    private String easemobNicknameStatus;//	环信昵称设置	number	0未注册，1已注册
    private String easemobPassword;//      	环信密码	string	环信密码
    private String easemobRegStatus;//         	环信注册状态	number	0未注册，1已注册
    private String firstInvestTime;//               	首次投资时间	string
    private String headImage;//      头像图片url	string
    private String id;//	流水号	number
    private String ifRecommend;//	是否是理财师推荐	number ifRecommend	是否理财师推荐	number	0-否 其他-是
    private String isFreeCustomer;//         	是否自由用户	number	1是0否
    private String isImportant;//       是否重要客户	number	1是，0否
    private String isLocked;//    	是否锁定账户	number	0未锁定 1锁定
    private String mobile;//手机号码	string
    private String qrcode;//            二维码	string
    private String rectInvestTime;//        最近投资时间	string
    private String rectVisitTime;//    最近访问时间	string
    private String refType;//           推荐类型	string	1理财师邀请2客户邀请
    private String refUser;//         推荐用户	string
    private String registerAccessUrl;//           注册受访地址	string
    private String registerFromUrl;//         	注册来源地址	string
    private String updateTime;//	修改时间	string
    private String userId;//   	用户id	string
    private String userName;//      	姓名	string


    private String sortLetters;//      字母排序



    public String isCfp() {
        return cfp;
    }

    public void setCfp(String cfp) {
        this.cfp = cfp;
    }

    public String getCfplanner() {
        return cfplanner;
    }

    public void setCfplanner(String cfplanner) {
        this.cfplanner = cfplanner;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(String delStatus) {
        this.delStatus = delStatus;
    }

    public String getEasemobAcct() {
        return easemobAcct;
    }

    public void setEasemobAcct(String easemobAcct) {
        this.easemobAcct = easemobAcct;
    }

    public String getEasemobNicknameStatus() {
        return easemobNicknameStatus;
    }

    public void setEasemobNicknameStatus(String easemobNicknameStatus) {
        this.easemobNicknameStatus = easemobNicknameStatus;
    }

    public String getEasemobPassword() {
        return easemobPassword;
    }

    public void setEasemobPassword(String easemobPassword) {
        this.easemobPassword = easemobPassword;
    }

    public String getEasemobRegStatus() {
        return easemobRegStatus;
    }

    public void setEasemobRegStatus(String easemobRegStatus) {
        this.easemobRegStatus = easemobRegStatus;
    }

    public String getFirstInvestTime() {
        return firstInvestTime;
    }

    public void setFirstInvestTime(String firstInvestTime) {
        this.firstInvestTime = firstInvestTime;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsFreeCustomer() {
        return isFreeCustomer;
    }

    public void setIsFreeCustomer(String isFreeCustomer) {
        this.isFreeCustomer = isFreeCustomer;
    }

    public String getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(String isImportant) {
        this.isImportant = isImportant;
    }

    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getRectInvestTime() {
        return rectInvestTime;
    }

    public void setRectInvestTime(String rectInvestTime) {
        this.rectInvestTime = rectInvestTime;
    }

    public String getRectVisitTime() {
        return rectVisitTime;
    }

    public void setRectVisitTime(String rectVisitTime) {
        this.rectVisitTime = rectVisitTime;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getRefUser() {
        return refUser;
    }

    public void setRefUser(String refUser) {
        this.refUser = refUser;
    }

    public String getRegisterAccessUrl() {
        return registerAccessUrl;
    }

    public void setRegisterAccessUrl(String registerAccessUrl) {
        this.registerAccessUrl = registerAccessUrl;
    }

    public String getRegisterFromUrl() {
        return registerFromUrl;
    }

    public void setRegisterFromUrl(String registerFromUrl) {
        this.registerFromUrl = registerFromUrl;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getSortLetters() {
        if (sortLetters == null) {
            if (userName != null && userName.length() > 0) {
                String sortString = Utils.getPinYin(userName.substring(0, 1));
                if (!StringUtil.isEmpty(sortString)) {
                    sortString = sortString.toUpperCase()
                            .substring(0, 1);
                    if (sortString.matches("[A-Z]")) {
                        setSortLetters(sortString.toUpperCase());
                    } else {
                        setSortLetters("#");
                    }
                } else {
                    setSortLetters("#");
                }
            } else {
                setSortLetters("#");
            }
        }
        return sortLetters;
    }

    public String getIfRecommend() {
        return ifRecommend;
    }

    public void setIfRecommend(String ifRecommend) {
        this.ifRecommend = ifRecommend;
    }
}