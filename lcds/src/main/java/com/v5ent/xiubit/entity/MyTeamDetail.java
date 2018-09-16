package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

public class MyTeamDetail extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8293097892568740184L;

    // 上面字段用于我的团体和团队详情
    private String allowance;//直接津贴
    private String childrenAllowance;//间接津贴
    private String childrenCount;//下级人数
    private String isRead;//是否已读 ???
    private String mobile;//手机号码
    private String newSubordinateCount; //下级新增成员数量
    private String registerTime; //注册时间
    private String userId;//用户编码
    private String userName;//用户名
    //V2.0.2添加  成员头像
    private String headImage;//用户名
    //3.0.0
    private String directAllowance;	//直接管理津贴	string	3.0
    private String grandChildrenCount;	//下下级人数	string	3.0
    private String teamAllowance;	//团队管理津贴	string	3.0
    private String jobGrade; //职级；例：见习	string	3.0
    private String newGrandChildrenCount; //下下级新增成员

    // 以下字段用于成员详情
    private String totalFee;//累计佣金
    private String newPatner; //是否新成员 1是2否
    private String cfgLevelName;//理财师等级名称
    private String firstRcpDate;//首单时间
    private String quarterFee;//本季佣金
    private String totalAllowance;//总接津贴
    private String registerDate;//总接津贴

    private String leaderReward;//leader奖励

    public String getJobGrade() {
        return jobGrade;
    }

    public void setJobGrade(String jobGrade) {
        this.jobGrade = jobGrade;
    }

    public String getNewGrandChildrenCount() {
        return newGrandChildrenCount;
    }

    public void setNewGrandChildrenCount(String newGrandChildrenCount) {
        this.newGrandChildrenCount = newGrandChildrenCount;
    }

    public String getDirectAllowance() {
        return directAllowance;
    }

    public void setDirectAllowance(String directAllowance) {
        this.directAllowance = directAllowance;
    }

    public String getGrandChildrenCount() {
        return grandChildrenCount;
    }

    public void setGrandChildrenCount(String grandChildrenCount) {
        this.grandChildrenCount = grandChildrenCount;
    }

    public String getTeamAllowance() {
        return teamAllowance;
    }

    public void setTeamAllowance(String teamAllowance) {
        this.teamAllowance = teamAllowance;
    }

    public String getLeaderReward() {
        return leaderReward;
    }

    public void setLeaderReward(String leaderReward) {
        this.leaderReward = leaderReward;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getAllowance() {
        return allowance;
    }

    public void setAllowance(String allowance) {
        this.allowance = allowance;
    }

    public String getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(String childrenCount) {
        this.childrenCount = childrenCount;
    }

    public String getChildrenAllowance() {
        return childrenAllowance;
    }

    public void setChildrenAllowance(String childrenAllowance) {
        this.childrenAllowance = childrenAllowance;
    }

    public String getCfgLevelName() {
        return cfgLevelName;
    }

    public void setCfgLevelName(String cfgLevelName) {
        this.cfgLevelName = cfgLevelName;
    }


    public String getFirstRcpDate() {
        return firstRcpDate;
    }

    public void setFirstRcpDate(String firstRcpDate) {
        this.firstRcpDate = firstRcpDate;
    }

    public String getQuarterFee() {
        return quarterFee;
    }

    public void setQuarterFee(String quarterFee) {
        this.quarterFee = quarterFee;
    }

    public String getTotalAllowance() {
        return totalAllowance;
    }

    public void setTotalAllowance(String totalAllowance) {
        this.totalAllowance = totalAllowance;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getNewPatner() {
        return newPatner;
    }

    public void setNewPatner(String newPatner) {
        this.newPatner = newPatner;
    }

    public String getNewSubordinateCount() {
        return newSubordinateCount;
    }

    public void setNewSubordinateCount(String newSubordinateCount) {
        this.newSubordinateCount = newSubordinateCount;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }
}
