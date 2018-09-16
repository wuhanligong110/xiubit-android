package com.toobei.common.network.httpapi

import com.toobei.common.entity.*
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明: 记录相关
 *
 * @author hasee-pc
 * @time 2017/10/19
 */
interface RecordApi {


    /**
     * 4.5.0 交易记录
     * investTime	投资时间	string	YYYY-mm-dd 非必需
     * pageIndex	第几页 >=1,默认为1	number
     * pageSize	页面条数，默认为10	number
     * token	登录令牌	string	必需
     */
    @FormUrlEncoded
    @POST("personcenter/investCalendar")
    fun investCalendar(@FieldMap map: Map<String, String>): Observable<InvestCalendarEntity>


    /**
     * 4.5.1 交易日历统计
     * 	investMonth	投资时间	string	yyyy-MM
     */
    @FormUrlEncoded
    @POST("personcenter/investCalendarStatistics")
    fun investCalendarStatistics(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<InvestCalendarStatisticsData>>

    /**
     *investTime	投资时间	string	YYYY-mm-dd 非必需
     *pageIndex	第几页 >=1,默认为1	number
     *pageSize	页面条数，默认为10	number
     *token
     */
    @FormUrlEncoded
    @POST("personcenter/insuranceCalendar")
    fun insuranceCalendar(@FieldMap map: Map<String, String>): Observable<InvestCalendarEntity>

    /**
     * 4.5.0 回款日历
     * pageIndex	第几页 >=1,默认为1	number
     * pageSize	页面条数，默认为10	number
     * repamentTime	回款时间	string	YYYY-mm-dd 非必需
     * repamentType	回款状态	number	0-待回款 1-已回款 非必需 默认查所有
     * token	登录令牌	string	必需
     */
    @FormUrlEncoded
    @POST("personcenter/repamentCalendar")
    fun repamentCalendar(@FieldMap map: Map<String, String>): Observable<RepamentCalendarEntity>

    /**
     * 4.5.1 回款日历统计
     * rePaymentMonth	回款月份	string	yyyy-MM
     */
    @FormUrlEncoded
    @POST("personcenter/repamentCalendarStatistics")
    fun repamentCalendarStatistics(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<RepamentCalendarStatisticsData>>
}