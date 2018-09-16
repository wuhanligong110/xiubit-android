package com.toobei.common.network.httpapi

import com.toobei.common.entity.InvestedPlatformEntity
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明:  网贷相关
 *
 * @author yangLin
 * @time 2017/10/13
 */
interface NetLoanApi {

    /**
     * 我的投资平台--4.0.0
     * token
     */
    @FormUrlEncoded
    @POST("platfrom/investedPlatform/4.0.0")
    fun MyInvestedPlatform(@FieldMap map: Map<String, String>): Observable<InvestedPlatformEntity>
}