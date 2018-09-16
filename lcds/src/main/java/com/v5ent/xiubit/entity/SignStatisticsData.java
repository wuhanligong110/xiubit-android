package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/11/17.
 */

public class SignStatisticsData extends BaseEntity {
    private static final long serialVersionUID = -3584152066675959022L;

    public String dissatisfyDescription; //不符合转出条件描述
    public String firstSignTime;     // 首次签到时间	object
    public String leftBouns;    //	剩余奖励金	string
    public String totalBouns;    //	总奖励金	string
    public String transferBouns;    //	可转奖励金	string
    public String transferedBouns;    //	已转金额	string
    public String userName;    //	用户姓名
}
