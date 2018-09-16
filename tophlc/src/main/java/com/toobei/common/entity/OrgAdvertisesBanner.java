package com.toobei.common.entity;

/**
 * 公司: tophlc
 * 类说明：首页 轮播图实体
 *
 * @date 2015-10-22
 */
public class OrgAdvertisesBanner extends BaseEntity {

    private static final long serialVersionUID = 1279226843362092772L;
    /**
     * serialVersionUID
     */

    private String orgActivityAdvertise;//机构活动宣传图	string
    private String orgActivityAdvertiseUrl;//	机构活动宣传图跳转链接	string
    private ShareContent shareContent;


    public void setShareContent(ShareContent shareContent) {
        this.shareContent = shareContent;
    }

    public String getOrgActivityAdvertise() {
        return orgActivityAdvertise;
    }

    public void setOrgActivityAdvertise(String orgActivityAdvertise) {
        this.orgActivityAdvertise = orgActivityAdvertise;
    }

    public String getOrgActivityAdvertiseUrl() {
        return orgActivityAdvertiseUrl;
    }

    public void setOrgActivityAdvertiseUrl(String orgActivityAdvertiseUrl) {
        this.orgActivityAdvertiseUrl = orgActivityAdvertiseUrl;
    }
}