package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseResponseEntity
import com.v5ent.xiubit.entity.InviteCfpOutCreateQrData
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明: 二维码
 *
 * @author hasee-pc
 * @time 2017/10/26
 */
interface QrCodeApi {

    /**
     * 我的邀请理财师二维码
     * token		string	必填
     *
     */
    @FormUrlEncoded
    @POST("invitation/cfp/homepage")
    fun getQrcode(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<InviteCfpOutCreateQrData>>



}