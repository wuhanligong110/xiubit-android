package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.PageListBase
import com.v5ent.xiubit.entity.*
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明: 签到
 *
 * @author hasee-pc
 * @time 2017/10/26
 */
interface SignInApi {

    /**
     * 分享
     * token		string	必填
     */
    @FormUrlEncoded
    @POST("sign/share/prize/4.5.1")
    fun signSharePrize(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<SignSharePrizeData>>

    /**
     * 分享信息
     * token		string	必填
     */
    @FormUrlEncoded
    @POST("sign/share/info/4.5.1")
    fun signShareInfo(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<SignShareInfoData>>


    /**
     * 奖励金转账户
     * token		string	必填
     */
    @FormUrlEncoded
    @POST("sign/bouns/transfer/4.5.1")
    fun signBounsTransfer(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<SignBounsTransferData>>


    /**
     * 签到
     * token		string	必填
     */
    @FormUrlEncoded
    @POST("sign/sign/4.5.1")
    fun signIn(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<SignInData>>

    /**
     * 签到信息
     * token		string	必填
     */
    @FormUrlEncoded
    @POST("sign/info/4.5.1")
    fun signInfo(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<SignInfoData>>


    /**
     * 签到日历
     * token		string	必填
     */
    @FormUrlEncoded
    @POST("sign/calendar/4.5.1")
    fun signCalendar(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<SignCalendarData>>


    /**
     * 签到统计
     * token		string	必填
     */
    @FormUrlEncoded
    @POST("sign/statistics/4.5.1")
    fun signStatistics(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<SignStatisticsData>>


    /**
     * 签到记录
     * pageIndex	页码	number
     * pageSize
     * token		string	必填
     */
    @FormUrlEncoded
    @POST("sign/records/pageList/4.5.1")
    fun signRecordsPageList(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<PageListBase<SignRecordsPageData>>>

}