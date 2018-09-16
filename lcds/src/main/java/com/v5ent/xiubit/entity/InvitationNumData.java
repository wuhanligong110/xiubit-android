package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/6/30
 */

public class InvitationNumData extends BaseEntity{
    private static final long serialVersionUID = 7832771101528692522L;

   private String cfpNum;	//推荐理财师数量	number
    private String investorNum;//邀请客户数量

    public String getCfpNum() {
        return cfpNum;
    }

    public void setCfpNum(String cfpNum) {
        this.cfpNum = cfpNum;
    }

    public String getInvestorNum() {
        return investorNum;
    }

    public void setInvestorNum(String investorNum) {
        this.investorNum = investorNum;
    }
}
