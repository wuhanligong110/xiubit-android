package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

import java.util.List;

public class RecommendProductDatasData extends BaseEntity {


    private static final long serialVersionUID = -4739886507431444984L;

    private int orgIsstaticproduct;      // 未技术对接,1：是 ,0：否 当为1时取allFeeList ，当为0时取haveFeeList和notHaveFeeList
    private List<RecommendProductData> allFeeList; //未对接平台所有投资人

    // 2016/10/17 0017  有佣金的投资人
    private List<RecommendProductData> haveFeeList;  //拥有佣金的投资人
    private List<RecommendProductData> notHaveFeeList; //notHaveFeeList

    public int getOrgIsstaticproduct() {
        return orgIsstaticproduct;
    }

    public void setOrgIsstaticproduct(int orgIsstaticproduct) {
        this.orgIsstaticproduct = orgIsstaticproduct;
    }

    public List<RecommendProductData> getAllFeeList() {
        return allFeeList;
    }

    public void setAllFeeList(List<RecommendProductData> allFeeList) {
        this.allFeeList = allFeeList;
    }


    public List<RecommendProductData> getHaveFeeList() {
        return haveFeeList;
    }

    public void setHaveFeeList(List<RecommendProductData> haveFeeList) {
        this.haveFeeList = haveFeeList;
    }

    public List<RecommendProductData> getNotHaveFeeList() {
        return notHaveFeeList;
    }

    public void setNotHaveFeeList(List<RecommendProductData> notHaveFeeList) {
        this.notHaveFeeList = notHaveFeeList;
    }


}