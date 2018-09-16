package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/26
 */

public class HasNewAddFeeCouponDatas extends BaseEntity {
    private static final long serialVersionUID = -2601203891676588545L;


    public AddFeeCouponBean addFeeCoupon;
    public String hasNewAddFeeCoupon;

    public static class AddFeeCouponBean {

        public String couponId;
        public String createTime;
        public String extends1;
        public String extends2;
        public String extends3;
        public String id;
        public String investLimit;
        public String name;
        public String operator;
        public String platformId;
        public String platformLimit;
        public String rate;
        public String source;
        public String type;
        public String updateTime;
        public String validBeginTime;
        public String validEndTime;
    }
}
