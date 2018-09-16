package com.toobei.common.entity;

import java.util.List;

/**
 * Created by hasee-pc on 2017/12/26.
 */

public class InsuranceCategoryEntiy extends BaseEntity {
    private static final long serialVersionUID = -8667199587547122531L;


    public List<InsuranceCategoryBean> datas;

    public static class InsuranceCategoryBean {
        /**
         * category : 1
         * message : 意外险
         */

        public String category;
        public String message;
    }
}
