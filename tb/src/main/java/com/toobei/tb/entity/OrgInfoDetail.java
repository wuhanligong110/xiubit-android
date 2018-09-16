package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

import java.util.List;

/**
 * 机构info
 */
public class OrgInfoDetail extends BaseEntity {
//	接口名称 3.3.2 平台列表 - 黄亚林
//	请求类型 post
//	请求Url /platfrom/pageList
//			请求参数列表
//	变量名	含义	类型	备注
//	pageIndex	第几页 >=1,默认为1	number
//	pageSize	页面记录数，默认为10	number
//	productDeadLine	筛选条件:产品期限(可为空)	string	传入格式要求：min_dead_line <30
//	securityLevel	筛选条件:安全评级(可为空)	string	传入格式要求： >=4
//	token	登录令牌	string
//	yearProfit	筛选条件:年化收益(可为空)	string	传入格式要求： min_profit >=8 and max_profit <=12

    private static final long serialVersionUID = 679447947750074217L;

    private String maxDeadLine;                                //       grade	安全评级	string
    private String maxDeadLineType;                            // 	        maxDeadLine	最大产品期限	string	最大产品期限类型为1时返回 "xx天"，为2时返回 "xx个月"
    private String maxFeeRatio;                                // 	        maxDeadLineType	最大产品期限类型	string	最大产品期限类型(1=天数|2=自然月)
    private String minDeadLine;                                // 	        maxFeeRatio	最大佣金率	string	仅理财师端app可见
    private String minDeadLineType;                            // 	        maxProfit	最大年化收益	string
    private String minFeeRatio;                                // 	        minDeadLine	最小产品期限	string	最小产品期限类型为1时返回 "xx天"，为2时直接返回 "xx个月"
    private String grade;                                      // 	        minDeadLineType	最小产品期限类型	string	最小产品期限类型(1=天数|2=自然月)
    private String maxProfit;                                  // 	        minFeeRatio	最小佣金率	string	仅理财师端app可见
    private String minProfit;                                  // 	        minProfit	最小年化收益	string
    private String name;                                       // 	        name	机构名称	string
    private String orgNumber;                                  // 	        orgNumber	机构编码	string
    private String orgUrl;                                     // 	        orgUrl	平台url	string
    private String platformIco;                                // 	        platformIco	平台logo	string
    private String usableProductNums;                          // 	        usableProductNums	可投的产品数量	string
    private String deadLineValueText;                          // 	       机构产品期限
    private String orgFeeRatio;                          // 	       年化收益
    private String orgAdvantage;                          // 	       年化收益
    private String orgTag;                          // 	       年化收益

    //V2.0 机构活动信息 T呗/理财师2.0版本
    private List<OrgActivitys> orgActivitys;


    // 以下字段是用于T呗中的理财师推荐机构而不是机构列表
    private Boolean hashRedpacket;// 	机构是否有红包(false/true)	boolean	2.0.4新增
    private String orgAdvertisement;//    机构广告	string
    private String orgAdvertisementUrl;// 	机构广告跳转链接	string
    private String orgLogo;//	机构Logo	string
    private String orgName;//    机构名称	string
    private String orgInvestTag;   //orgInvestTag	T呗端机构自定义标签	string	T呗2.0版本(多个以英文逗号分隔)

    public String getMaxDeadLine() {
        return maxDeadLine;
    }

    public void setMaxDeadLine(String maxDeadLine) {
        this.maxDeadLine = maxDeadLine;
    }

    public String getMaxDeadLineType() {
        return maxDeadLineType;
    }

    public void setMaxDeadLineType(String maxDeadLineType) {
        this.maxDeadLineType = maxDeadLineType;
    }

    public String getMaxFeeRatio() {
        return maxFeeRatio;
    }

    public void setMaxFeeRatio(String maxFeeRatio) {
        this.maxFeeRatio = maxFeeRatio;
    }

    public String getMinDeadLine() {
        return minDeadLine;
    }

    public void setMinDeadLine(String minDeadLine) {
        this.minDeadLine = minDeadLine;
    }

    public String getMinDeadLineType() {
        return minDeadLineType;
    }

    public void setMinDeadLineType(String minDeadLineType) {
        this.minDeadLineType = minDeadLineType;
    }

    public String getMinFeeRatio() {
        return minFeeRatio;
    }

    public void setMinFeeRatio(String minFeeRatio) {
        this.minFeeRatio = minFeeRatio;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMaxProfit() {
        return maxProfit;
    }

    public void setMaxProfit(String maxProfit) {
        this.maxProfit = maxProfit;
    }

    public String getMinProfit() {
        return minProfit;
    }

    public void setMinProfit(String minProfit) {
        this.minProfit = minProfit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getOrgUrl() {
        return orgUrl;
    }

    public void setOrgUrl(String orgUrl) {
        this.orgUrl = orgUrl;
    }

    public String getPlatformIco() {
        return platformIco;
    }

    public void setPlatformIco(String platformIco) {
        this.platformIco = platformIco;
    }

    public String getUsableProductNums() {
        return usableProductNums;
    }

    public void setUsableProductNums(String usableProductNums) {
        this.usableProductNums = usableProductNums;
    }


    public String getDeadLineValueText() {
        return deadLineValueText;
    }

    public void setDeadLineValueText(String deadLineValueText) {
        this.deadLineValueText = deadLineValueText;
    }

    public String getOrgFeeRatio() {
        return orgFeeRatio;
    }

    public void setOrgFeeRatio(String orgFeeRatio) {
        this.orgFeeRatio = orgFeeRatio;
    }

    public String getOrgAdvantage() {
        return orgAdvantage;
    }

    public void setOrgAdvantage(String orgAdvantage) {
        this.orgAdvantage = orgAdvantage;
    }

    public String getOrgTag() {
        return orgTag;
    }

    public void setOrgTag(String orgTag) {
        this.orgTag = orgTag;
    }

    public String getOrgInvestTag() {
        return orgInvestTag;
    }

    public void setOrgInvestTag(String orgInvestTag) {
        this.orgInvestTag = orgInvestTag;
    }

    public List<OrgActivitys> getOrgActivitys() {
        return orgActivitys;
    }

    public void setOrgActivitys(List<OrgActivitys> orgActivitys) {
        this.orgActivitys = orgActivitys;
    }

    public Boolean getHashRedpacket() {
        return hashRedpacket;
    }

    public void setHashRedpacket(Boolean hashRedpacket) {
        this.hashRedpacket = hashRedpacket;
    }

    public String getOrgAdvertisement() {
        return orgAdvertisement;
    }

    public void setOrgAdvertisement(String orgAdvertisement) {
        this.orgAdvertisement = orgAdvertisement;
    }

    public String getOrgAdvertisementUrl() {
        return orgAdvertisementUrl;
    }

    public void setOrgAdvertisementUrl(String orgAdvertisementUrl) {
        this.orgAdvertisementUrl = orgAdvertisementUrl;
    }

    public String getOrgLogo() {
        return orgLogo;
    }

    public void setOrgLogo(String orgLogo) {
        this.orgLogo = orgLogo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public class OrgActivitys extends BaseEntity {
        private String activityName;
        private String linkUrl;
        private String shareDesc;           //	        shareDesc		string
        private String shareIcon;           //	        shareIcon		string
        private String shareLink;           //	        shareLink		string
        private String shareTitle;          //	        shareTitle		string

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getShareDesc() {
            return shareDesc;
        }

        public void setShareDesc(String shareDesc) {
            this.shareDesc = shareDesc;
        }

        public String getShareIcon() {
            return shareIcon;
        }

        public void setShareIcon(String shareIcon) {
            this.shareIcon = shareIcon;
        }

        public String getShareLink() {
            return shareLink;
        }

        public void setShareLink(String shareLink) {
            this.shareLink = shareLink;
        }

        public String getShareTitle() {
            return shareTitle;
        }

        public void setShareTitle(String shareTitle) {
            this.shareTitle = shareTitle;
        }
    }
}