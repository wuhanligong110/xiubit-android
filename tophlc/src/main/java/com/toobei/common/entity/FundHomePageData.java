package com.toobei.common.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/17
 */

public class FundHomePageData extends BaseEntity{
    private static final long serialVersionUID = 1660862026234464260L;
    public String data;	//加密字符串	string
    public String integrationMode;	//加密方式	string
    public String referral;	//合作伙伴	string
    public String requestUrl; //请求地址

}
