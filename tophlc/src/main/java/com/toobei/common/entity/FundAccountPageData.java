package com.toobei.common.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/17
 */
 
public class FundAccountPageData extends BaseEntity{
    private static final long serialVersionUID = -7179458091981973428L;
                                                            
        public String data;                 //加密数据		
        public String integrationMode;    //加密方式		
        public String referral;    //合作伙伴		
        public String requestUrl;   //	请求地址
    }
