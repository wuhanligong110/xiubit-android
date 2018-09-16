package com.toobei.common.entity;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/22
 */

public class CfgMemberDetailData extends BaseEntity {
    private static final long serialVersionUID = -3926396390422687791L;

    public String currInvestAmt;
    public String directRecomCfp;
    public String firstInvestTime;
    public boolean follow;
    public String grade;
    public String headImage;
    public String loginTime;
    public String mobile;
    public String registTime;
    public String secondLevelCfp;
    public String thisMonthIssueAmt;
    public String thisMonthProfit;
    public String totalIssueAmt;
    public String totalProfit;
    public String userName;
    public List<RegisteredOrgListBean> registeredOrgList;

    public static class RegisteredOrgListBean {
        /**
         * orgLogo : 测试内容1o8t
         */

        public String orgLogo;
    }
}
