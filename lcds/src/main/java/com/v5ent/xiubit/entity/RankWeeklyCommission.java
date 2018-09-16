package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明:  接口详情 (id: 337)
 * 接口名称 周佣榜-排行榜-陈衡-已实现
 * 请求类型 get
 * 请求Url act/rankList/zyb/rank
 *
 * @author qingyechen
 * @time 2017/2/23 10:46
 */
public class RankWeeklyCommission extends BaseEntity {


    private static final long serialVersionUID = 4437157285140908574L;
    /**
     * headImg : 测试内容2jw6
     * mobile : 188****8541
     * rank : 1
     * totalProfit : 222199.99
     */

    private String headImg;               //headImg	头像 空为没有	string
    private String mobile;                //	        mobile	手机号码	string
    private String rank;                  //	        rank	排名	number
    private String totalProfit;           //	        totalProfit	总收益	string
    private String levelName;              // 职级名称

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(String totalProfit) {
        this.totalProfit = totalProfit;
    }
}
