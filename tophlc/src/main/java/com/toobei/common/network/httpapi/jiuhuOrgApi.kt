package com.toobei.common.network.httpapi

import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.jiuhuOrgEntity
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
interface jiuhuOrgApi {


    /**
     * 记账本在投列表-v4.5.0
     * token
     */
    @FormUrlEncoded
    @POST("jfqz/getToken")
    fun getJiuHuToken (@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<jiuhuOrgEntity>>
}