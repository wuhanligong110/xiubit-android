package com.v5ent.xiubit.entity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/26
 */

public class HomePageDialogBean {
    public HasNewRedPacketEntity hasNewRedPacketEntity;
    public HasNewAddFeeCouponEntity hasNewAddFeeCouponEntry;
    public JobGradeVoucherPopupEntity jobGradeVoucherPopupEntity;
    public HasNewDoubleElevenEntity doubleElevenFinishStatueEntity;

    public HomePageDialogBean(HasNewRedPacketEntity hasNewRedPacketEntity, HasNewAddFeeCouponEntity hasNewAddFeeCouponEntry, JobGradeVoucherPopupEntity jobGradeVoucherPopupEntity, HasNewDoubleElevenEntity doubleElevenFinishStatueEntity) {
        this.hasNewRedPacketEntity = hasNewRedPacketEntity;
        this.hasNewAddFeeCouponEntry = hasNewAddFeeCouponEntry;
        this.jobGradeVoucherPopupEntity = jobGradeVoucherPopupEntity;
        this.doubleElevenFinishStatueEntity = doubleElevenFinishStatueEntity;
    }

    public HomePageDialogBean(){}


}
