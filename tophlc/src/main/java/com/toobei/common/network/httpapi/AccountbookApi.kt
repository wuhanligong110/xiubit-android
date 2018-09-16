package com.toobei.common.network.httpapi

import com.toobei.common.entity.AccountbookEditEntity
import com.toobei.common.entity.AccountbookInvestingDetailEntity
import com.toobei.common.entity.AccountbookInvestingListEntity
import com.toobei.common.entity.AccountbookStatisticsEntity
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明: 记账本相关
 *
 * @author hasee-pc
 * @time 2017/10/18
 */
interface AccountbookApi {


    /**
     *记账本编辑-v4.5.0
     * id	详情id(可选)	number	传id时为更新记账本信息,不传时为新增记账
     * investAmt	本金	number
     * investDirection	去向	string
     * profit	收益	number
     * remark	备注	string
     * status	状态	boolean	true:在投 false:删除
     * token	登录令牌	string
     *
     */
    @FormUrlEncoded
    @POST("accountbook/investing/edit/4.5.0")
    fun accountbookEdit(@FieldMap map: Map<String, String>): Observable<AccountbookEditEntity>

    /**
     * 记账本统计-v4.5.0
     * token
     */
    @FormUrlEncoded
    @POST("accountbook/statistics/4.5.0")
    fun accountbookStatistics(@FieldMap map: Map<String, String>): Observable<AccountbookStatisticsEntity>

    /**
     * 记账本在投详情-v4.5.0
     * id	详情id	number
     * token	登录令牌	string
     */
    @FormUrlEncoded
    @POST("accountbook/investing/detail/4.5.0")
    fun accountbookInvestingDetail(@FieldMap map: Map<String, String>): Observable<AccountbookInvestingDetailEntity>


    /**
     * 记账本在投列表-v4.5.0
     * token
     */
    @FormUrlEncoded
    @POST("accountbook/investing/list/4.5.0")
    fun accountbookInvestingList (@FieldMap map: Map<String, String>): Observable<AccountbookInvestingListEntity>
}