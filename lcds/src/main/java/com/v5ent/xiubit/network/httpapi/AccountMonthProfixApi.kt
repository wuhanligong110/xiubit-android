package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseResponseEntity
import com.v5ent.xiubit.entity.IncomeDetailDatasData
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明: 月度收益
 *
 * @author hasee-pc
 * @time 2017/10/26
 */
interface AccountMonthProfixApi {

    /**
     * 我的邀请理财师二维码
     * token		string	必填
     * @param token
     * @param month      日期 时间 格式 2015-10
     * @param profixType 收益类型1销售佣金，2推荐津贴，3活动奖励，4团队leader奖励（必填）
     */
    @FormUrlEncoded
    @POST("account/monthProfixDetailList/2.1")
    fun getAccountMonthProfix(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<IncomeDetailDatasData>>



}