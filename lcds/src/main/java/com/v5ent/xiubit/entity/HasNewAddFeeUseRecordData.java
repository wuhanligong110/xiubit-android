package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/11/2.
 */

public class HasNewAddFeeUseRecordData extends BaseEntity {

    private static final long serialVersionUID = 7131325257675158122L;


        public AddFeeCouponUseDetailBean addFeeCouponUseDetail;
        public String hasNewAddFee;

        public static class AddFeeCouponUseDetailBean {
            /**
             * couponId : 85efced98e9a40f59a261a9acee52b84
             * couponType : 1
             * createTime : 2017-10-18 11:22:17
             * extends1 :
             * extends2 :
             * extends3 :
             * feeAmount : 83.33
             * feeRate : 6
             * id : 1
             * investId : beac0a78e1d24ceaa108a261d1626a17
             * userId : af55712345fe4a8aa97c349b753d6d5c
             */

            public String couponId;
            public String couponType;
            public String createTime;
            public String extends1;
            public String extends2;
            public String extends3;
            public String feeAmount;
            public String feeRate;
            public String id;
            public String investId;
            public String userId;
        }
    }
