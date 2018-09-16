package com.v5ent.xiubit.data;

import com.v5ent.xiubit.R;

/**
 * 公司: tophlc
 * 类说明: 新手引导图片
 *
 * @author a
 * @time 2016/12/14 0014 下午 4:28
 */
public enum GuideViewType {

   



//    /**
//     * 首页-首页
//     */
//    FRAGMENT_HOME_GUIDE1(R.drawable.img_guide_home_page1),
//    FRAGMENT_HOME_GUIDE2(R.drawable.img_guide_home_page2),
//    /**
//     * 首页-我的
//     */
//    FRAGMENT_MINE_GUIDE1(R.drawable.img_guide_mine_page1),
//    FRAGMENT_MINE_GUIDE2(R.drawable.img_guide_mine_page2),
//    /**
//     * 首页-理财
//     */
//    FRAGMENT_LIECAI_GUIDE1(R.drawable.img_guide_liecai_page1),
//
//
//
//    /**
//     * 首页-投资页
//     */
//    FRAGMENT_HOME_INVEST_GUIDE1(R.drawable.img_guide_product_page1),
//
//    /**
//     * 平台详情页
//     */
//    ORG_DETIAL_GUIDE1(R.drawable.img_guide_orgdetial_page1),
    ORG_DETIAL_GUIDE2(R.drawable.img_guide_orgdetial_page2);


    int value;

    GuideViewType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}