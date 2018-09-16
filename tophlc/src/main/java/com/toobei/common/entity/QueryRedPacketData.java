package com.toobei.common.entity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/20
 */

public class QueryRedPacketData extends BaseEntity {
    private static final long serialVersionUID = -4629638016972072099L;


    public String amount	;//金额	string
    public String amountLimit;//	金额限制	number	0=不限|1=大于|2=大于等于
    public String cfpIfSend;
    public String deadline;
    public String expireTime;
    public String investLimit;
    public String platform;
    public boolean platformLimit;
    public String productLimit;
    public String productName;
    public String useStatus;
    public String name;
    public String redpacketCount;
    public String redpacketMoney;
    public String money;
    public String remark;
    public String rid;
    public String userImage;
    public String userMobile;
    public String userName;
    public String sendStatus;
}
