package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明: 接口详情 (id: 348)
 * 接口名称 V2.2.0判断理财师leader奖励满足状态
 * 请求类型 get
 * 请求Url /perso
 *
 * @author qingyechen
 * @time 2017/3/2 13:46
 */
public class LeaderProfitStatus extends BaseEntity {

    private static final long serialVersionUID = -5237436270778108403L;
    private String cfpStatus;

    public String getCfpStatus() {
        return cfpStatus;
    }

    public void setCfpStatus(String cfpStatus) {
        this.cfpStatus = cfpStatus;
    }
}
