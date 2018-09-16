package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by yangLin on 2018/1/7.
 */

public class SmartTestResultData extends BaseEntity {
    private static final long serialVersionUID = 8341617200773897949L;


    public String riskGrade;
    public String totalScore;
    public List<RecomListBean> recomList;

    public static class RecomListBean {
        /**
         * categoryImage : ee3dd86e92bf9da9e1e971433e680c1e
         * recomCategory : 5
         */

        public String categoryImage;
        public String recomCategory;
    }
}
