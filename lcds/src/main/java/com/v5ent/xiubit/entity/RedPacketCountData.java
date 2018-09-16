package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/6/28
 */

public class RedPacketCountData extends BaseEntity{
    private static final long serialVersionUID = 1696046745483794431L;

    private String investRedPacketCount;
    private String sendRedPacketCount;

    public String getInvestRedPacketCount() {
        return investRedPacketCount;
    }

    public void setInvestRedPacketCount(String investRedPacketCount) {
        this.investRedPacketCount = investRedPacketCount;
    }

    public String getSendRedPacketCount() {
        return sendRedPacketCount;
    }

    public void setSendRedPacketCount(String sendRedPacketCount) {
        this.sendRedPacketCount = sendRedPacketCount;
    }
}
