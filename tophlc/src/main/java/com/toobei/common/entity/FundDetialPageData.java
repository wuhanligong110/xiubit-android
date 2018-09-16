package com.toobei.common.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/17
 */

public class FundDetialPageData extends BaseEntity {
    private static final long serialVersionUID = 7312700805778661989L;


    public String data;               //加密数据		
    public String integrationMode;    //加密方式		
    public String productCode;   //基金代码		
    public String referral;   //合作伙伴		
    public String requestUrl;        //请求地址
}
