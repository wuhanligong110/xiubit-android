package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/11/17.
 */

public class SignInfoData extends BaseEntity {
    private static final long serialVersionUID = -3727548908433357954L;

    public String consecutiveDays;
    public boolean hasSigned;
    public SignInfoBean signInfo;
    public String times;

    public static class SignInfoBean {

        public String extend1;
        public String extend2;
        public String id;
        public String redpacketId;
        public String shareStatus; //	分享状态 1：已分享 0：未分享
        public String signAmount;
        public String signTime;
        public String timesAmount;
        public String timesType;
        public String updateTime;
        public String userId;
        public String userType;
    }
}
