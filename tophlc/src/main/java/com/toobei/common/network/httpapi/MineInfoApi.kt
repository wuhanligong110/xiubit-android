package com.toobei.common.network.httpapi

import com.toobei.common.entity.PersonInfoEntity
import com.toobei.common.entity.PersonInfoFundEntity
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明:我的相关接口
 *
 * @author hasee-pc
 * @time 2017/10/16
 */
interface MineInfoApi {
    /**
     * 4.5.0我的-整页-显示职级等
     * token
     */
    @FormUrlEncoded
    @POST("personcenter/personInfo")
    fun personInfo(@FieldMap map: Map<String, String>): Observable<PersonInfoEntity>


    /**
     * 4.5.0我的-整页-基金
     * token
     */
    @FormUrlEncoded
    @POST("personcenter/personInfoFund")
    fun personInfoFund(@FieldMap map: Map<String, String>): Observable<PersonInfoFundEntity>
}