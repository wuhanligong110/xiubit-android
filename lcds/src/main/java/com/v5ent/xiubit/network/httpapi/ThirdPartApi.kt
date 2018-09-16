package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseResponseEntity
import com.v5ent.xiubit.entity.IsBindOrgAcctData
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
interface ThirdAccountPartApi {

    /**
     * 接口名称 是否已绑定的机构 -钟灵 -已实现
     * 请求类型 get
     * 请求Url /platfrom/isBindOrgAcc
     * @param token          登录态
     * @param platFromNumber 机构编码
     */
    @FormUrlEncoded
    @POST("platfrom/isBindOrgAcct")
    fun isBindOrgAcc(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<IsBindOrgAcctData>>



}