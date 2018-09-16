package com.toobei.common.entity;

/**
 * Created by Administrator on 2016/12/20 0020.
 */

public class ProductClassifyPreferenceDetail {

    private static final long serialVersionUID = 3191051615684971073L;

    /**
     * productPageListResponse : {"addRate":0,"assignmentTime":"","buyMaxMoney":0,"buyMinMoney":1,"buyTotalMoney":100,"buyedTotalMoney":51,"buyedTotalPeople":2,"cfpRecommend":"","collectBeginTime":"2016-10-26 10:07:01","collectEndTime":"2016-10-27 10:07:01","couldbuyMoney":49,"deadLineMaxSelfDefined":"1天","deadLineMaxValue":1,"deadLineMinSelfDefined":"1天","deadLineMinValue":1,"deadLineValueText":"1, 天","feeRatio":0,"flowMaxRate":8,"flowMinRate":8,"grade":"3","isCollect":2,"isFixedDeadline":1,"isFlow":1,"isHaveProgress":0,"isQuota":2,"isRedemption":0,"orgAmountLimit":500000,"orgFeeType":1,"orgIsstaticproduct":0,"orgName":"E周行","orgNumber":"OPEN_EZHOUXING_WEB","platformCashback":"","productDetailUrl":"http://preliecai.tophlc.com/pages/financing/product_detail.html","productId":"39A17FDB1936410698D6CCD4FA8795D0","productLogo":"569775f1848d2c385a1a5b10d0e4d42b","productName":"领会测试标20161026","productRateText":"8.0%","redemptionTime":"","saleStartTime":"2016-10-26 10:07:01","status":1,"tagList":"","tagListRight":["首投"],"thirdProductId":"10048","timeNow":"2016-11-23 13:11:40"}
     * cateId : 802
     * cateName : 理财师热推
     */

    private ProductDetail productPageListResponse;
    private int cateId;
    private String cateName;

    public void setProductPageListResponse(ProductDetail productPageListResponse) {
        this.productPageListResponse = productPageListResponse;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public ProductDetail getProductPageListResponse() {
        return productPageListResponse;
    }

    public int getCateId() {
        return cateId;
    }

    public String getCateName() {
        return cateName;
    }
}
