package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

import java.util.List;

/**
 * 机构info
 */
public class PlatformDetail extends BaseEntity {


    private static final long serialVersionUID = -5469439431216989734L;


    /**
     * capital : 100000000
     * city : 深圳
     * deadLineMaxSelfDefined : 测试内容io5h
     * deadLineMinSelfDefined : 测试内容m3d1
     * deadLineValueText : 测试内容4kl3
     * feeRateMax : 20.00
     * feeRateMin : 16.50
     * icp : 京ICP备15015561号-2
     * orgActivitys : [{"linkUrl":"测试内容hemz","platformImg":"测试内容d85z"}]
     * orgAdvantage : 测试内容vrdo
     * orgBack : 中国平安旗下
     * orgDynamicList : [{"id":68185,"orgDynamicUrl":"测试内容51rj","orgNumber":"测试内容1y4z","orgTitle":"测试内容vq8n"}]
     * orgEnvironmentList : [{"orgNumber":"测试内容7net","orgPicture":"测试内容5run"}]
     * orgHonor : 测试内容3si7
     * orgHonorList : [{"orgNumber":"测试内容17gq","orgPicture":"测试内容66nk"}]
     * orgInvestStrategy : 测试内容2p16
     * orgIsstaticproduct : 43245
     * orgJointType : 84848
     * orgLevel : BB
     * orgLogo : 测试内容q9k8
     * orgName : 陆金所
     * orgNo : 8888888
     * orgPapersList : [{"orgNumber":"测试内容lw5r","orgPicture":"测试内容v883"}]
     * orgPlannerStrategy : 测试内容6fvg
     * orgProductTag : 测试内容3i8y
     * orgProfile : 陆金所金融信息服务股份有限公司总部位于上海，注册资本5000万。合盘贷（www.hepandai.com）是上海合盘金融信息服务股份有限公司倾力打造的互联网金融平台，在线实现资金需求与供给的完美交互，为小微企业、三农、个体工商户提供专业的融资服务，为普通大众阶层提供全方位的理财服务。
     * orgSecurity : 我们将会通过与平安银行的资金存管、风险准备金计提、严格规范的风控措施以及安全的信息技术，保障您的每一笔交易安全可靠，稳定收益，理财无忧。
     * orgTag : 测试内容6p3i
     * orgUrl : http://mchannel.xiaoniuapp.com/pages/activities/thanks.html
     * platformIco : 测试内容hnq9
     * proDaysMax : 180天
     * proDaysMin : 60天
     * trusteeship : 招商银行
     * upTime : 2016-07-14 14:16:14
     */

    private String capital;                                         //    capital	注册资金(单位:万元)	string
    private String city;                                            //	    city	所在城市	string
    private String deadLineMaxSelfDefined;                          //	    deadLineMaxSelfDefined	产品最大期限天数 自定义显示	string
    private String deadLineMinSelfDefined;                          //	    deadLineMinSelfDefined	产品最小期限天数 自定义显示	string
    private String deadLineValueText;                               //	    deadLineValueText	产品期限	string	7,天,90,天
    private String feeRateMax;                                      //	    feeRateMax	最大年化收益	string
    private String feeRateMin;                                      //	    feeRateMin	最小年化收益	string
    private String icp;                                             //	    icp	icp备案	string
    private String orgAdvantage;                                    //	    orgAdvantage	机构亮点	string	T呗/理财师1.2.1版本 (多个以英文逗号分隔)
    private String orgBack;                                         //	    orgBack	平台背景	string
    private String orgHonor;                                        //	    orgHonor	荣誉	string	T呗/理财师2.0版本 富文本编辑
    private String orgInvestStrategy;                              //	    orgInvestStrategy	投资攻略	string	T呗1.2.1版本
    private String orgIsstaticproduct;                             //	    orgIsstaticproduct	是否为未对接机构	number	(1：未对接 ,0：已对接) T呗/理财师1.2.1版本
    private String orgJointType;                                   //	    orgJointType	对接的机构类型	number	(0:移动+PC端，1:移动端，2:PC端)
    private String orgLevel;                                       //	    orgLevel	平台安全评级	string
    private String orgLogo;                                        //	    orgLogo	机构详情图片	string	机构详情图片
    private String orgName;                                        //	    orgName	平台名称	string
    private String orgNo;                                          //	    orgNo	平台编码	string
    private String orgPlannerStrategy;                             //	    orgPlannerStrategy	貅比特攻略	string	理财师1.2.1版本
    private String orgProductTag;                                  //	    orgProductTag	产品自定义标签	string	T呗/理财师1.2.1版本 (多个以英文逗号分隔)
    private String orgProfile;                                     //	    orgProfile	平台简介	string
    private String orgSecurity;                                    //	    orgSecurity	安全保障	string
    private String orgTag;                                         //	    orgTag	机构标签	string	T呗/理财师1.2.1版本 (多个以英文逗号分隔)
    private String orgUrl;                                         //	    orgUrl	平台访问链接	string
    private String platformIco;                                    //	    platformIco	机构Logo	string	T呗/理财师1.2.1版本
    private String proDaysMax;                                     //	    proDaysMax	平台最大产品期限	string
    private String proDaysMin;                                     //	    proDaysMin	平台最小产品期限	string
    private String trusteeship;                                    //	    trusteeship	资金托管	string
    private String upTime;                                         //	    upTime	上线时间	string
    //  private List<OrgActivitysBean> orgActivitys;                    //	    orgActivitys	机构活动	array<object>	T呗/理财师2.0版本
    private List<OrgDynamicListBean> orgDynamicList;                //	    orgDynamicList	机构动态	array<object>	T呗/理财师2.0版本
    //图片
    private List<OrgHonorListBean> orgEnvironmentList;        //	    orgEnvironmentList	机构环境图	array<object>	T呗/理财师2.0版本
    private List<OrgHonorListBean> orgHonorList;                    //	    orgHonorList	机构荣誉证书	array<object>	T呗/理财师2.0版本
    private List<OrgHonorListBean> orgPapersList;                  //	    orgPapersList	机构证书	array<object>	T呗/理财师2.0版本

    private List<OrgActivitysBean> orgActivitys;
    private List<TeamInfosBean> teamInfos;                          //         团队介绍	array<object>

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDeadLineMaxSelfDefined() {
        return deadLineMaxSelfDefined;
    }

    public void setDeadLineMaxSelfDefined(String deadLineMaxSelfDefined) {
        this.deadLineMaxSelfDefined = deadLineMaxSelfDefined;
    }

    public String getDeadLineMinSelfDefined() {
        return deadLineMinSelfDefined;
    }

    public void setDeadLineMinSelfDefined(String deadLineMinSelfDefined) {
        this.deadLineMinSelfDefined = deadLineMinSelfDefined;
    }

    public String getDeadLineValueText() {
        return deadLineValueText;
    }

    public void setDeadLineValueText(String deadLineValueText) {
        this.deadLineValueText = deadLineValueText;
    }

    public String getFeeRateMax() {
        return feeRateMax;
    }

    public void setFeeRateMax(String feeRateMax) {
        this.feeRateMax = feeRateMax;
    }

    public String getFeeRateMin() {
        return feeRateMin;
    }

    public void setFeeRateMin(String feeRateMin) {
        this.feeRateMin = feeRateMin;
    }

    public String getIcp() {
        return icp;
    }

    public void setIcp(String icp) {
        this.icp = icp;
    }

    public String getOrgAdvantage() {
        return orgAdvantage;
    }

    public void setOrgAdvantage(String orgAdvantage) {
        this.orgAdvantage = orgAdvantage;
    }

    public String getOrgBack() {
        return orgBack;
    }

    public void setOrgBack(String orgBack) {
        this.orgBack = orgBack;
    }

    public String getOrgHonor() {
        return orgHonor;
    }

    public void setOrgHonor(String orgHonor) {
        this.orgHonor = orgHonor;
    }

    public String getOrgInvestStrategy() {
        return orgInvestStrategy;
    }

    public void setOrgInvestStrategy(String orgInvestStrategy) {
        this.orgInvestStrategy = orgInvestStrategy;
    }

    public String getOrgIsstaticproduct() {
        return orgIsstaticproduct;
    }

    public void setOrgIsstaticproduct(String orgIsstaticproduct) {
        this.orgIsstaticproduct = orgIsstaticproduct;
    }

    public String getOrgJointType() {
        return orgJointType;
    }

    public void setOrgJointType(String orgJointType) {
        this.orgJointType = orgJointType;
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
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

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgPlannerStrategy() {
        return orgPlannerStrategy;
    }

    public void setOrgPlannerStrategy(String orgPlannerStrategy) {
        this.orgPlannerStrategy = orgPlannerStrategy;
    }

    public String getOrgProductTag() {
        return orgProductTag;
    }

    public void setOrgProductTag(String orgProductTag) {
        this.orgProductTag = orgProductTag;
    }

    public String getOrgProfile() {
        return orgProfile;
    }

    public void setOrgProfile(String orgProfile) {
        this.orgProfile = orgProfile;
    }

    public String getOrgSecurity() {
        return orgSecurity;
    }

    public void setOrgSecurity(String orgSecurity) {
        this.orgSecurity = orgSecurity;
    }

    public String getOrgTag() {
        return orgTag;
    }

    public void setOrgTag(String orgTag) {
        this.orgTag = orgTag;
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

    public String getProDaysMax() {
        return proDaysMax;
    }

    public void setProDaysMax(String proDaysMax) {
        this.proDaysMax = proDaysMax;
    }

    public String getProDaysMin() {
        return proDaysMin;
    }

    public void setProDaysMin(String proDaysMin) {
        this.proDaysMin = proDaysMin;
    }

    public String getTrusteeship() {
        return trusteeship;
    }

    public void setTrusteeship(String trusteeship) {
        this.trusteeship = trusteeship;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

//    public List<OrgActivitysBean> getOrgActivitys() {
//        return orgActivitys;
//    }
//
//    public void setOrgActivitys(List<OrgActivitysBean> orgActivitys) {
//        this.orgActivitys = orgActivitys;
//    }

    public List<OrgDynamicListBean> getOrgDynamicList() {
        return orgDynamicList;
    }

    public void setOrgDynamicList(List<OrgDynamicListBean> orgDynamicList) {
        this.orgDynamicList = orgDynamicList;
    }

    public List<OrgHonorListBean> getOrgEnvironmentList() {
        return orgEnvironmentList;
    }

    public void setOrgEnvironmentList(List<OrgHonorListBean> orgEnvironmentList) {
        this.orgEnvironmentList = orgEnvironmentList;
    }

    public List<OrgHonorListBean> getOrgHonorList() {
        return orgHonorList;
    }

    public void setOrgHonorList(List<OrgHonorListBean> orgHonorList) {
        this.orgHonorList = orgHonorList;
    }

    public List<OrgHonorListBean> getOrgPapersList() {
        return orgPapersList;
    }

    public void setOrgPapersList(List<OrgHonorListBean> orgPapersList) {
        this.orgPapersList = orgPapersList;
    }

    public List<TeamInfosBean> getTeamInfos() {
        return teamInfos;
    }

    public void setTeamInfos(List<TeamInfosBean> teamInfos) {
        this.teamInfos = teamInfos;
    }

    public List<OrgActivitysBean> getOrgActivitys() {
        return orgActivitys;
    }

    public void setOrgActivitys(List<OrgActivitysBean> orgActivitys) {
        this.orgActivitys = orgActivitys;
    }

    public static class OrgActivitysBean extends BaseEntity {

        private static final long serialVersionUID = 5422492410949252091L;
        private String linkUrl;            //  linkUrl	机构活动宣传图跳转链接	string
        private String platformImg;        //	        platformImg	机构活动宣传图	string
        private String shareDesc;//	分享描述	string
        private String shareIcon;//	分享图标	string
        private String shareLink;//	分享链接	string
        private String shareTitle;//	分享标题	string

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getPlatformImg() {
            return platformImg;
        }

        public void setPlatformImg(String platformImg) {
            this.platformImg = platformImg;
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

    public static class OrgDynamicListBean extends BaseEntity {

        private static final long serialVersionUID = -1805723843738297583L;
        private String createTime; //发布时间
        private String id;                          //  id	机构动态id	number	此id传入动态信息查询接口
        private String orgDynamicUrl;               //	        orgDynamicUrl	动态链接	string	注意：动态链接为空时请调动态信息查询接口,动态信息和动态链接两个字段 只有一个有值。
        private String orgNumber;                   //	        orgNumber	机构编码	string
        private String orgTitle;                    //	        orgTitle	动态标题	string

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrgDynamicUrl() {
            return orgDynamicUrl;
        }

        public void setOrgDynamicUrl(String orgDynamicUrl) {
            this.orgDynamicUrl = orgDynamicUrl;
        }

        public String getOrgNumber() {
            return orgNumber;
        }

        public void setOrgNumber(String orgNumber) {
            this.orgNumber = orgNumber;
        }

        public String getOrgTitle() {
            return orgTitle;
        }

        public void setOrgTitle(String orgTitle) {
            this.orgTitle = orgTitle;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }

    public static class OrgEnvironmentListBean extends BaseEntity {

        private static final long serialVersionUID = -9203509216350318289L;
        private String orgNumber;               //	        orgNumber	机构编码	string
        private String orgPicture;              //	        orgPicture	环境图片	string

        public String getOrgNumber() {
            return orgNumber;
        }

        public void setOrgNumber(String orgNumber) {
            this.orgNumber = orgNumber;
        }

        public String getOrgPicture() {
            return orgPicture;
        }

        public void setOrgPicture(String orgPicture) {
            this.orgPicture = orgPicture;
        }
    }

    public static class OrgHonorListBean extends BaseEntity {

        private static final long serialVersionUID = 7309553151280640615L;
        private String orgNumber;          // orgNumber	机构编码	string
        private String orgPicture;         //orgPicture	证书	string

        public String getOrgNumber() {
            return orgNumber;
        }

        public void setOrgNumber(String orgNumber) {
            this.orgNumber = orgNumber;
        }

        public String getOrgPicture() {
            return orgPicture;
        }

        public void setOrgPicture(String orgPicture) {
            this.orgPicture = orgPicture;
        }
    }

    public static class OrgPapersListBean extends BaseEntity {

        private static final long serialVersionUID = -7801986994381652717L;
        private String orgNumber;   //orgNumber	机构编码	string
        private String orgPicture;  //orgPicture	证书	string

        public String getOrgNumber() {
            return orgNumber;
        }

        public void setOrgNumber(String orgNumber) {
            this.orgNumber = orgNumber;
        }

        public String getOrgPicture() {
            return orgPicture;
        }

        public void setOrgPicture(String orgPicture) {
            this.orgPicture = orgPicture;
        }
    }


}