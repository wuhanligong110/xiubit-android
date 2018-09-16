package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseEntity
import com.toobei.common.entity.BaseResponseEntity
import com.v5ent.xiubit.entity.SunburnDetailEntity
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
interface APartApi {

    /**
     * 截图后上次晒单
     * id	晒单ID	string
     * image	晒单图片	string	可以为空
     * token		string
     */
    @FormUrlEncoded
    @POST("orginfoa/sunburn")
    fun sunburn(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<BaseEntity>>


    /**
     * 接口名称 晒单详情
     * 请求类型 get
     * 请求Url  /api/orginfoa/sunburnDetail
     */
    @FormUrlEncoded
    @POST("orginfoa/sunburnDetail")
    fun querySunburn(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<SunburnDetailEntity>>
}