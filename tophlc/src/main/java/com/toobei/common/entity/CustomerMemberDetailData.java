package com.toobei.common.entity;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/18
 */

public class CustomerMemberDetailData extends BaseEntity {
    private static final long serialVersionUID = -1141256153328925072L;


    public String currInvestAmt;
    public String firstInvestTime;
    public boolean follow;
    public String headImage;
    public String loginTime;
    public String mobile;
    public String registTime;
    public String thisMonthInvestAmt;
    public String thisMonthProfit;
    public String totalInvestAmt;
    public String totalProfit;
    public String userName;
    public List<RegisteredOrgListBean> registeredOrgList;

    public static class RegisteredOrgListBean {
        /**
         * orgLogo : 测试内容6842
         */

        public String orgLogo;
    }
}
