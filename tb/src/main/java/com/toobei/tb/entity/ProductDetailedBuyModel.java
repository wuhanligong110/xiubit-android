package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2016/12/29.
 */

public class ProductDetailedBuyModel extends BaseEntity {

    private static final long serialVersionUID = -895356464858276389L;
    private String orgNo ;// 机构编码
    private String productId ;// 产品id
    private String orgName ; //机构名称

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
