package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseResponseEntity
import com.v5ent.xiubit.entity.*
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明: 关注和取消关注
 *
 * @author hasee-pc
 * @time 2017/10/26
 */
interface HomePageDialogApi {

    /**
     * 双十一活动任务完成状态
     * token
     */
    @FormUrlEncoded
    @POST("activity/DoubleElevenFinishStatueEntity/finishStatus/4.5.0")
    fun doubleElevenFinishStatus(@FieldMap map: Map<String, String>): Observable<DoubleElevenFinishStatueEntity>


    /**
     * 是否有新的第十一笔订单
     * token		string	必填

     */
    @FormUrlEncoded
    @POST("activity/doubleEleven/hasNewDoubleEleven/4.5.0")
    fun hasNewDoubleEleven(@FieldMap map: Map<String, String>): Observable<HasNewDoubleElevenEntity>


    /**
     * 4.5.0职级体验券弹出框
     * token
     */
    @FormUrlEncoded
    @POST("personcenter/partner/jobGradeVoucherPopup")
    fun jobGradeVoucherPopup(@FieldMap map: Map<String, String>): Observable<JobGradeVoucherPopupEntity>


    /**
     * 4.5.0是否有新的红包-v4.5.0
     * token
     */
    @FormUrlEncoded
    @POST("homepage/hasNewRedPacket/4.5.0")
    fun hasNewRedPacket(@FieldMap map: Map<String, String>): Observable<HasNewRedPacketEntity>


    /**
     * 是否有新的加拥券-v4.5.0
     * token
     */
    @FormUrlEncoded
    @POST("homepage/hasNewAddFeeCoupon/4.5.0")
    fun hasNewAddFeeCoupon(@FieldMap map: Map<String, String>): Observable<HasNewAddFeeCouponEntity>

    /**
     * 佣金券是否有新的使用记录-v4.5.0
     * token
     */
    @FormUrlEncoded
    @POST("homepage/hasNewAddFee/4.5.0")
    fun hasNewAddFeeUseRecord(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<HasNewAddFeeUseRecordData>>
}