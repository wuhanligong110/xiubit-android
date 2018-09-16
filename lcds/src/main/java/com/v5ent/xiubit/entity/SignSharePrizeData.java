package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/11/17.
 */

public class SignSharePrizeData extends BaseEntity {
    private static final long serialVersionUID = 5268116531021900681L;

        public String bouns;
        public String prizeType;
        public RedpacketResponseBean redpacketResponse;

        public static class RedpacketResponseBean {

            public String amount;
            public String amountLimit;
            public String cfpIfSend;
            public String deadline;
            public String expireTime;
            public String investLimit;
            public String name;
            public String platform;
            public boolean platformLimit;
            public String productLimit;
            public String productName;
            public String redpacketCount;
            public String redpacketMoney;
            public String redpacketType;
            public String remark;
            public String rid;
            public String sendStatus;
            public String useStatus;
            public String userImage;
            public String userMobile;
            public String userName;
        }
    }
