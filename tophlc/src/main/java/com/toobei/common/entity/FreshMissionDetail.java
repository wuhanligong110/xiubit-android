package com.toobei.common.entity;


public class FreshMissionDetail extends BaseEntity {


    private String finishAll;    //  是否完成所有任务；0未完成，1已完成	string
    private String grantHongbaoStatus;            //   	派发红包；0未完成，1已完成，2已领取	，3锁定
    private String inviteCfplannerStatus;        //	    邀请理财师；0未完成，1已完成，2已领取	string
    private String inviteCustomerStatus;         //	    邀请客户；0未完成，1已完成，2已领取	string
    private String buyProductStatus;             //	    自己购买
    private String recommendProductStatus;       //	    推荐产品；0未完成，1已完成，2已领取，3锁定
    private String seeProfitStatus;              //	    查看收益；0未完成，1已完成，2已领取	string

    public FreshMissionDetail(String finishAll) {
        this.finishAll = finishAll;
    }

    public void setGrantHongbaoStatus(String grantHongbaoStatus) {
        this.grantHongbaoStatus = grantHongbaoStatus;
    }

    public void setInviteCfplannerStatus(String inviteCfplannerStatus) {
        this.inviteCfplannerStatus = inviteCfplannerStatus;
    }

    public void setInviteCustomerStatus(String inviteCustomerStatus) {
        this.inviteCustomerStatus = inviteCustomerStatus;
    }

    public void setBuyProductStatus(String buyProductStatus) {
        this.buyProductStatus = buyProductStatus;
    }

    public void setRecommendProductStatus(String recommendProductStatus) {
        this.recommendProductStatus = recommendProductStatus;
    }

    public void setSeeProfitStatus(String seeProfitStatus) {
        this.seeProfitStatus = seeProfitStatus;
    }

    public String getGrantHongbaoStatus() {
        return grantHongbaoStatus;
    }

    public String getInviteCfplannerStatus() {
        return inviteCfplannerStatus;
    }

    public String getInviteCustomerStatus() {
        return inviteCustomerStatus;
    }

    public String getBuyProductStatus() {
        return buyProductStatus;
    }

    public String getRecommendProductStatus() {
        return recommendProductStatus;
    }

    public String getSeeProfitStatus() {
        return seeProfitStatus;
    }

    public String getFinishAll() {
        return finishAll;
    }

    public void setFinishAll(String finishAll) {
        this.finishAll = finishAll;
    }
}
