package com.toobei.common.entity;

/**
 * 接口名称 3.4.8 产品分类列表 v2.0.1
 * 请求类型 get
 * 请求Url /product/productClassifyPageList/2.0.1
 * 接口描述 展开
 * 理财-产品分类列表-分页排序
 * cateId特殊说明：
 * 1-热门产品 【不带自动上下架功能】
 * 2-新手产品
 * 3-短期产品
 * 4-高收益...
 *
 * @author Administrator
 * @time 2016/11/24 0024 上午 10:39
 */
public class ProductClassifyStatisticsDetail extends BaseEntity {


    private static final long serialVersionUID = 7559902058535516557L;
    /**
     * cateDeclare : 测试内容i84k
     * cateId : 1
     * cateLogoChannel : 测试内容v4d3
     * cateLogoInvestor : 测试内容0fbu
     * cateName : 热门产品
     * count : 18
     * flowMaxRateStatistics : 13.5
     * flowMinRateStatistics : 7.5
     * productPageListResponse : {}
     * urlLink : 测试内容3236
     */

    private String cateDeclare;                                        //   cateDeclare	分类说明	string
    private String cateId;                                                //   cateId	分类ID	number
    // 1-热门产品 2-新手产品 3-短期产品 4-高收益产品 5-稳健收益产品 801-理财师推荐产品
    // 802-热推产品 901-首投标 902-复投标 多个一起查询的时候请使用,分开 如：1,2,3,4,5,801,901,902 不传时则查询所有的产品分类
    private String cateLogoChannel;                                    //   cateLogoChannel	分类logo 猎才大师	string
    private String cateLogoInvestor;                                   //   cateLogoInvestor	分类logo 投资者端	string
    private String cateName;                                           //   cateName	分类名称	string
    private String count;                                                 //   count	产品数量	number
    private String flowMaxRateStatistics;                              //   flowMaxRateStatistics	最大浮动利率统计	number
    private String flowMinRateStatistics;                              //   flowMinRateStatistics	最小浮动利率统计	number
    //  private ProductPageListResponseBean productPageListResponse;       //   productPageListResponse	每个产品分类 对应的一个产品 (PC端)	object	详情同 3.4.8产品分类列表返回对象
    private String urlLink;                                            //   urlLink	分类链接	string

    public void setCateDeclare(String cateDeclare) {
        this.cateDeclare = cateDeclare;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public void setCateLogoChannel(String cateLogoChannel) {
        this.cateLogoChannel = cateLogoChannel;
    }

    public void setCateLogoInvestor(String cateLogoInvestor) {
        this.cateLogoInvestor = cateLogoInvestor;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setFlowMaxRateStatistics(String flowMaxRateStatistics) {
        this.flowMaxRateStatistics = flowMaxRateStatistics;
    }

    public void setFlowMinRateStatistics(String flowMinRateStatistics) {
        this.flowMinRateStatistics = flowMinRateStatistics;
    }

//    public void setProductPageListResponse(ProductPageListResponseBean productPageListResponse) {
//        this.productPageListResponse = productPageListResponse;
//    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public String getCateDeclare() {
        return cateDeclare;
    }

    public String getCateId() {
        return cateId;
    }

    public String getCateLogoChannel() {
        return cateLogoChannel;
    }

    public String getCateLogoInvestor() {
        return cateLogoInvestor;
    }

    public String getCateName() {
        return cateName;
    }

    public String getCount() {
        return count;
    }

    public String getFlowMaxRateStatistics() {
        return flowMaxRateStatistics;
    }

    public String getFlowMinRateStatistics() {
        return flowMinRateStatistics;
    }

//    public ProductPageListResponseBean getProductPageListResponse() {
//        return productPageListResponse;
//    }

    public String getUrlLink() {
        return urlLink;
    }

    // TODO: 2016/11/24 0024 每个产品分类 对应的一个产品 (PC端)	object	详情同 3.4.8产品分类列表返回对象
//    public static class ProductPageListResponseBean {
//    }
}