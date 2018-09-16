package com.v5ent.xiubit.network.httpapi

import com.v5ent.xiubit.entity.HaveGoodTransNoReadEntity
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/27
 */
interface GoodReportApi {

    /**
     * V4.1.1未读喜报
     * token
     */
    @FormUrlEncoded
    @POST("personcenter/haveGoodTransNoRead")
    fun haveGoodTransNoRead(@FieldMap map: Map<String, String>): Observable<HaveGoodTransNoReadEntity>
}