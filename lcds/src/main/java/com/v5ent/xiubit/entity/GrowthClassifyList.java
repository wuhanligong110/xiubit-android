package com.v5ent.xiubit.entity;

import com.toobei.common.entity.PageListBase;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/7/31
 */

public class GrowthClassifyList {
    private static final long serialVersionUID = 2133235092903337443L;
    
    private String bannerImg;
    
    private GrowthClassifyListBean growthHandbookList ;

    public GrowthClassifyListBean getGrowthHandbookList() {
        return growthHandbookList;
    }

    public void setGrowthHandbookList(GrowthClassifyListBean growthHandbookList) {
        this.growthHandbookList = growthHandbookList;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }
    
    public static class GrowthClassifyListBean extends PageListBase<GrowthClassifyData>{
        
    }
}
