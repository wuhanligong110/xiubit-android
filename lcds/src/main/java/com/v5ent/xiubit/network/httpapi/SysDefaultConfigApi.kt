package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseResponseEntity
import com.v5ent.xiubit.entity.DefaultConfig
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
interface SysDefaultConfigApi {

    /**
     * 功能：app获取默认配置
     */
    @FormUrlEncoded
    @POST("app/default-config")
    fun sysConfig(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<DefaultConfig>>



}