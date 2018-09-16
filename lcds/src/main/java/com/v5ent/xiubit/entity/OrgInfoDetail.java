package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 机构info
 */
public class OrgInfoDetail extends BaseEntity {


    private static final long serialVersionUID = 679447947750074217L;


            /**
             * grade : 测试内容64s9
             * listRecommend : 16502
             * name : 测试内容18j3
             * orgNumber : 测试内容m8b3
             * averageRate : 0.00
             * orgAdvantage : 万惠金科(股票代码：430705)旗下的全资子公司
             * orgFeeRatio : 6
             * orgTag : 上市公司系,
             * platformlistIco : cb00e18e42c5213811fb7f62182c8e11
             * usableProductNums : 0
             */

            private String grade;
            private String listRecommend;
            private String name;
            private String orgNumber;
            private String averageRate;
            private String orgAdvantage;
            private String orgFeeRatio;
            private String orgTag;
            private String platformlistIco;
            private String usableProductNums;


    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getListRecommend() {
        return listRecommend;
    }

    public void setListRecommend(String listRecommend) {
        this.listRecommend = listRecommend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(String averageRate) {
        this.averageRate = averageRate;
    }

    public String getOrgAdvantage() {
        return orgAdvantage;
    }

    public void setOrgAdvantage(String orgAdvantage) {
        this.orgAdvantage = orgAdvantage;
    }

    public String getOrgFeeRatio() {
        return orgFeeRatio;
    }

    public void setOrgFeeRatio(String orgFeeRatio) {
        this.orgFeeRatio = orgFeeRatio;
    }

    public String getOrgTag() {
        return orgTag;
    }

    public void setOrgTag(String orgTag) {
        this.orgTag = orgTag;
    }

    public String getPlatformlistIco() {
        return platformlistIco;
    }

    public void setPlatformlistIco(String platformlistIco) {
        this.platformlistIco = platformlistIco;
    }

    public String getUsableProductNums() {
        return usableProductNums;
    }

    public void setUsableProductNums(String usableProductNums) {
        this.usableProductNums = usableProductNums;
    }
}